package model.events;

import model.field.Cell;
import model.fieldObjects.robot.Robot;

import java.util.EventObject;

public class RobotMoveEvent extends EventObject {
    public RobotMoveEvent(Object source) {
        super(source);
    }

    // Робот, порождающий событие
    private Robot _robot;

    public void setRobot(Robot robot) {
        _robot = robot;
    }

    public Robot getRobot() {
        return _robot;
    }

    // Ячейка, из которой робот перемещается
    private Cell _fromCell;

    public void setFromCell(Cell fromCell) {
        _fromCell = fromCell;
    }

    public Cell getFromCell() {
        return _fromCell;
    }

    // Ячейка, в которую робот перемещается
    private Cell _toCell;

    public void setToCell(Cell toCell) {
        _toCell = toCell;
    }

    public Cell getToCell() {
        return _toCell;
    }
}
