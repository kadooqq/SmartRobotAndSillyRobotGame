package model.field.fieldObjects.robot.moveCharacteristics;

public abstract class MoveCharacteristic {
    public MoveCharacteristic(double coefficient) {
        if (coefficient < 0) {
            throw new IllegalArgumentException("Невозможно создать характеристику ландшафта отрицательной");
        }
        _coefficient = coefficient;
    }

    private double _coefficient;

    public double getCoefficient() {
        return _coefficient;
    }
}
