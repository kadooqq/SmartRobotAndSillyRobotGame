package model.events;

import model.field.Cell;
import model.field.fieldObjects.robot.Robot;

import java.util.EventObject;

public class RobotDestroyEvent extends EventObject {

    public RobotDestroyEvent(Object source) {
        super(source);
    }

    private Robot _destroyedRobot;

    public Robot getDestroyedRobot() {
        return _destroyedRobot;
    }

    public void setDestroyedRobot(Robot destroyedRobot) {
        _destroyedRobot = destroyedRobot;
    }

    private Cell _whereDestroyedCell;

    public Cell getCellWhereDestroyed() {
        return _whereDestroyedCell;
    }

    public void setWhereDestroyedCell(Cell whereDestroyedCell) {
        _whereDestroyedCell = whereDestroyedCell;
    }
}
