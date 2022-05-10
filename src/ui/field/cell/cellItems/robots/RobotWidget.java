package ui.field.cell.cellItems.robots;

import model.field.fieldObjects.robot.Robot;
import ui.field.cell.cellItems.CellItemWidget;

import java.io.File;

public abstract class RobotWidget extends CellItemWidget {
    public RobotWidget(Robot robot) {
        super();
        _robot = robot;
    }

    protected Robot _robot;

    protected abstract File getImageFile();
}
