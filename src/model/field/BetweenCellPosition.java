package model.field;

import java.util.TreeMap;

public class BetweenCellPosition {
    public BetweenCellPosition(Cell cell, Cell neighbourCell) {
        if (cell == null || neighbourCell == null) throw new IllegalArgumentException("Невозможно создать стену в заданном месте");
        Direction directionToMoveFromCellToNeighbourCell = cell.getNeighbourDirection(neighbourCell);
        if (directionToMoveFromCellToNeighbourCell == null)
            throw new IllegalArgumentException("Попытка создать позицию между двумя несоседствующими ячейками");

        _neighbourCells = new TreeMap<>();
        _neighbourCells.put(directionToMoveFromCellToNeighbourCell.getOppositeDirection(), cell);
        _neighbourCells.put(directionToMoveFromCellToNeighbourCell, neighbourCell);
    }

    public BetweenCellPosition(Cell cell, Direction direction) {
        if (cell == null || direction == null) throw new IllegalArgumentException("Невозможно создать стену в заданном месте");
        _neighbourCells = new TreeMap<>();
        _neighbourCells.put(direction.getOppositeDirection(), cell);
        if (cell.getNeighbourCell(direction) != null) {
            _neighbourCells.put(direction, cell.getNeighbourCell(direction));
        }
    }

    private TreeMap<Direction, Cell> _neighbourCells = null;

    public TreeMap<Direction, Cell> getNeighbourCells() {
        return _neighbourCells;
    }
}
