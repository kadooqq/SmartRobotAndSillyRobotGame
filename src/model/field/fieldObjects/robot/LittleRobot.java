package model.field.fieldObjects.robot;

import model.events.RobotDestroyEvent;
import model.field.Cell;
import model.field.Direction;
import model.field.fieldObjects.Destroyable;
import model.field.fieldObjects.landscape.SwampSegment;
import model.listeners.RobotDestroyListener;

import java.util.ArrayList;
import java.util.List;

public class LittleRobot extends Robot implements Destroyable {

    // ----------------------------------------------- Перемещение -----------------------------------------------------
    @Override
    public boolean move(Direction direction) {
        if (!super.move(direction)) {
            if (_position.getWallSegment(direction) == null && _position.getNeighbourCell(direction) != null
                    &&_position.getNeighbourCell(direction).getRobot() instanceof BigRobot) {
                destroy();
                return true;
            }
            return false;
        }
        return true;
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
