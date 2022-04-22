package model.game;

import model.events.RobotDestroyEvent;
import model.events.RobotTeleportEvent;
import model.field.Field;
import model.fieldObjects.robot.BigRobot;
import model.fieldObjects.robot.LittleRobot;
import model.fieldObjects.robot.Robot;
import model.listeners.ExitCellListener;
import model.listeners.GameStatusChangedListener;
import model.listeners.RobotDestroyListener;
import model.labyrinth.Labyrinth;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class Game implements ExitCellListener, RobotDestroyListener {
    // ----------------------------------------------- Статус игры ----------------------------------------------
    private GameStatus _gameStatus = null;

    public GameStatus getGameStatus() {
        return _gameStatus;
    }

    private void setGameStatus(GameStatus gameStatus) {
        _gameStatus = gameStatus;
        fireGameStatusChange();
    }

    // ----------------------------------------------- Инициализация игры ----------------------------------------------
    public void initGame(Labyrinth labyrinth) {
        buildField(labyrinth);
        setGameObjectsListeners();
        setGameStatus(GameStatus.GAME_IS_ON);
    }

    // ----------------------------------------------- Завершение игры ----------------------------------------------
    public void abort() {
        _gameField = null;
        setGameStatus(GameStatus.GAME_ABORTED);
    }

    // ----------------------------------------------- Игровое поле ----------------------------------------------
    private Field _gameField = null;

    private void buildField(Labyrinth labyrinth) {
        _gameField = labyrinth.buildField();
    }

    public Field getGameField() {
        return _gameField;
    }

    // ----------------------------------------------- Роботы ----------------------------------------------
    public List<Robot> getRobotsOnField() {
        return _gameField.getRobots();
    }

    // ----------------------------------------------- Установить взаимную прослушку объектов --------------------------
    private void setGameObjectsListeners() {
        LittleRobot littleRobot = null;
        BigRobot bigRobot = null;
        for (var robot: _gameField.getRobots()) {
            if (robot instanceof LittleRobot) littleRobot = (LittleRobot)robot;
            if (robot instanceof BigRobot) bigRobot = (BigRobot)robot;
        }

        littleRobot.addRobotMoveListener(_gameField.getExitCell());
        littleRobot.addRobotMoveListener(bigRobot);

        littleRobot.addRobotDestroyListener(this);

        _gameField.getExitCell().addExitCellListener(this);
    }

    // ----------------------------------------------- Наблюдение за точкой выхода -------------------------------------
    @Override
    public void robotTeleported(RobotTeleportEvent e) {
        setGameStatus(GameStatus.WINNER_IS_LITTLE_ROBOT);
    }

    // ----------------------------------------------- Наблюдение за существованием маленького робота ------------------
    @Override
    public void robotDestroyed(RobotDestroyEvent e) {
        setGameStatus(GameStatus.WINNER_IS_BIG_ROBOT);
    }

    // ----------------------------------------------- Порождение события ----------------------------------------------
    private final List<GameStatusChangedListener> _listeners = new ArrayList<>();

    public void addGameStatusChangedListener(GameStatusChangedListener l) {
        _listeners.add(l);
    }

    public void removeGameStatusChangedListener(GameStatusChangedListener l) {
        _listeners.remove(l);
    }

    protected void fireGameStatusChange() {
        for (GameStatusChangedListener l : _listeners) {
            l.gameStatusChanged(new EventObject(_gameStatus));
        }
    }
}
