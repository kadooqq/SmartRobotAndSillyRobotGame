package model.tests;

import model.field.Direction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DirectionTests {
    @Test
    public void getOppositeDirectionTest() {
        Assertions.assertEquals(Direction.SOUTH, Direction.NORTH.getOppositeDirection());
        Assertions.assertEquals(Direction.WEST, Direction.EAST.getOppositeDirection());
        Assertions.assertEquals(Direction.NORTH, Direction.SOUTH.getOppositeDirection());
        Assertions.assertEquals(Direction.EAST, Direction.WEST.getOppositeDirection());
    }

    @Test
    public void getClockwisedRotatedDirectionTest() {
        Assertions.assertEquals(Direction.EAST, Direction.NORTH.getClockwiseRotatedDirection());
        Assertions.assertEquals(Direction.SOUTH, Direction.EAST.getClockwiseRotatedDirection());
        Assertions.assertEquals(Direction.WEST, Direction.SOUTH.getClockwiseRotatedDirection());
        Assertions.assertEquals(Direction.NORTH, Direction.WEST.getClockwiseRotatedDirection());
    }
}
