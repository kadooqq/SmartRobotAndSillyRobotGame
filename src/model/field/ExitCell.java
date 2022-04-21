package model.field;

import model.fieldObjects.robot.Robot;
import model.listeners.ExitCellListener;
import model.listeners.RobotMoveListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class ExitCell extends Cell implements RobotMoveListener {
    public ExitCell(MyPoint coordinates) {
        super(coordinates);
    }

    private void teleportRobot() {
        getRobot().setNullPosition();
        fireLittleRobotTeleport(takeRobot());
    }

    // ----------------------------------------------- Наблюдение за перемещением маленького робота --------------------
    @Override
    public void robotMadeMove(EventObject e) {
        if (getRobot() != null && getRobot() == e.getSource()) {
            teleportRobot();
        }
    }

    // ----------------------------------------------- Порождение события ----------------------------------------------
    private final List<ExitCellListener> _listeners = new ArrayList<>();

    public void addExitCellListener(ExitCellListener l) {
        _listeners.add(l);
    }

    public void removeExitCellListener(ExitCellListener l) {
        _listeners.remove(l);
    }

    protected void fireLittleRobotTeleport(Robot robot) {
        for (ExitCellListener l : _listeners) {
            l.robotTeleported(new EventObject(robot));
        }
    }
}
