package model.labyrinth;

import model.field.Field;
import model.field.MyPoint;
import model.field.fieldObjects.landscape.IceSegment;
import model.field.fieldObjects.landscape.RockSegment;
import model.field.fieldObjects.landscape.SandSegment;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.fieldObjects.robot.LittleRobot;

public class LabyrinthWithRockSegments extends Labyrinth {
    @Override
    public Field buildField() {
        int fieldWidth = 10, fieldHeight = 10;
        MyPoint exitPointPosition = new MyPoint(1, 4);
        Field field = new Field(fieldWidth, fieldHeight, exitPointPosition);
        addRobots(field);
        addWalls(field);
        addLandscape(field);
        return field;
    }

    @Override
    protected void addRobots(Field field) {
        LittleRobot littleRobot = new LittleRobot();
        MyPoint littleRobotPosition = new MyPoint(1, 1);
        setRobot(field, littleRobotPosition, littleRobot);

        addBigRobot(field, new MyPoint(7, 5), littleRobot);
    }

    @Override
    protected void addWalls(Field field) {/*
        field.getCell(new MyPoint(3, 3)).setWallSegment(new WallSegment(), Direction.NORTH);
        field.getCell(new MyPoint(4, 3)).setWallSegment(new WallSegment(), Direction.NORTH);
        field.getCell(new MyPoint(2, 3)).setWallSegment(new WallSegment(), Direction.EAST);
        field.getCell(new MyPoint(2, 2)).setWallSegment(new WallSegment(), Direction.EAST);*/
    }

    @Override
    protected void addLandscape(Field field) {
        setLandscapeSegment(field, new MyPoint(2, 1), new SwampSegment());/*
        setLandscapeSegment(field, new MyPoint(2, 2), new SwampSegment());
        setLandscapeSegment(field, new MyPoint(3, 2), new SwampSegment());*/

        setLandscapeSegment(field, new MyPoint(6, 6), new SandSegment());

        setLandscapeSegment(field, new MyPoint(9, 9), new IceSegment());

        setLandscapeSegment(field, new MyPoint(3, 3), new RockSegment());
        setLandscapeSegment(field, new MyPoint(4, 4), new RockSegment());
        setLandscapeSegment(field, new MyPoint(5, 5), new RockSegment());

        setLandscapeSegment(field, new MyPoint(3, 5), new RockSegment());
        setLandscapeSegment(field, new MyPoint(5, 3), new RockSegment());
    }
}
