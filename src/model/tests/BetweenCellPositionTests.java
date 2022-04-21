package model.tests;

import model.field.Direction;
import model.field.BetweenCellPosition;
import model.field.Cell;
import model.field.MyPoint;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BetweenCellPositionTests {
    @Test
    public void northSingleCellBetweenCellPositionTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        Direction direction = Direction.NORTH;

        BetweenCellPosition betweenCellPosition = new BetweenCellPosition(cell, direction);

        Assertions.assertEquals(cell, betweenCellPosition.getNeighbourCells().get(direction.getOppositeDirection()));
        Assertions.assertEquals(1, betweenCellPosition.getNeighbourCells().size());
    }

    @Test
    public void eastSingleCellBetweenCellPositionTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        Direction direction = Direction.EAST;

        BetweenCellPosition betweenCellPosition = new BetweenCellPosition(cell, direction);

        Assertions.assertEquals(cell, betweenCellPosition.getNeighbourCells().get(direction.getOppositeDirection()));
        Assertions.assertEquals(1, betweenCellPosition.getNeighbourCells().size());
    }

    @Test
    public void southSingleCellBetweenCellPositionTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        Direction direction = Direction.SOUTH;

        BetweenCellPosition betweenCellPosition = new BetweenCellPosition(cell, direction);

        Assertions.assertEquals(cell, betweenCellPosition.getNeighbourCells().get(direction.getOppositeDirection()));
        Assertions.assertEquals(1, betweenCellPosition.getNeighbourCells().size());
    }

    @Test
    public void westSingleCellBetweenCellPositionTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        Direction direction = Direction.WEST;

        BetweenCellPosition betweenCellPosition = new BetweenCellPosition(cell, direction);

        Assertions.assertEquals(cell, betweenCellPosition.getNeighbourCells().get(direction.getOppositeDirection()));
        Assertions.assertEquals(1, betweenCellPosition.getNeighbourCells().size());
    }

    @Test
    public void twoNeighbourCellsVerticalBetweenCellPositionTest() {
        Cell cell1 = new Cell(new MyPoint(1, 1));
        Cell cell2 = new Cell(new MyPoint(1, 2));
        Direction direction = Direction.NORTH;

        Assertions.assertTrue(cell1.setNeighbourCell(cell2, direction));

        BetweenCellPosition betweenCellPosition = new BetweenCellPosition(cell1, direction);

        Assertions.assertEquals(cell1, betweenCellPosition.getNeighbourCells().get(direction.getOppositeDirection()));
        Assertions.assertEquals(cell2, betweenCellPosition.getNeighbourCells().get(direction));
        Assertions.assertEquals(2, betweenCellPosition.getNeighbourCells().size());
    }

    @Test
    public void twoNeighbourCellsHorizontalBetweenCellPositionTest() {
        Cell cell1 = new Cell(new MyPoint(1, 1));
        Cell cell2 = new Cell(new MyPoint(2, 1));
        Direction direction = Direction.EAST;

        Assertions.assertTrue(cell1.setNeighbourCell(cell2, direction));

        BetweenCellPosition betweenCellPosition = new BetweenCellPosition(cell1, direction);

        Assertions.assertEquals(cell1, betweenCellPosition.getNeighbourCells().get(direction.getOppositeDirection()));
        Assertions.assertEquals(cell2, betweenCellPosition.getNeighbourCells().get(direction));
        Assertions.assertEquals(2, betweenCellPosition.getNeighbourCells().size());
    }

    @Test
    public void anotherTwoNeighbourCellsVerticalBetweenCellPositionTest() {
        Cell cell1 = new Cell(new MyPoint(1, 1));
        Cell cell2 = new Cell(new MyPoint(1, 2));
        Direction direction = Direction.NORTH;

        Assertions.assertTrue(cell1.setNeighbourCell(cell2, direction));

        BetweenCellPosition betweenCellPosition = new BetweenCellPosition(cell1, cell2);

        Assertions.assertEquals(cell1, betweenCellPosition.getNeighbourCells().get(direction.getOppositeDirection()));
        Assertions.assertEquals(cell2, betweenCellPosition.getNeighbourCells().get(direction));
        Assertions.assertEquals(2, betweenCellPosition.getNeighbourCells().size());
    }

    @Test
    public void anotherTwoNeighbourCellsHorizontalBetweenCellPositionTest() {
        Cell cell1 = new Cell(new MyPoint(1, 1));
        Cell cell2 = new Cell(new MyPoint(2, 1));
        Direction direction = Direction.EAST;

        Assertions.assertTrue(cell1.setNeighbourCell(cell2, direction));

        BetweenCellPosition betweenCellPosition = new BetweenCellPosition(cell1, cell2);

        Assertions.assertEquals(cell1, betweenCellPosition.getNeighbourCells().get(direction.getOppositeDirection()));
        Assertions.assertEquals(cell2, betweenCellPosition.getNeighbourCells().get(direction));
        Assertions.assertEquals(2, betweenCellPosition.getNeighbourCells().size());
    }

    @Test
    public void twoNonNeighbourCellsHorizontalBetweenCellPositionTest() {
        Cell cell1 = new Cell(new MyPoint(1, 1));
        Cell cell2 = new Cell(new MyPoint(2, 1));
        Direction direction = Direction.EAST;

        try {
            new BetweenCellPosition(cell1, cell2);
            Assertions.assertFalse(false);
        }
        catch (IllegalArgumentException e) {
            Assertions.assertTrue(true);
        }
    }
}
