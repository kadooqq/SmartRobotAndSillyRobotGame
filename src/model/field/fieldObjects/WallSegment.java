package model.field.fieldObjects;

import model.field.BetweenCellPosition;

public class WallSegment {
    private BetweenCellPosition _position = null;

    public boolean setPosition(BetweenCellPosition betweenCellPosition) {
        if (betweenCellPosition == null) return false;
        if (_position == betweenCellPosition) return true;
        var entryCell = betweenCellPosition.getNeighbourCells().firstEntry();
        if (!canCreateAtPosition(betweenCellPosition)
                && entryCell.getValue().getWallSegment(entryCell.getKey().getOppositeDirection()) != this) return false;
        _position = betweenCellPosition;
        for (var neighbourCell : _position.getNeighbourCells().entrySet()) {
            neighbourCell.getValue().setWallSegment(this, neighbourCell.getKey().getOppositeDirection());
        }
        return true;
    }

    public BetweenCellPosition getPosition() {
        return _position;
    }

    public static boolean canCreateAtPosition(BetweenCellPosition position) {
        if (position == null) return false;
        for (var cell : position.getNeighbourCells().entrySet()) {
            if (cell.getValue().getWallSegment(cell.getKey().getOppositeDirection()) != null) return false;
        }
        return true;
    }
}
