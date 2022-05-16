package model.field.fieldObjects.landscape;

import model.field.Cell;
import model.field.ExitCell;
import model.field.fieldObjects.CellItem;

public abstract class LandscapeSegment extends CellItem {
    protected Cell _position = null;

    public boolean setPosition(Cell position) {
        if (!canBeLocatedAtPosition(position)) return false;
        if (_position == position) return true;
        _position = position;
        position.setLandscapeSegment(this);
        return true;
    }

    public boolean canBeLocatedAtPosition(Cell position) {
        return !(position instanceof ExitCell);
    }
}
