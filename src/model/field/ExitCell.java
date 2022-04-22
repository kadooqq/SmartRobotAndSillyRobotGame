package model.field;

import model.events.RobotMoveEvent;
import model.events.RobotTeleportEvent;
import model.fieldObjects.robot.LittleRobot;
import model.fieldObjects.robot.Robot;
import model.listeners.ExitCellListener;
import model.listeners.RobotMoveListener;

import java.util.ArrayList;
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
    public void robotMadeMove(RobotMoveEvent e) {
        if (getRobot() != null && getRobot() instanceof LittleRobot && getRobot() == e.getRobot()) {
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
            RobotTeleportEvent e = new RobotTeleportEvent(this);
            e.setTeleportedRobot(robot);
            e.setTeleportCell(this);
            l.robotTeleported(e);
        }
    }
}
