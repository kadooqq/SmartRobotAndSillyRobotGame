package model.fieldObjects.landscape;

import model.field.Cell;
import model.fieldObjects.landscape.characteristics.LandscapeCharacteristic;

public abstract class LandscapeSegment {
    protected Cell _position = null;

    public void setPosition(Cell position) {
        if (_position == position) return;
        _position = position;
        position.setLandscapeSegment(this);
    }
    protected LandscapeCharacteristic _characteristic = null;

    public LandscapeCharacteristic getCharacteristic() {
        return _characteristic;
    }
}
