package model.field.fieldObjects.robot;

import model.events.LittleRobotEndStepEvent;
import model.events.RobotDestroyEvent;
import model.field.Cell;
import model.field.Direction;
import model.field.fieldObjects.Destroyable;
import model.field.fieldObjects.landscape.IceSegment;
import model.field.fieldObjects.landscape.LandscapeSegment;
import model.field.fieldObjects.landscape.SandSegment;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.fieldObjects.robot.moveCharacteristics.MoveCharacteristic;
import model.field.fieldObjects.robot.moveCharacteristics.SlipperinessCharacteristic;
import model.field.fieldObjects.robot.moveCharacteristics.ViscosityCharacteristic;
import model.listeners.LittleRobotEndStepListener;
import model.listeners.RobotDestroyListener;

import java.util.ArrayList;
import java.util.List;

public class LittleRobot extends Robot implements Destroyable {

    // ----------------------------------------------- Перемещение -----------------------------------------------------

    @Override
    public boolean makeStep(Direction direction) {
        boolean isSuccessfulStep = false;
        boolean isRobotSlides;
        do {
            processBeforeMovingCharacteristic();

            if (!canMoveAfterProcessingMoveCharacteristic()) return isSuccessfulStep;

            isSuccessfulStep = super.makeStep(direction) || isSuccessfulStep;

            processAfterMovingCharacteristic();

            isRobotSlides = _position != null && robotHasSlipperinessCharacteristic();
        } while (isRobotSlides);

        fireLittleRobotEndStep();

        return isSuccessfulStep;
    }

    // ----------------------------------------------- Работа с ландшафтом ---------------------------------------------
    @Override
    public void processIfLandscapeSegment() {
        if (_position == null) return;

        setLandscapeCharacteristic(createMoveCharacteristic(_position.getLandscapeSegment()));

        if (_position.getLandscapeSegment() instanceof SwampSegment) {
            destroy();
        }
    }

    @Override
    protected void processBeforeMovingCharacteristic() {
        super.processBeforeMovingCharacteristic();
        if (robotHasViscosityCharacteristic()) {
            fireLittleRobotEndStep();
        }
    }

    protected MoveCharacteristic createMoveCharacteristic(LandscapeSegment landscapeSegment) {
        if (_position.getLandscapeSegment() instanceof SandSegment) {
            return new ViscosityCharacteristic(MoveCharacteristicCoefficients.SandViscosityCoefficient);
        } else if (_position.getLandscapeSegment() instanceof IceSegment) {
            return new SlipperinessCharacteristic(MoveCharacteristicCoefficients.IceSlipperinessCoefficient);
        }
        return null;
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
        if (l == null) return;
        _destroyListeners.add(l);
    }

    public void addRobotDestroyListener(int index, RobotDestroyListener l) {
        if (l == null) return;
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
        if (l == null) return;
        _endStepListeners.add(l);
    }

    public void addRobotEndStepListener(int index, LittleRobotEndStepListener l) {
        if (l == null) return;
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

    // ----------------------------------------------- Коэффициенты характеристик передвижения -------------------------
    protected static class MoveCharacteristicCoefficients {
        protected final static int SandViscosityCoefficient = 1;
        protected final static int IceSlipperinessCoefficient = 1;
    }
}
