package model.field.fieldObjects.robot;

import model.events.RobotMoveEvent;
import model.events.WeatherChangeEvent;
import model.field.Cell;
import model.field.Direction;
import model.field.fieldObjects.CellItem;
import model.field.fieldObjects.landscape.LandscapeSegment;
import model.field.fieldObjects.robot.moveCharacteristics.MoveCharacteristic;
import model.field.fieldObjects.robot.moveCharacteristics.SlipperinessCharacteristic;
import model.field.fieldObjects.robot.moveCharacteristics.ViscosityCharacteristic;
import model.listeners.RobotMoveListener;
import model.listeners.WeatherChangeListener;

import java.util.ArrayList;
import java.util.List;

public abstract class Robot extends CellItem implements WeatherChangeListener {

    // ----------------------------------------------- Позиция ---------------------------------------------------------
    public boolean setPosition(Cell cell) {
        if (cell == null) return false;

        if (_position == cell) return true;

        boolean isRobotCanToBeLocatedAtNewPosition = canBeLocatedAtPosition(cell) || cell.getRobot() == this;
        if (!isRobotCanToBeLocatedAtNewPosition) return false;

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

        boolean canMoveAtNeighbourCell = neighbourCell != null
                && canBeLocatedAtPosition(_position.getNeighbourCell(direction))
                && _position.getWallSegment(direction) == null;

        if (canMoveAtNeighbourCell) {
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

        boolean isRobotSuccessfullySetAtNewPosition = setPosition(cellToMove);

        if (isRobotSuccessfullySetAtNewPosition) {
            fireRobotMove(fromCell, cellToMove);
        }

        return isRobotSuccessfullySetAtNewPosition;
    }

    protected boolean canMoveAfterProcessingMoveCharacteristic() {
        boolean isRobotStuck = robotHasViscosityCharacteristic();
        return !(isRobotStuck);
    }

    // ----------------------------------------------- Работа с ландшафтом ---------------------------------------------
    protected MoveCharacteristic _characteristic = null;

    public abstract void processIfLandscapeSegment();

    protected void processBeforeMovingCharacteristic() {
        boolean isBeforeMovingCharacteristic = robotHasViscosityCharacteristic();

        if (!isBeforeMovingCharacteristic) return;

        boolean isCharacteristicSetButLandscapeSegmentNotUnderTheRobot = (_position.getLandscapeSegment() == null);

        boolean isLifeTimeOfCharacteristicEnded = !_characteristic.decrementLifeTime();

        if (isCharacteristicSetButLandscapeSegmentNotUnderTheRobot || isLifeTimeOfCharacteristicEnded) {
            _characteristic = null;
        }
    }

    protected void processAfterMovingCharacteristic() {
        boolean isAfterMovingCharacteristic = robotHasSlipperinessCharacteristic();

        if (!isAfterMovingCharacteristic) return;

        boolean isCharacteristicSetButLandscapeSegmentNotUnderTheRobot = (_position.getLandscapeSegment() == null);

        boolean isLifeTimeOfCharacteristicEnded = !_characteristic.decrementLifeTime();

        if (isCharacteristicSetButLandscapeSegmentNotUnderTheRobot || isLifeTimeOfCharacteristicEnded) {
            _characteristic = null;
        }
    }

    protected void setLandscapeCharacteristic(MoveCharacteristic characteristic) {
        boolean newAndOldCharacteristicAreSlipperiness = (robotHasSlipperinessCharacteristic()
                                                        && characteristic instanceof SlipperinessCharacteristic);

        if (characteristic == null || newAndOldCharacteristicAreSlipperiness) return;

        _characteristic = characteristic;
    }

    protected boolean robotHasViscosityCharacteristic() {
        return _characteristic instanceof ViscosityCharacteristic;
    }

    protected boolean robotHasSlipperinessCharacteristic() {
        return _characteristic instanceof SlipperinessCharacteristic;
    }

    protected abstract MoveCharacteristic createMoveCharacteristic(LandscapeSegment landscapeSegment);

    // ----------------------------------------------- Генерация события при перемещении -------------------------------
    private final List<RobotMoveListener> _moveListeners = new ArrayList<>();

    public void addRobotMoveListener(RobotMoveListener l) {
        if (l == null) return;
        _moveListeners.add(l);
    }

    public void addRobotMoveListener(int index, RobotMoveListener l) {
        if (l == null) return;
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

    public void weatherChanged(WeatherChangeEvent e) {
        processIfLandscapeSegment();
    }

    // ----------------------------------------------- Коэффициенты характеристик передвижения -------------------------
    protected static class MoveCharacteristicCoefficients {
        protected static final int SwampViscosityCoefficient = 0;
        protected static final int SandViscosityCoefficient = 0;
        protected static final int IceSlipperinessCoefficient = 0;
    }
}
