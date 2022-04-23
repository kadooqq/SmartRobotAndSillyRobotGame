package model.field.fieldObjects.landscape;

import model.field.fieldObjects.landscape.characteristics.ViscosityCharacteristic;

public class SwampSegment extends LandscapeSegment {
    public SwampSegment(double characteristicCoefficient) {
        _characteristic = new ViscosityCharacteristic(characteristicCoefficient);
    }
}
