package model.field.fieldObjects.robot;

import model.events.RobotMoveEvent;
import model.field.Cell;
import model.field.Direction;
import model.field.fieldObjects.CellItem;
import model.field.fieldObjects.robot.moveCharacteristics.MoveCharacteristic;
import model.listeners.RobotMoveListener;

import java.util.ArrayList;
import java.util.List;

public abstract class Robot extends CellItem {

    // ----------------------------------------------- Позиция ---------------------------------------------------------
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

    public boolean canBeLocatedAtPosition(Cell cell) {
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

    protected boolean makeStep(Direction direction) {
        if (move(direction)) {
            processIfLandscapeSegment();
            return true;
        }
        return false;
    }

    private boolean move(Direction direction) {
        Cell cellToMove = canMove(direction);
        if (cellToMove == null) return false;
        Cell fromCell = getPosition();
        getPosition().takeRobot();
        if (setPosition(cellToMove)) {
            fireRobotMove(fromCell, cellToMove);
            return true;
        }
        return false;
    }

    // ----------------------------------------------- Работа с ландшафтом ---------------------------------------------
    protected MoveCharacteristic _characteristic = null;
    protected int _numOfStepsWithCharacteristic = 0;

    protected abstract void processIfLandscapeSegment();

    protected void processMoveCharacteristic() {
        if (_characteristic != null && _numOfStepsWithCharacteristic == 0) {
            _characteristic = null;
        }
        if (_numOfStepsWithCharacteristic > 0) {
            --_numOfStepsWithCharacteristic;
        }
    }

    protected void setLandscapeCharacteristic(MoveCharacteristic characteristic) {
        _characteristic = characteristic;
        _numOfStepsWithCharacteristic = (int) Math.floor(_characteristic.getCoefficient());
    }

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

    protected void fireRobotMove(Cell fromCell, Cell toCell) {
        for (RobotMoveListener l : _moveListeners) {
            RobotMoveEvent e = new RobotMoveEvent(this);
            e.setRobot(this);
            e.setFromCell(fromCell);
            e.setToCell(toCell);
            l.robotMadeMove(e);
        }
    }

    // ----------------------------------------------- Коэффициенты характеристик передвижения -------------------------
    protected static class MoveCharacteristicCoefficients {
        protected static int SwampViscosityCoefficient = 0;
    }
}
