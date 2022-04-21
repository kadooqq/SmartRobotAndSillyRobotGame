package model.fieldObjects.robot;

import model.field.Direction;
import model.fieldObjects.Destroyable;
import model.fieldObjects.landscape.SwampSegment;
import model.listeners.RobotDestroyListener;
import model.listeners.RobotMoveListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class LittleRobot extends Robot implements Destroyable {

    // ----------------------------------------------- Перемещение -----------------------------------------------------
    @Override
    public boolean move(Direction direction) {
        if (super.move(direction)) {
            fireRobotMove();
            return true;
        }
        return false;
    }

    // ----------------------------------------------- Работа с ландшафтом ---------------------------------------------
    @Override
    protected void processIfLandscapeSegment() {
        if (_position.getLandscapeSegment() != null && _position.getLandscapeSegment() instanceof SwampSegment) {
            destroy();
        }
    }

    // ----------------------------------------------- Уничтожение -----------------------------------------------------
    @Override
    public void destroy() {
        _position.takeRobot();
        _position = null;
        fireRobotDestroy();
    }

    // ----------------------------------------------- Порождение события ----------------------------------------------

    // ----------------------------------------------- при уничтожении
    private final List<RobotDestroyListener> _destroyListeners = new ArrayList<>();

    public void addRobotDestroyListener(RobotDestroyListener l) {
        _destroyListeners.add(l);
    }

    public void removeRobotDestroyListener(RobotDestroyListener l) {
        _destroyListeners.remove(l);
    }

    protected void fireRobotDestroy() {
        for (RobotDestroyListener l : _destroyListeners) {
            l.robotDestroyed(new EventObject(this));
        }
    }

    // ----------------------------------------------- при перемещении
    private final List<RobotMoveListener> _moveListeners = new ArrayList<>();

    public void addRobotMoveListener(RobotMoveListener l) {
        _moveListeners.add(l);
    }

    public void removeRobotMoveListener(RobotMoveListener l) {
        _moveListeners.remove(l);
    }

    protected void fireRobotMove() {
        for (RobotMoveListener l : _moveListeners) {
            l.robotMadeMove(new EventObject(this));
        }
    }
}
