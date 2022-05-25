package model.tests;

import model.field.Direction;
import model.field.ExitCell;
import model.field.Field;
import model.field.MyPoint;
import model.field.fieldObjects.WallSegment;
import model.field.fieldObjects.landscape.IceSegment;
import model.field.fieldObjects.landscape.SandSegment;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.fieldObjects.robot.LittleRobot;
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
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

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
        Assertions.assertFalse(robot.makeStep(Direction.WEST));
        Assertions.assertFalse(robot.makeStep(Direction.SOUTH));

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
        Assertions.assertTrue(robot.makeStep(Direction.NORTH));

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
        Assertions.assertTrue(robot.makeStep(Direction.WEST));

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
        Assertions.assertTrue(robot.makeStep(Direction.SOUTH));

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
        Assertions.assertFalse(robot.makeStep(Direction.NORTH));

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
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        MyPoint resultPosition = littleRobotStartPosition.getNeighbourPoint(Direction.EAST);
        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
    }

    @Test
    public void littleRobotMoveOnSwampSegmentTest() {
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
        field.getCell(littleRobotStartPosition.getNeighbourPoint(Direction.EAST)).setLandscapeSegment(new SwampSegment());

        // Переместиться в ячейку с болотом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        MyPoint resultPosition = littleRobotStartPosition.getNeighbourPoint(Direction.EAST);
        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertNull(robot.getPosition());
        Assertions.assertNull(field.getCell(resultPosition).getRobot());
    }

    @Test
    public void littleRobotMoveOnSandSegmentTest() {
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

        // Установить справа песок
        MyPoint sandPosition = new MyPoint(4, 3);
        field.getCell(sandPosition).setLandscapeSegment(new SandSegment());

        // Переместиться в ячейку с песком
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertEquals(field.getCell(sandPosition), robot.getPosition());
        Assertions.assertEquals(robot, field.getCell(sandPosition).getRobot());

        Assertions.assertFalse(robot.makeStep(Direction.EAST));

        MyPoint resultPosition = sandPosition.getNeighbourPoint(Direction.EAST);

        Assertions.assertNull(field.getCell(resultPosition).getRobot());
        Assertions.assertEquals(field.getCell(sandPosition), robot.getPosition());
        Assertions.assertEquals(robot, field.getCell(sandPosition).getRobot());

        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(sandPosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
        Assertions.assertEquals(robot, field.getCell(resultPosition).getRobot());

    }

    @Test
    public void littleRobotMoveOnIceSegmentTest() {
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

        // Установить справа лед
        MyPoint icePosition = new MyPoint(4, 3);
        field.getCell(icePosition).setLandscapeSegment(new IceSegment());

        MyPoint resultPosition = icePosition.getNeighbourPoint(Direction.EAST);

        // Переместиться в ячейку со льдом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertNull(field.getCell(icePosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
    }

    @Test
    public void littleRobotMoveOnIceSegmentTwoIceSegmentsTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 6, height = 6;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(3, 3);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Установить справа лед
        MyPoint ice1Position = new MyPoint(4, 3);
        field.getCell(ice1Position).setLandscapeSegment(new IceSegment());

        MyPoint ice2Position = new MyPoint(5, 3);
        field.getCell(ice2Position).setLandscapeSegment(new IceSegment());


        MyPoint resultPosition = ice2Position;

        // Переместиться в ячейку со льдом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertNull(field.getCell(ice1Position).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());

        resultPosition = ice2Position.getNeighbourPoint(Direction.EAST);

        // Переместиться в ячейку со льдом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(ice2Position).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
    }

    @Test
    public void littleRobotMoveOnIceSegmentThreeIceSegmentsTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 7, height = 7;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        MyPoint littleRobotStartPosition = new MyPoint(3, 3);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Установить справа лед
        MyPoint ice1Position = new MyPoint(4, 3);
        field.getCell(ice1Position).setLandscapeSegment(new IceSegment());

        MyPoint ice2Position = new MyPoint(5, 3);
        field.getCell(ice2Position).setLandscapeSegment(new IceSegment());

        MyPoint ice3Position = new MyPoint(6, 3);
        field.getCell(ice2Position).setLandscapeSegment(new IceSegment());


        MyPoint result1Position = ice2Position;

        // Переместиться в ячейку со льдом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertNull(field.getCell(ice1Position).getRobot());
        Assertions.assertEquals(field.getCell(result1Position).getRobot(), robot);
        Assertions.assertEquals(field.getCell(result1Position), robot.getPosition());

        result1Position = ice2Position.getNeighbourPoint(Direction.EAST);

        // Переместиться в ячейку со льдом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(ice2Position).getRobot());
        Assertions.assertEquals(field.getCell(result1Position).getRobot(), robot);
        Assertions.assertEquals(field.getCell(result1Position), robot.getPosition());

        MyPoint result2Position = ice3Position.getNeighbourPoint(Direction.EAST);

        // Переместиться в ячейку со льдом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(result1Position).getRobot());
        Assertions.assertNull(field.getCell(ice3Position).getRobot());
        Assertions.assertEquals(field.getCell(result2Position).getRobot(), robot);
        Assertions.assertEquals(field.getCell(result2Position), robot.getPosition());

    }

    @Test
    public void littleRobotMoveOnIceSegmentButMeetWallTest() {
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

        // Установить справа лед
        MyPoint ice1Position = new MyPoint(4, 3);
        field.getCell(ice1Position).setLandscapeSegment(new IceSegment());
        field.getCell(ice1Position).setWallSegment(new WallSegment(), Direction.EAST);

        MyPoint resultPosition = ice1Position;

        // Переместиться в ячейку со льдом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertEquals(field.getCell(resultPosition).getRobot(), robot);
        Assertions.assertEquals(field.getCell(resultPosition), robot.getPosition());
    }

    @Test
    public void littleRobotMoveOnIceSegmentSlidesToSwampSegmentTest() {
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

        // Установить справа лед
        MyPoint ice1Position = new MyPoint(4, 3);
        field.getCell(ice1Position).setLandscapeSegment(new IceSegment());
        MyPoint swampPosition = new MyPoint(5, 3);
        field.getCell(swampPosition).setLandscapeSegment(new SwampSegment());

        // Переместиться в ячейку со льдом
        Assertions.assertTrue(robot.makeStep(Direction.EAST));

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertNull(field.getCell(ice1Position).getRobot());
        Assertions.assertNull(field.getCell(swampPosition).getRobot());
        Assertions.assertNull(robot.getPosition());
    }

    @Test
    public void moveToExitCellLittleRobotTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        LittleRobot robot = new LittleRobot();

        robot.addRobotMoveListener((ExitCell) field.getCell(exitPointCoordinates));

        MyPoint littleRobotStartPosition = new MyPoint(2, 1);

        // Установить робота на поле
        Assertions.assertTrue(field.getCell(littleRobotStartPosition).setRobot(robot));
        Assertions.assertEquals(field.getCell(littleRobotStartPosition), robot.getPosition());
        Assertions.assertEquals(field.getCell(littleRobotStartPosition).getRobot(), robot);

        // Переместиться в точку выхода
        Assertions.assertTrue(robot.makeStep(Direction.WEST));

        Assertions.assertNull(field.getCell(littleRobotStartPosition).getRobot());
        Assertions.assertNull(robot.getPosition());
        Assertions.assertNull(field.getCell(exitPointCoordinates).getRobot());
    }
}
