package ui;

import model.events.*;
import model.field.Cell;
import model.field.ExitCell;
import model.field.Field;
import model.field.fieldObjects.robot.LittleRobot;
import model.field.fieldObjects.robot.Robot;
import model.field.weather.SeasonController;
import model.game.Game;
import model.game.GameStatus;
import model.listeners.*;
import org.jetbrains.annotations.NotNull;
import ui.field.FieldWidget;
import ui.field.cell.CellWidget;
import ui.field.cell.cellItems.CellItemWidget;
import ui.field.cell.cellItems.landscape.LandscapeSegmentWidget;
import ui.field.cell.cellItems.robots.LittleRobotWidget;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class GameWidget extends JPanel {
    private final Game _game;

    private final HashMap<Field, FieldWidget> _fields = new HashMap<>();

    public GameWidget(@NotNull Game game) {
        _game = game;
        _fields.put(_game.getGameField(), new FieldWidget(_game.getGameField(), new WidgetFactory()));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(_fields.get(_game.getGameField()));

        subscribeOnRobots();
        subscribeOnExitCell();
        subscribeOnWeatherController();
    }

    private void subscribeOnRobots() {
        List<Robot> robots = _game.getRobotsOnField();
        RobotController robotController = new RobotController();
        for(Robot robot : robots) {
            robot.addRobotMoveListener(0, robotController);
            if (robot instanceof LittleRobot) {
                ((LittleRobot) robot).addRobotDestroyListener(0, robotController);
                ((LittleRobot) robot).addRobotEndStepListener(new LittleRobotEndStepController());
            }
        }
    }

    private void subscribeOnExitCell() {
        ExitCell exitCell = _game.getGameField().getExitCell();
        exitCell.addExitCellListener(0, new ExitCellController());
    }

    private void subscribeOnWeatherController() {
        SeasonController seasonController = _game.getGameField().getSeasonController();
        if (_game.getGameField().getSeasonController() != null) {
            seasonController.addWeatherChangeListener(0, new WeatherController());
        }
    }

    private class RobotController implements RobotMoveListener, RobotDestroyListener {

        @Override
        public void robotDestroyed(RobotDestroyEvent e) {
            CellItemWidget robotWidget = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(e.getDestroyedRobot());

            ((LittleRobotWidget)robotWidget).setDestroyedRobotImage();

            robotWidget.repaint();
        }

        @Override
        public void robotMadeMove(RobotMoveEvent e) {
            CellItemWidget robotWidget = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(e.getRobot());
            CellWidget from = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(e.getFromCell());
            CellWidget to = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(e.getToCell());
            from.removeItem(robotWidget);
            to.addItem(robotWidget);
            robotWidget.repaint();
        }
    }

    private class ExitCellController implements ExitCellListener {

        @Override
        public void robotTeleported(RobotTeleportEvent e) {
            Robot robot = e.getTeleportedRobot();
            Cell teleport = e.getTeleportCell();
            CellWidget teleportWidget = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(teleport);
            CellItemWidget robotWidget = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(robot);
            teleportWidget.removeItem(robotWidget);
        }
    }

    private class LittleRobotEndStepController implements LittleRobotEndStepListener {

        @Override
        public void littleRobotEndedStep(LittleRobotEndStepEvent e) {
            if (_game.getGameStatus() == GameStatus.GAME_IS_ON) {
                CellItemWidget robotWidget = _fields.get(_game.getGameField()).getWidgetFactory().getWidget((LittleRobot) e.getSource());
                robotWidget.requestFocusInWindow();
            }
        }
    }

    private class WeatherController implements WeatherChangeListener {

        @Override
        public void weatherChanged(WeatherChangeEvent e) {
            for (var cell : e.getChangedLandscape()) {
                CellWidget cellWidget = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(cell);
                LandscapeSegmentWidget oldLandscape = (LandscapeSegmentWidget) cellWidget.getItem(CellWidget.Layer.BOTTOM);
                if (oldLandscape != null) {
                    cellWidget.removeItem(oldLandscape);
                    _fields.get(_game.getGameField()).getWidgetFactory().remove(oldLandscape);
                }
                if (cell.getLandscapeSegment() != null) {
                    CellItemWidget newLandscape = _fields.get(_game.getGameField()).getWidgetFactory().create(cell.getLandscapeSegment());
                    cellWidget.addItem(newLandscape);
                }
            }
            updateUI();
        }
    }
}
