package model.field.fieldObjects.robot;

import model.events.RobotDestroyEvent;
import model.field.Cell;
import model.field.fieldObjects.Destroyable;
import model.field.fieldObjects.landscape.SwampSegment;
import model.listeners.RobotDestroyListener;

import java.util.ArrayList;
import java.util.List;

public class LittleRobot extends Robot implements Destroyable {

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
        Cell whereDestroyedCell = _position;
        _position.takeRobot();
        fireRobotDestroy(whereDestroyedCell);
    }

    // ----------------------------------------------- Порождение события при уничтожении ------------------------------

    private final List<RobotDestroyListener> _destroyListeners = new ArrayList<>();

    public void addRobotDestroyListener(RobotDestroyListener l) {
        _destroyListeners.add(l);
    }

    public void removeRobotDestroyListener(RobotDestroyListener l) {
        _destroyListeners.remove(l);
    }

    protected void fireRobotDestroy(Cell whereDestroyedCell) {
        for (RobotDestroyListener l : _destroyListeners) {
            RobotDestroyEvent e = new RobotDestroyEvent(this);
            e.setWhereDestroyedCell(whereDestroyedCell);
            e.setDestroyedRobot(this);
            l.robotDestroyed(e);
        }
    }
}
