package model.tests;

import model.field.Cell;
import model.field.MyPoint;
import model.field.fieldObjects.landscape.SwampSegment;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SetLandscapeSegmentTests {
    @Test
    public void setLandscapeSegmentTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        SwampSegment swampSegment = new SwampSegment();

        Assertions.assertTrue(cell.setLandscapeSegment(swampSegment));
        Assertions.assertEquals(swampSegment, cell.getLandscapeSegment());
        Assertions.assertEquals(cell, swampSegment.getPosition());
    }

    @Test
    public void setNewLandscapeSegmentTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        SwampSegment swampSegment = new SwampSegment();
        SwampSegment swampSegment1 = new SwampSegment();

        Assertions.assertTrue(cell.setLandscapeSegment(swampSegment));
        Assertions.assertEquals(swampSegment, cell.getLandscapeSegment());
        Assertions.assertEquals(cell, swampSegment.getPosition());

        Assertions.assertTrue(cell.setLandscapeSegment(swampSegment1));
        Assertions.assertEquals(swampSegment1, cell.getLandscapeSegment());
        Assertions.assertEquals(cell, swampSegment1.getPosition());
        Assertions.assertNull(swampSegment.getPosition());
    }
}
