package model.field.fieldObjects.robot;

import model.events.LittleRobotEndStepEvent;
import model.field.Cell;
import model.field.Direction;
import model.field.MyPoint;
import model.field.fieldObjects.CellItem;
import model.field.fieldObjects.Destroyable;
import model.field.fieldObjects.landscape.IceSegment;
import model.field.fieldObjects.landscape.LandscapeSegment;
import model.field.fieldObjects.landscape.SandSegment;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.fieldObjects.robot.moveCharacteristics.MoveCharacteristic;
import model.field.fieldObjects.robot.moveCharacteristics.SlipperinessCharacteristic;
import model.field.fieldObjects.robot.moveCharacteristics.ViscosityCharacteristic;
import model.gameStuff.PathFinder;
import model.listeners.LittleRobotEndStepListener;

public class BigRobot extends Robot implements LittleRobotEndStepListener {
    private PathFinder _pathFinder;

    public void setPathFinder(PathFinder pathFinder) {
        if (pathFinder == null) return;
        if (_pathFinder == pathFinder) return;
        dropPathFinder();
        _pathFinder = pathFinder;
        _pathFinder.setRobot(this);
    }

    public PathFinder getPathFinder() {
        return _pathFinder;
    }

    public void dropPathFinder() {
        var pathFinder = _pathFinder;
        _pathFinder = null;
        if (pathFinder != null) pathFinder.forgetRobot();
    }

    // ----------------------------------------------- Цель ------------------------------------------------------------
    private Destroyable _target = null;

    private final int TARGET_SIGHT_DISTANCE = 2;

    public void setTarget(Destroyable target) {
        _target = target;
    }

    private void catchTarget() {
        if (_target != null) {
            _target.destroy();
        }
    }

    private boolean checkIfTargetInNeighbourCell(Direction direction) {
        return _target != null && _position.getNeighbourCell(direction) == getTargetPosition();
    }

    private Cell getTargetPosition() {
        if (_target == null) return null;
        return ((CellItem)_target).getPosition();
    }

    // ----------------------------------------------- Перемещение -----------------------------------------------------
    private void makeStep() {
        if (_target == null) return;
        Direction directionToMove = calculateStepDirection(getTargetPosition());
        makeStep(directionToMove);
    }

    @Override
    protected boolean makeStep(Direction direction) {
        boolean isSuccessfulStep = false;

        boolean isRobotSlides;
        do {
            processBeforeMovingCharacteristic();

            if (!canMoveAfterProcessingMoveCharacteristic()) return isSuccessfulStep;

            isSuccessfulStep = super.makeStep(direction) || isSuccessfulStep;

            processAfterMovingCharacteristic();

            if (!isSuccessfulStep && direction != null) {
                if (checkIfTargetInNeighbourCell(direction)) {
                    catchTarget();
                    return true;
                }
            }

            isRobotSlides = robotHasSlipperinessCharacteristic();
        } while(isRobotSlides);

        return isSuccessfulStep;
    }

    private Direction calculateStepDirection(Cell cell) {
        if (cell == null) return null;

        MyPoint distanceToTarget = _position.getDistanceFromCellToCell(cell);

        boolean isDistanceToTargetInRangeOfSight = distanceToTarget.getX() <= TARGET_SIGHT_DISTANCE
                                                && distanceToTarget.getY() <= TARGET_SIGHT_DISTANCE;

        Direction directionToMove;
        if (isDistanceToTargetInRangeOfSight) {
            directionToMove = getDirectionToMoveToCellUsingPathFinder(cell);
        } else {
            directionToMove = getDirectionToMoveHorizontally(cell);
        }
        return directionToMove;
    }

    private Direction getDirectionToMoveToCellUsingPathFinder(Cell cell) {
        var pathToMove = _pathFinder.findPath(getPosition(), cell);

        Direction directionToMove = null;
        if (!pathToMove.isEmpty()) {
            pathToMove.pop();
            directionToMove = _position.getNeighbourDirection(pathToMove.pop());
        }

        return directionToMove;
    }

    private Direction getDirectionToMoveHorizontally(Cell cell) {
        Direction calculatedDirectionToMove = _position.getDirectionsToMove(cell).pop();

        if (calculatedDirectionToMove == Direction.EAST || calculatedDirectionToMove == Direction.WEST) {
            return calculatedDirectionToMove;
        }

        return null;
    }

    // ----------------------------------------------- Работа с ландшафтом ---------------------------------------------

    @Override
    public void processIfLandscapeSegment() {
        if (_position == null) return;
        setLandscapeCharacteristic(createMoveCharacteristic(_position.getLandscapeSegment()));
    }

    protected MoveCharacteristic createMoveCharacteristic(LandscapeSegment landscapeSegment) {
        if (_position.getLandscapeSegment() instanceof SwampSegment) {
            return new ViscosityCharacteristic(MoveCharacteristicCoefficients.SwampViscosityCoefficient);
        } else if (_position.getLandscapeSegment() instanceof SandSegment) {
            return new ViscosityCharacteristic(MoveCharacteristicCoefficients.SandViscosityCoefficient);
        } else if (_position.getLandscapeSegment() instanceof IceSegment) {
            return new SlipperinessCharacteristic(MoveCharacteristicCoefficients.IceSlipperinessCoefficient);
        }

        return null;
    }

    // ----------------------------------------------- Наблюдение за перемещением маленького робота --------------------
    @Override
    public void littleRobotEndedStep(LittleRobotEndStepEvent e) {
        makeStep();
    }

    // ----------------------------------------------- Коэффициенты характеристик передвижения -------------------------
    protected static class MoveCharacteristicCoefficients {
        protected static final int SwampViscosityCoefficient = 3;
        protected static final int SandViscosityCoefficient = 1;
        protected static final int IceSlipperinessCoefficient = 1;
    }
}
