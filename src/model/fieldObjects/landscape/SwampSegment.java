package model.fieldObjects.landscape;

import model.fieldObjects.landscape.characteristics.ViscosityCharacteristic;

public class SwampSegment extends LandscapeSegment {
    public SwampSegment(double characteristicCoefficient) {
        _characteristic = new ViscosityCharacteristic(characteristicCoefficient);
    }
}
