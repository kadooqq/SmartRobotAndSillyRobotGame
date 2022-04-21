package model.fieldObjects.robot;

import model.gameStuff.PathFinder;
import model.field.Cell;
import model.field.Direction;
import model.field.MyPoint;
import model.fieldObjects.Destroyable;
import model.fieldObjects.landscape.SwampSegment;
import model.fieldObjects.landscape.characteristics.LandscapeCharacteristic;
import model.fieldObjects.landscape.characteristics.ViscosityCharacteristic;
import model.listeners.RobotMoveListener;

import java.util.EventObject;

public class BigRobot extends Robot implements RobotMoveListener {
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
        if (_characteristic != null && _characteristic instanceof ViscosityCharacteristic && _numOfStepsWithCharacteristic > 0
                || _target == null) {
            if (_numOfStepsWithCharacteristic > 0) {
                --_numOfStepsWithCharacteristic;
            }
            if (_numOfStepsWithCharacteristic == 0) {
                _characteristic = null;
            }
            return;
        }
        Direction directionToMove = calculateStepDirection(((LittleRobot) _target).getPosition());
        if (!move(directionToMove) && directionToMove != null && _position.getNeighbourCell(directionToMove) == ((LittleRobot) _target)._position) {
            catchTarget();
        }
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
    private LandscapeCharacteristic _characteristic = null;
    private int _numOfStepsWithCharacteristic = 0;

    @Override
    protected void processIfLandscapeSegment() {
        if (_position.getLandscapeSegment() != null && _position.getLandscapeSegment() instanceof SwampSegment) {
            setLandscapeCharacteristic(_position.getLandscapeSegment().getCharacteristic());
        }
    }

    private void setLandscapeCharacteristic(LandscapeCharacteristic characteristic) {
        _characteristic = characteristic;
        _numOfStepsWithCharacteristic = (int) Math.floor(_characteristic.getCoefficient());
    }

    // ----------------------------------------------- Наблюдение за перемещением маленького робота --------------------
    @Override
    public void robotMadeMove(EventObject e) {
        makeStep();
    }
}
