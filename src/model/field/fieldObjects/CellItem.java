package model.field.fieldObjects;

import model.field.Cell;

public abstract class CellItem {

    protected Cell _position;

    public Cell getPosition() {
        return _position;
    }

    public abstract boolean setPosition(Cell position);

    public abstract boolean canBeLocatedAtPosition(Cell cell);
}
