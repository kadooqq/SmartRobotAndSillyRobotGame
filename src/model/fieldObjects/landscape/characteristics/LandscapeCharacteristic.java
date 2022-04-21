package model.fieldObjects.landscape.characteristics;

public abstract class LandscapeCharacteristic {
    public LandscapeCharacteristic(double coefficient) {
        _coefficient = coefficient;
    }

    private double _coefficient;

    public double getCoefficient() {
        return _coefficient;
    }
}
