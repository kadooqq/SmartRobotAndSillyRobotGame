package model.game;

import model.events.GameStatusChangeEvent;
import model.events.RobotDestroyEvent;
import model.events.RobotTeleportEvent;
import model.field.Field;
import model.field.fieldObjects.robot.BigRobot;
import model.field.fieldObjects.robot.LittleRobot;
import model.field.fieldObjects.robot.Robot;
import model.labyrinth.Labyrinth;
import model.listeners.ExitCellListener;
import model.listeners.GameStatusChangedListener;
import model.listeners.RobotDestroyListener;

import java.util.ArrayList;
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

        // Добавить прослушку погоды роботами
        if (_gameField.getSeasonController() != null) {
            _gameField.getSeasonController().addWeatherChangeListener(littleRobot);
            _gameField.getSeasonController().addWeatherChangeListener(bigRobot);
        }

        // Добавить прослушку маленького робота точкой выхода
        littleRobot.addRobotMoveListener(_gameField.getExitCell());

        // Добавить прослушку точки выхода полем
        _gameField.getExitCell().addExitCellListener(_gameField);

        // Добавить прослушку точки выхода игрой
        _gameField.getExitCell().addExitCellListener(this);
        // Добавить прослушку уничтожения маленького робота игрой
        littleRobot.addRobotDestroyListener(this);

        // Добавить прослушку конца хода маленького робота большим роботом
        if (bigRobot != null) {
            littleRobot.addRobotEndStepListener(bigRobot);
        }
        // и сезоном
        if (_gameField.getSeasonController() != null) {
            littleRobot.addRobotEndStepListener(_gameField.getSeasonController());
        }
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
        if (l == null) return;
        _listeners.add(l);
    }

    public void removeGameStatusChangedListener(GameStatusChangedListener l) {
        _listeners.remove(l);
    }

    protected void fireGameStatusChange() {
        for (GameStatusChangedListener l : _listeners) {
            GameStatusChangeEvent e = new GameStatusChangeEvent(this);
            e.setGameStatus(_gameStatus);
            l.gameStatusChanged(e);
        }
    }
}
