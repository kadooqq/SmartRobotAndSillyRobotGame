package model.field.fieldObjects.robot;

import model.events.LittleRobotEndStepEvent;
import model.events.RobotDestroyEvent;
import model.field.Cell;
import model.field.Direction;
import model.field.fieldObjects.Destroyable;
import model.field.fieldObjects.landscape.SwampSegment;
import model.listeners.LittleRobotEndStepListener;
import model.listeners.RobotDestroyListener;

import java.util.ArrayList;
import java.util.List;

public class LittleRobot extends Robot implements Destroyable {

    // ----------------------------------------------- Перемещение -----------------------------------------------------

    @Override
    public boolean makeStep(Direction direction) {
        boolean isSuccessfulStep = super.makeStep(direction);
        if (!isSuccessfulStep) {
            if (_position.getWallSegment(direction) == null && _position.getNeighbourCell(direction) != null
                    &&_position.getNeighbourCell(direction).getRobot() instanceof BigRobot) {
                destroy();
                isSuccessfulStep = true;
            }
        }
        fireLittleRobotEndStep();
        return isSuccessfulStep;
    }

    // ----------------------------------------------- Работа с ландшафтом ---------------------------------------------
    @Override
    protected void processIfLandscapeSegment() {
        if (_position != null && _position.getLandscapeSegment() != null && _position.getLandscapeSegment() instanceof SwampSegment) {
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

    public void addRobotDestroyListener(int index, RobotDestroyListener l) {
        _destroyListeners.add(index, l);
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

    // ----------------------------------------------- Порождение события об окончании хода ----------------------------

    private final List<LittleRobotEndStepListener> _endStepListeners = new ArrayList<>();

    public void addRobotEndStepListener(LittleRobotEndStepListener l) {
        _endStepListeners.add(l);
    }

    public void addRobotEndStepListener(int index, LittleRobotEndStepListener l) {
        _endStepListeners.add(index, l);
    }

    public void removeRobotDestroyListener(LittleRobotEndStepListener l) {
        _endStepListeners.remove(l);
    }

    protected void fireLittleRobotEndStep() {
        for (LittleRobotEndStepListener l : _endStepListeners) {
            l.littleRobotEndedStep(new LittleRobotEndStepEvent(this));
        }
    }
}
