package model.labyrinth;

import model.field.ExitCell;
import model.field.Field;
import model.field.fieldObjects.landscape.LandscapeSegment;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.fieldObjects.robot.LittleRobot;
import model.field.fieldObjects.robot.Robot;
import model.field.MyPoint;

public abstract class Labyrinth {
    public abstract Field buildField();

    // ----------------------------------------------- Расставить роботов ----------------------------------------------
    protected abstract void addRobots(Field field);

    protected boolean setRobot(Field field, MyPoint position, Robot robot) {
        if (field.getCell(position) instanceof ExitCell && robot instanceof LittleRobot ||
        field.getCell(position).getLandscapeSegment() != null) return false;
        return field.getCell(position).setRobot(robot);
    }

    // ----------------------------------------------- Расставить стены ----------------------------------------------
    protected abstract void addWalls(Field field);

    // ----------------------------------------------- Расставить ландшафт ----------------------------------------------
    protected abstract void addLandscape(Field field);

    protected boolean setLandscapeSegment(Field field, MyPoint position, LandscapeSegment landscapeSegment) {
        if (field.getCell(position) instanceof ExitCell && landscapeSegment instanceof SwampSegment ||
                field.getCell(position).getRobot() != null) return false;
        field.getCell(position).setLandscapeSegment(landscapeSegment);
        return true;
    }
}
