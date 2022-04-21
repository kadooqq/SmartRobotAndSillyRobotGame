package model.tests;

import model.field.Cell;
import model.field.MyPoint;
import model.fieldObjects.robot.BigRobot;
import model.fieldObjects.robot.LittleRobot;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SetRobotOnCellTests {
    @Test
    public void getAndSetRobotTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        LittleRobot littleRobot = new LittleRobot();

        Assertions.assertTrue(cell.setRobot(littleRobot));
        Assertions.assertEquals(littleRobot, cell.getRobot());
        Assertions.assertEquals(cell, littleRobot.getPosition());
    }

    @Test
    public void badGetAndSetRobotTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        LittleRobot littleRobot = new LittleRobot();
        BigRobot bigRobot = new BigRobot();

        Assertions.assertTrue(cell.setRobot(littleRobot));
        Assertions.assertFalse(cell.setRobot(bigRobot));

        Assertions.assertNull(bigRobot.getPosition());

        Assertions.assertEquals(littleRobot, cell.getRobot());
        Assertions.assertEquals(cell, littleRobot.getPosition());
    }

    @Test
    public void anotherBadGetAndSetRobotTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        LittleRobot littleRobot = new LittleRobot();
        BigRobot bigRobot = new BigRobot();

        Assertions.assertTrue(cell.setRobot(bigRobot));
        Assertions.assertFalse(cell.setRobot(littleRobot));

        Assertions.assertNull(littleRobot.getPosition());

        Assertions.assertEquals(bigRobot, cell.getRobot());
        Assertions.assertEquals(cell, bigRobot.getPosition());
    }

    @Test
    public void takeRobotTest() {
        Cell cell = new Cell(new MyPoint(1, 1));
        LittleRobot littleRobot = new LittleRobot();

        Assertions.assertTrue(cell.setRobot(littleRobot));

        Assertions.assertEquals(littleRobot, cell.takeRobot());

        Assertions.assertNull(cell.getRobot());
        Assertions.assertNull(littleRobot.getPosition());
    }
}
