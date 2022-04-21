package model.tests;

import model.gameStuff.PathFinder;
import model.field.Cell;
import model.field.Direction;
import model.field.Field;
import model.field.MyPoint;
import model.fieldObjects.WallSegment;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PathFinderTests {
    @Test
    public void simplePathFinderTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        PathFinder pathFinder = new PathFinder(field);

        var path = pathFinder.findPath(field.getCell(new MyPoint(1, 1)), field.getCell(new MyPoint(1, 5)));

        Assertions.assertEquals(5, path.size());

        Cell expectedPathCell = field.getCell(new MyPoint(1, 1));
        for (int i = 0; i < 5; ++i) {
            var pathCell = path.pop();
            Assertions.assertEquals(expectedPathCell, pathCell);
            expectedPathCell = expectedPathCell.getNeighbourCell(Direction.NORTH);
        }
    }

    @Test
    public void wallOnPathPathFinderTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);
        field.getCell(new MyPoint(1, 1)).setWallSegment(new WallSegment(), Direction.NORTH);
        field.getCell(new MyPoint(1, 5)).setWallSegment(new WallSegment(), Direction.SOUTH);

        PathFinder pathFinder = new PathFinder(field);

        var path = pathFinder.findPath(field.getCell(new MyPoint(1, 1)), field.getCell(new MyPoint(1, 5)));

        Assertions.assertEquals(7, path.size());

        Cell expectedPathCell = field.getCell(new MyPoint(1, 1));
        Assertions.assertEquals(expectedPathCell, path.pop());
        expectedPathCell = expectedPathCell.getNeighbourCell(Direction.EAST);
        for (int i = 0; i < 5; ++i) {
            var pathCell = path.pop();
            Assertions.assertEquals(expectedPathCell, pathCell);
            expectedPathCell = expectedPathCell.getNeighbourCell(Direction.NORTH);
        }
        expectedPathCell = field.getCell(new MyPoint(1, 5));
        Assertions.assertEquals(expectedPathCell, path.pop());
    }
}
