package model.fieldObjects.landscape;

import model.field.Cell;
import model.field.ExitCell;
import model.fieldObjects.CellItem;
import model.fieldObjects.landscape.characteristics.LandscapeCharacteristic;

public abstract class LandscapeSegment extends CellItem {
    protected Cell _position = null;

    public boolean setPosition(Cell position) {
        if (canBeLocatedAtPosition(position))
        if (_position == position) return true;
        _position = position;
        position.setLandscapeSegment(this);
        return true;
    }

    public boolean canBeLocatedAtPosition(Cell position) {
        if (position instanceof ExitCell) return false;
        return true;
    }

    protected LandscapeCharacteristic _characteristic = null;

    public LandscapeCharacteristic getCharacteristic() {
        return _characteristic;
    }
}
