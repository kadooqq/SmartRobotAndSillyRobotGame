package model.tests;

import model.field.Direction;
import model.field.Field;
import model.field.MyPoint;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.fieldObjects.robot.BigRobot;
import model.field.fieldObjects.robot.LittleRobot;
import model.gameStuff.PathFinder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BigRobotTests {
    @Test
    public void simpleBigRobotTest() {
        int exitPointX = 3, exitPointY = 3;
        Field field = new Field(5, 5, new MyPoint(exitPointX, exitPointY));

        LittleRobot littleRobot = new LittleRobot();
        field.getCell(new MyPoint(1, 1)).setRobot(littleRobot);

        PathFinder pathFinder = new PathFinder(field);
        BigRobot bigRobot = new BigRobot();
        bigRobot.setPathFinder(pathFinder);

        bigRobot.setTarget(littleRobot);
        field.getCell(new MyPoint(5, 5)).setRobot(bigRobot);

        littleRobot.addRobotMoveListener(bigRobot);

        Assertions.assertTrue(littleRobot.move(Direction.NORTH));
        Assertions.assertEquals(field.getCell(new MyPoint(4, 5)), bigRobot.getPosition());
    }

    @Test
    public void rightMoveBigRobotTest() {
        int exitPointX = 3, exitPointY = 3;
        Field field = new Field(5, 5, new MyPoint(exitPointX, exitPointY));

        LittleRobot littleRobot = new LittleRobot();
        field.getCell(new MyPoint(5, 5)).setRobot(littleRobot);

        PathFinder pathFinder = new PathFinder(field);
        BigRobot bigRobot = new BigRobot();
        bigRobot.setPathFinder(pathFinder);

        bigRobot.setTarget(littleRobot);
        field.getCell(new MyPoint(1, 1)).setRobot(bigRobot);

        littleRobot.addRobotMoveListener(bigRobot);

        Assertions.assertTrue(littleRobot.move(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(2, 1)), bigRobot.getPosition());
    }

    @Test
    public void skipStepMoveBigRobotTest() {
        int exitPointX = 3, exitPointY = 3;
        Field field = new Field(5, 5, new MyPoint(exitPointX, exitPointY));

        LittleRobot littleRobot = new LittleRobot();
        field.getCell(new MyPoint(5, 5)).setRobot(littleRobot);

        PathFinder pathFinder = new PathFinder(field);
        BigRobot bigRobot = new BigRobot();
        bigRobot.setPathFinder(pathFinder);

        bigRobot.setTarget(littleRobot);
        field.getCell(new MyPoint(5, 1)).setRobot(bigRobot);

        littleRobot.addRobotMoveListener(bigRobot);

        Assertions.assertTrue(littleRobot.move(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(5, 1)), bigRobot.getPosition());
    }

    @Test
    public void littleRobotNearMoveBigRobotTest() {
        int exitPointX = 3, exitPointY = 3;
        Field field = new Field(5, 5, new MyPoint(exitPointX, exitPointY));

        LittleRobot littleRobot = new LittleRobot();
        field.getCell(new MyPoint(5, 5)).setRobot(littleRobot);

        PathFinder pathFinder = new PathFinder(field);
        BigRobot bigRobot = new BigRobot();
        bigRobot.setPathFinder(pathFinder);

        bigRobot.setTarget(littleRobot);
        field.getCell(new MyPoint(5, 2)).setRobot(bigRobot);

        littleRobot.addRobotMoveListener(bigRobot);

        Assertions.assertTrue(littleRobot.move(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(5, 3)), bigRobot.getPosition());
    }

    @Test
    public void destroyLittleRobotNearMoveBigRobotTest() {
        int exitPointX = 3, exitPointY = 3;
        Field field = new Field(5, 5, new MyPoint(exitPointX, exitPointY));

        LittleRobot littleRobot = new LittleRobot();
        field.getCell(new MyPoint(5, 5)).setRobot(littleRobot);

        PathFinder pathFinder = new PathFinder(field);
        BigRobot bigRobot = new BigRobot();
        bigRobot.setPathFinder(pathFinder);

        bigRobot.setTarget(littleRobot);
        field.getCell(new MyPoint(5, 3)).setRobot(bigRobot);

        littleRobot.addRobotMoveListener(bigRobot);

        Assertions.assertTrue(littleRobot.move(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(5, 3)), bigRobot.getPosition());
        Assertions.assertNull(littleRobot.getPosition());
        Assertions.assertNull(field.getCell(new MyPoint(5, 4)).getRobot());
    }

    @Test
    public void moveAtLandscapeSegmentBigRobotTest() {
        int exitPointX = 3, exitPointY = 3;
        Field field = new Field(5, 5, new MyPoint(exitPointX, exitPointY));

        LittleRobot littleRobot = new LittleRobot();
        field.getCell(new MyPoint(5, 5)).setRobot(littleRobot);

        PathFinder pathFinder = new PathFinder(field);
        BigRobot bigRobot = new BigRobot();
        bigRobot.setPathFinder(pathFinder);

        bigRobot.setTarget(littleRobot);
        field.getCell(new MyPoint(1, 1)).setRobot(bigRobot);
        field.getCell(new MyPoint(2, 1)).setLandscapeSegment(new SwampSegment(3));

        littleRobot.addRobotMoveListener(bigRobot);

        Assertions.assertTrue(littleRobot.move(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(2, 1)), bigRobot.getPosition());
        Assertions.assertTrue(littleRobot.move(Direction.NORTH));
        Assertions.assertEquals(field.getCell(new MyPoint(2, 1)), bigRobot.getPosition());
        Assertions.assertTrue(littleRobot.move(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(2, 1)), bigRobot.getPosition());
        Assertions.assertTrue(littleRobot.move(Direction.NORTH));
        Assertions.assertEquals(field.getCell(new MyPoint(2, 1)), bigRobot.getPosition());
        Assertions.assertTrue(littleRobot.move(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(3, 1)), bigRobot.getPosition());
    }

    @Test
    public void changePathFinderBigRobotTest() {
        int exitPointX = 3, exitPointY = 3;
        Field field = new Field(5, 5, new MyPoint(exitPointX, exitPointY));

        PathFinder pathFinder = new PathFinder(field);
        BigRobot bigRobot = new BigRobot();
        bigRobot.setPathFinder(pathFinder);

        PathFinder anotherPathFinder = new PathFinder(field);
        bigRobot.setPathFinder(anotherPathFinder);

        Assertions.assertNull(pathFinder.getRobot());
        Assertions.assertEquals(anotherPathFinder, bigRobot.getPathFinder());
        Assertions.assertEquals(bigRobot, anotherPathFinder.getRobot());
    }
}
