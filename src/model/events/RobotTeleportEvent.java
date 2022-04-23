package model.events;

import model.field.ExitCell;
import model.field.fieldObjects.robot.Robot;

import java.util.EventObject;

public class RobotTeleportEvent extends EventObject {

    public RobotTeleportEvent(Object source) {
        super(source);
    }

    private Robot _teleportedRobot;

    public void setTeleportedRobot(Robot teleportedRobot) {
        _teleportedRobot = teleportedRobot;
    }

    public Robot getTeleportedRobot() {
        return _teleportedRobot;
    }

    private ExitCell _teleportCell;

    public void setTeleportCell(ExitCell teleportCell) {
        _teleportCell = teleportCell;
    }

    public ExitCell getTeleportCell() {
        return _teleportCell;
    }
}
