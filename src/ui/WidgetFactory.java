package ui;

import model.field.Cell;
import model.field.ExitCell;
import model.field.fieldObjects.CellItem;
import model.field.fieldObjects.WallSegment;
import model.field.fieldObjects.landscape.LandscapeSegment;
import model.field.fieldObjects.landscape.SandSegment;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.fieldObjects.robot.BigRobot;
import model.field.fieldObjects.robot.LittleRobot;
import model.field.fieldObjects.robot.Robot;
import org.jetbrains.annotations.NotNull;
import ui.field.betweenCellsWidgets.Orientation;
import ui.field.betweenCellsWidgets.WallSegmentWidget;
import ui.field.cell.CellWidget;
import ui.field.cell.ExitCellWidget;
import ui.field.cell.cellItems.CellItemWidget;
import ui.field.cell.cellItems.landscape.SandSegmentWidget;
import ui.field.cell.cellItems.landscape.SwampSegmentWidget;
import ui.field.cell.cellItems.robots.BigRobotWidget;
import ui.field.cell.cellItems.robots.LittleRobotWidget;

import javax.swing.*;
import java.util.HashMap;

public class WidgetFactory extends JPanel {
    private final HashMap<Cell, CellWidget> _cells = new HashMap<>();
    private final HashMap<CellItem, CellItemWidget> _cellItems = new HashMap<>();
    private final HashMap<WallSegment, WallSegmentWidget> _walls = new HashMap<>();

    public CellWidget create(@NotNull Cell cell) {
        if (_cells.containsKey(cell)) return _cells.get(cell);

        CellWidget cellWidget = (cell instanceof ExitCell) ? new ExitCellWidget() : new CellWidget();

        Robot robot = cell.getRobot();
        if (robot != null) {
            CellItemWidget robotWidget = create(robot);
            cellWidget.add(robotWidget);
            cellWidget.addItem(robotWidget);
        }

        LandscapeSegment landscapeSegment = cell.getLandscapeSegment();
        if (landscapeSegment != null) {
            CellItemWidget landscapeSegmentWidget = create(landscapeSegment);
            cellWidget.add(landscapeSegmentWidget);
            cellWidget.addItem(landscapeSegmentWidget);
        }



        _cells.put(cell, cellWidget);
        return cellWidget;
    }

    public CellWidget getWidget(@NotNull Cell cell) {
        return _cells.get(cell);
    }

    public void remove(@NotNull Cell cell) {
        _cells.remove(cell);
    }

    public CellItemWidget create(@NotNull CellItem cellItem) {
        if (_cellItems.containsKey(cellItem)) return _cellItems.get(cellItem);

        CellItemWidget cellItemWidget = null;

        if (cellItem instanceof LittleRobot) {
            cellItemWidget = new LittleRobotWidget((LittleRobot)cellItem);
        }
        else if (cellItem instanceof BigRobot) {
            cellItemWidget = new BigRobotWidget((BigRobot)cellItem);
        }
        else if (cellItem instanceof SwampSegment) {
            cellItemWidget = new SwampSegmentWidget();
        }
        else if (cellItem instanceof SandSegment) {
            cellItemWidget = new SandSegmentWidget();
        }

        _cellItems.put(cellItem, cellItemWidget);
        return cellItemWidget;
    }

    public CellItemWidget getWidget(@NotNull CellItem cellObject) {
        return _cellItems.get(cellObject);
    }

    public void remove(@NotNull CellItem cellObject) { _cellItems.remove(cellObject); }

    public WallSegmentWidget create(@NotNull WallSegment wallSegment, @NotNull Orientation orientation) {
        if (_walls.containsKey(wallSegment)) return _walls.get(wallSegment);

        WallSegmentWidget wallSegmentWidget = new WallSegmentWidget(orientation);

        _walls.put(wallSegment, wallSegmentWidget);
        return wallSegmentWidget;
    }

    public WallSegmentWidget getWidget(@NotNull WallSegment wallSegment) {
        return _walls.get(wallSegment);
    }

    public void remove(@NotNull WallSegment wallSegment) { _walls.remove(wallSegment); }
}
