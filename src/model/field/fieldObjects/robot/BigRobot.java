package model.field.fieldObjects.robot;

import model.events.LittleRobotEndStepEvent;
import model.field.Cell;
import model.field.Direction;
import model.field.MyPoint;
import model.field.fieldObjects.Destroyable;
import model.field.fieldObjects.landscape.SwampSegment;
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

    public void setTarget(Destroyable target) {
        _target = target;
    }

    private void catchTarget() {
        if (_target != null) {
            _target.destroy();
        }
    }

    // ----------------------------------------------- Перемещение -----------------------------------------------------
    private void makeStep() {
        if (_target == null) return;
        Direction directionToMove = calculateStepDirection(((LittleRobot) _target).getPosition());
        makeStep(directionToMove);
    }

    @Override
    protected boolean makeStep(Direction direction) {
        if (_characteristic != null) {
            processMoveCharacteristic();
            if (_characteristic instanceof ViscosityCharacteristic) {
                return false;
            }
        }

        if (!super.makeStep(direction) && direction != null) {
            if (_target != null && _position.getNeighbourCell(direction) == ((LittleRobot) _target).getPosition()) {
                catchTarget();
                return true;
            }
            return false;
        }
        return true;
    }

    private Direction calculateStepDirection(Cell targetPosition) {
        if (targetPosition == null) return null;
        Direction directionToMove = null;
        MyPoint distanceToTarget = _position.getDistanceFromCellToCell(targetPosition);
        if (distanceToTarget.getX() <= 2 && distanceToTarget.getY() <= 2) {
            var pathToMove = _pathFinder.findPath(getPosition(), targetPosition);
            if (!pathToMove.isEmpty()) {
                pathToMove.pop();
                directionToMove = _position.getNeighbourDirection(pathToMove.pop());
            }
        } else {
            Direction calculatedDirectionToMove = _position.getDirectionsToMove(targetPosition).pop();
            if (calculatedDirectionToMove == Direction.EAST || calculatedDirectionToMove == Direction.WEST) {
                directionToMove = calculatedDirectionToMove;
            }
        }
        return directionToMove;
    }

    // ----------------------------------------------- Работа с ландшафтом ---------------------------------------------

    @Override
    protected void processIfLandscapeSegment() {
        if (_position.getLandscapeSegment() instanceof SwampSegment) {
            setLandscapeCharacteristic(new ViscosityCharacteristic(MoveCharacteristicCoefficients.SwampViscosityCoefficient));
        }
    }

    // ----------------------------------------------- Наблюдение за перемещением маленького робота --------------------
    @Override
    public void littleRobotEndedStep(LittleRobotEndStepEvent e) {
        makeStep();
    }

    // ----------------------------------------------- Коэффициенты характеристик передвижения -------------------------
    protected static class MoveCharacteristicCoefficients {
        protected static int SwampViscosityCoefficient = 3;
    }
}
