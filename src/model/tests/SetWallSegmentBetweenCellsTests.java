package model.tests;

import model.field.BetweenCellPosition;
import model.field.Direction;
import model.field.Cell;
import model.field.MyPoint;
import model.fieldObjects.WallSegment;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SetWallSegmentBetweenCellsTests {
    @Test
    public void singleCellBetweenCellPositionTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        WallSegment wall = new WallSegment();
        Direction direction = Direction.NORTH;

        Assertions.assertTrue(cell.setWallSegment(wall, direction));
        Assertions.assertEquals(wall, cell.getWallSegment(direction));
        Assertions.assertEquals(cell, wall.getPosition().getNeighbourCells().get(direction.getOppositeDirection()));
    }

    @Test
    public void twoNeighbourCellsBetweenCellPositionTest() {
        Cell cell1 = new Cell(new MyPoint(1, 1));
        Cell cell2 = new Cell(new MyPoint(1, 1));
        WallSegment wall = new WallSegment();
        Direction direction = Direction.NORTH;

        cell1.setNeighbourCell(cell2, direction);

        Assertions.assertTrue(cell1.setWallSegment(wall, direction));

        Assertions.assertEquals(wall, cell1.getWallSegment(direction));
        Assertions.assertEquals(cell1, wall.getPosition().getNeighbourCells().get(direction.getOppositeDirection()));

        Assertions.assertEquals(wall, cell2.getWallSegment(direction.getOppositeDirection()));
        Assertions.assertEquals(cell2, wall.getPosition().getNeighbourCells().get(direction));
    }

    @Test
    public void anotherTwoNeighbourCellsBetweenCellPositionTest() {
        Cell cell1 = new Cell(new MyPoint(1, 1));
        Cell cell2 = new Cell(new MyPoint(1, 1));
        WallSegment wall = new WallSegment();
        Direction direction = Direction.NORTH;

        cell1.setNeighbourCell(cell2, direction);

        Assertions.assertTrue(wall.setPosition(new BetweenCellPosition(cell1, cell2)));

        Assertions.assertEquals(wall, cell1.getWallSegment(direction));
        Assertions.assertEquals(cell1, wall.getPosition().getNeighbourCells().get(direction.getOppositeDirection()));

        Assertions.assertEquals(wall, cell2.getWallSegment(direction.getOppositeDirection()));
        Assertions.assertEquals(cell2, wall.getPosition().getNeighbourCells().get(direction));
    }
}
