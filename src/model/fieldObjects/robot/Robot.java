package model.fieldObjects.robot;

import model.events.RobotMoveEvent;
import model.field.Cell;
import model.field.Direction;
import model.listeners.RobotMoveListener;

import java.util.ArrayList;
import java.util.List;

public abstract class Robot {

    // ----------------------------------------------- Позиция ---------------------------------------------------------
    protected Cell _position = null;

    public boolean setPosition(Cell cell) {
        if (cell == null) return false;
        if (_position == cell) return true;
        if (!canBeLocatedAtPosition(cell) && cell.getRobot() != this) return false;
        _position = cell;
        return cell.setRobot(this);
    }

    public void setNullPosition() {
        _position = null;
    }

    public Cell getPosition() {
        return _position;
    }

    public static boolean canBeLocatedAtPosition(Cell cell) {
        return cell != null && cell.getRobot() == null;
    }

    // ----------------------------------------------- Перемещение -----------------------------------------------------
    protected Cell canMove(Direction direction) {
        if (direction == null || _position == null) return null;
        Cell neighbourCell = _position.getNeighbourCell(direction);
        if (neighbourCell != null && canBeLocatedAtPosition(_position.getNeighbourCell(direction))
                && _position.getWallSegment(direction) == null) {
            return neighbourCell;
        }
        return null;
    }

    protected boolean move(Direction direction) {
        Cell cellToMove = canMove(direction);
        if (cellToMove == null) return false;
        getPosition().takeRobot();
        if (setPosition(cellToMove)) {
            processIfLandscapeSegment();
            return true;
        }
        return false;
    }

    // ----------------------------------------------- Работа с ландшафтом ---------------------------------------------
    protected abstract void processIfLandscapeSegment();

    // ----------------------------------------------- Генерация события при перемещении -------------------------------
    private final List<RobotMoveListener> _moveListeners = new ArrayList<>();

    public void addRobotMoveListener(RobotMoveListener l) {
        _moveListeners.add(l);
    }

    public void addRobotMoveListener(int index, RobotMoveListener l) {
        _moveListeners.add(index, l);
    }

    public void removeRobotMoveListener(RobotMoveListener l) {
        _moveListeners.remove(l);
    }

    protected void fireRobotMove(Cell fromCell) {
        for (RobotMoveListener l : _moveListeners) {
            RobotMoveEvent e = new RobotMoveEvent(this);
            e.setRobot(this);
            e.setFromCell(fromCell);
            e.setToCell(_position);
            l.robotMadeMove(e);
        }
    }
}
