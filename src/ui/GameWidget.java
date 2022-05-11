package ui;

import model.events.RobotDestroyEvent;
import model.events.RobotMoveEvent;
import model.events.RobotTeleportEvent;
import model.field.Cell;
import model.field.ExitCell;
import model.field.Field;
import model.field.fieldObjects.robot.LittleRobot;
import model.field.fieldObjects.robot.Robot;
import model.game.Game;
import model.listeners.ExitCellListener;
import model.listeners.RobotDestroyListener;
import model.listeners.RobotMoveListener;
import org.jetbrains.annotations.NotNull;
import ui.field.cell.CellWidget;
import ui.field.cell.ExitCellWidget;
import ui.field.cell.cellItems.CellItemWidget;
import ui.field.FieldWidget;

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
    }

    private void subscribeOnRobots() {
        List<Robot> robots = _game.getRobotsOnField();
        RobotController robotController = new RobotController();
        for(Robot robot : robots) {
            robot.addRobotMoveListener(0, robotController);
            if (robot instanceof LittleRobot) {
                ((LittleRobot) robot).addRobotDestroyListener(0, robotController);
            }
        }
    }

    private void subscribeOnExitCell() {
        ExitCell exitCell = _game.getGameField().getExitCell();
        exitCell.addExitCellListener(0, new ExitCellController());
    }

    private class RobotController implements RobotMoveListener, RobotDestroyListener {

        @Override
        public void robotDestroyed(RobotDestroyEvent e) {
            CellItemWidget robotWidget = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(e.getDestroyedRobot());
            CellWidget cellWhereDestroyedWidget = _fields.get(_game.getGameField()).getWidgetFactory().getWidget(e.getCellWhereDestroyed());

            cellWhereDestroyedWidget.removeItem(robotWidget);

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

            //TODO баг с UI!!!!!!!!
            if (e.getRobot() instanceof LittleRobot && !(to instanceof ExitCellWidget)) {
                robotWidget.requestFocusInWindow();
            }
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
}
