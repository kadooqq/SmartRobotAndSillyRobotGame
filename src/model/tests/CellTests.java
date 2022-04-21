package model.tests;

import model.field.Direction;
import model.fieldObjects.landscape.SwampSegment;
import model.field.Cell;
import model.field.MyPoint;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Stack;
import java.util.TreeMap;

public class CellTests {
    //------------------------------------------------ сеттеры и геттеры -----------------------------------------------
    @Test
    public void setAndGetNeighbourCellTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(0, 1));

        Assertions.assertTrue(cell1.setNeighbourCell(cell2, Direction.NORTH));
        Assertions.assertEquals(cell2, cell1.getNeighbourCell(Direction.NORTH));
        Assertions.assertEquals(cell1, cell2.getNeighbourCell(Direction.NORTH.getOppositeDirection()));
    }

    @Test
    public void badSetNeighbourCellTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(0, 1));
        Cell cell3 = new Cell(new MyPoint(0, 1));

        Assertions.assertTrue(cell1.setNeighbourCell(cell2, Direction.NORTH));
        Assertions.assertFalse(cell1.setNeighbourCell(cell3, Direction.NORTH));
        Assertions.assertEquals(cell2, cell1.getNeighbourCell(Direction.NORTH));
        Assertions.assertEquals(cell1, cell2.getNeighbourCell(Direction.NORTH.getOppositeDirection()));
    }

    @Test
    public void getNeighbourCellsTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(0, 1));
        Cell cell3 = new Cell(new MyPoint(1, 0));
        Cell cell4 = new Cell(new MyPoint(0, -1));
        Cell cell5 = new Cell(new MyPoint(-1, 0));


        Assertions.assertTrue(cell1.setNeighbourCell(cell2, Direction.NORTH));
        Assertions.assertTrue(cell1.setNeighbourCell(cell3, Direction.EAST));
        Assertions.assertTrue(cell1.setNeighbourCell(cell4, Direction.SOUTH));
        Assertions.assertTrue(cell1.setNeighbourCell(cell5, Direction.WEST));

        final TreeMap<Direction, Cell> expected = new TreeMap<>();
        expected.put(Direction.NORTH, cell2);
        expected.put(Direction.EAST, cell3);
        expected.put(Direction.SOUTH, cell4);
        expected.put(Direction.WEST, cell5);

        Assertions.assertEquals(expected, cell1.getNeighbourCells());
    }

    @Test
    public void getNeighbourDirectionTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(0, 1));

        Assertions.assertTrue(cell1.setNeighbourCell(cell2, Direction.NORTH));
        Assertions.assertEquals(Direction.NORTH, cell1.getNeighbourDirection(cell2));
    }

    //------------------------------------------------ getDistanceFromCellToCell ---------------------------------------
    @Test
    public void getDistanceFromCellToCellTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(0, 1));
        MyPoint expected = new MyPoint(0, 1);

        Assertions.assertEquals(expected, cell1.getDistanceFromCellToCell(cell2));
    }

    //------------------------------------------------ getDirectionsToMove ---------------------------------------------
    @Test
    public void NorthGetDirectionsToMoveTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(0, 1));
        Stack<Direction> expected = new Stack<>();
        expected.push(Direction.NORTH);

        Assertions.assertEquals(expected, cell1.getDirectionsToMove(cell2));
    }

    @Test
    public void SouthGetDirectionsToMoveTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(0, -1));
        Stack<Direction> expected = new Stack<>();
        expected.push(Direction.SOUTH);

        Assertions.assertEquals(expected, cell1.getDirectionsToMove(cell2));
    }

    @Test
    public void EastGetDirectionsToMoveTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(1, 0));
        Stack<Direction> expected = new Stack<>();
        expected.push(Direction.EAST);

        Assertions.assertEquals(expected, cell1.getDirectionsToMove(cell2));
    }

    @Test
    public void WestGetDirectionsToMoveTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(-1, 0));
        Stack<Direction> expected = new Stack<>();
        expected.push(Direction.WEST);

        Assertions.assertEquals(expected, cell1.getDirectionsToMove(cell2));
    }

    @Test
    public void NorthEastGetDirectionsToMoveTest() {
        Cell cell1 = new Cell(new MyPoint(0, 0));
        Cell cell2 = new Cell(new MyPoint(1, 1));
        Stack<Direction> expected = new Stack<>();
        expected.push(Direction.NORTH);
        expected.push(Direction.EAST);

        Assertions.assertEquals(expected, cell1.getDirectionsToMove(cell2));
    }

    //------------------------------------------------ LandscapeSegment ------------------------------------------------
    @Test
    public void setAndGetLandscapeSegmentTest() {
        Cell cell = new Cell(new MyPoint(0, 0));
        SwampSegment swampSegment = new SwampSegment(1);
        cell.setLandscapeSegment(swampSegment);

        Assertions.assertEquals(swampSegment, cell.getLandscapeSegment());
    }
}
