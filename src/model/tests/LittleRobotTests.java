package model.tests;

import model.field.Direction;
import model.field.ExitCell;
import model.field.Field;
import model.field.MyPoint;
import model.fieldObjects.WallSegment;
import model.fieldObjects.landscape.SwampSegment;
import model.fieldObjects.robot.LittleRobot;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class LittleRobotTests {
    @Test
    public void simpleLittleRobotMoveTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(1, 1);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Переместиться в свободную соседнюю ячейку
        Assertions.assertTrue(robot.move(Direction.EAST));

        MyPoint resultPosition = littleRobotStartPosition.getNeighbourPoint(Direction.EAST);

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
    }

    @Test
    public void outOfFieldLittleRobotMoveTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(1, 1);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Переместиться в сторону, где нет ячеек поля
        Assertions.assertFalse(robot.move(Direction.WEST));
        Assertions.assertFalse(robot.move(Direction.SOUTH));

        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);
    }

    @Test
    public void littleRobotUpMoveTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(1, 1);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Переместиться в свободную соседнюю ячейку
        Assertions.assertTrue(robot.move(Direction.NORTH));

        MyPoint resultPosition = littleRobotStartPosition.getNeighbourPoint(Direction.NORTH);

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
    }

    @Test
    public void littleRobotLeftMoveTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(3, 3);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Переместиться в свободную соседнюю ячейку
        Assertions.assertTrue(robot.move(Direction.WEST));

        MyPoint resultPosition = littleRobotStartPosition.getNeighbourPoint(Direction.WEST);

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
    }

    @Test
    public void littleRobotDownMoveTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(3, 3);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Переместиться в свободную соседнюю ячейку
        Assertions.assertTrue(robot.move(Direction.SOUTH));

        MyPoint resultPosition = littleRobotStartPosition.getNeighbourPoint(Direction.SOUTH);

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
    }

    @Test
    public void littleRobotMoveToWallTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(3, 3);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Установить стену сверху от робота
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setWallSegment(new WallSegment(), Direction.NORTH));

        // Попытаться переместиться в ячейку, закрытую стеной
        Assertions.assertFalse(robot.move(Direction.NORTH));

        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);
    }

    @Test
    public void littleRobotMoveNearWallTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(3, 3);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Установить стену сверху от робота
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setWallSegment(new WallSegment(), Direction.NORTH));

        // Переместиться в свободную соседнюю ячейку
        Assertions.assertTrue(robot.move(Direction.EAST));

        MyPoint resultPosition = littleRobotStartPosition.getNeighbourPoint(Direction.EAST);
        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
    }

    @Test
    public void littleRobotMoveOnLandscapeSegmentTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(3, 3);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Установить справа болото
        field.getCell(littleRobotStartPosition.getNeighbourPoint(Direction.EAST)).setLandscapeSegment(new SwampSegment(1));

        // Переместиться в ячейку с болотом
        Assertions.assertTrue(robot.move(Direction.EAST));

        MyPoint resultPosition = littleRobotStartPosition.getNeighbourPoint(Direction.EAST);
        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertNull(robot.getPosition());
        Assertions.assertNull(field.getCell(resultPosition).getRobot());
    }

    @Test
    public void moveToExitCellLittleRobotTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        robot.addRobotMoveListener((ExitCell)field.getCell(exitPointCoordinates));

        MyPoint littleRobotStartPosition = new MyPoint(2, 1);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Переместиться в точку выхода
        Assertions.assertTrue(robot.move(Direction.WEST));

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertNull(robot.getPosition());
        Assertions.assertNull(field.getCell(exitPointCoordinates).getRobot());
    }
}