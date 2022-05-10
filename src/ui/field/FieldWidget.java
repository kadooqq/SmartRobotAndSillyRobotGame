package ui.field;

import model.field.Cell;
import model.field.Direction;
import model.field.Field;
import model.field.MyPoint;
import model.field.fieldObjects.WallSegment;
import org.jetbrains.annotations.NotNull;
import ui.WidgetFactory;
import ui.field.betweenCellsWidgets.BetweenCellsWidget;
import ui.field.betweenCellsWidgets.Orientation;
import ui.field.betweenCellsWidgets.WallSegmentWidget;
import ui.field.cell.CellWidget;

import javax.swing.*;

public class FieldWidget extends JPanel {
    public FieldWidget(@NotNull Field field, @NotNull WidgetFactory widgetFactory) {
        _widgetFactory = widgetFactory;
        _field = field;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fillField();
    }
    private final WidgetFactory _widgetFactory;

    public WidgetFactory getWidgetFactory() {
        return _widgetFactory;
    }

    private final Field _field;

    private void fillField() {
        for (int i = _field.getHeight(); i >= 1; --i) {
            add(createRowHorizontalWalls(i, Direction.NORTH));
            add(createCellRowWithVerticalWalls(i));
        }
        add(createRowHorizontalWalls(1, Direction.SOUTH));
    }

    private JPanel createCellRowWithVerticalWalls(int rowIndex) {
        JPanel cellRow = new JPanel();
        cellRow.setLayout(new BoxLayout(cellRow, BoxLayout.X_AXIS));

        for (int i = 1; i <= _field.getWidth(); ++i) {
            Cell cell = _field.getCell(new MyPoint(i, rowIndex));

            BetweenCellsWidget westBetweenCellsWidget = new BetweenCellsWidget(Orientation.VERTICAL);
            WallSegment westWallSegment = cell.getWallSegment(Direction.WEST);
            if (westWallSegment != null) {
                WallSegmentWidget westWallWidget = _widgetFactory.create(westWallSegment, Orientation.VERTICAL);
                westBetweenCellsWidget.setItem(westWallWidget);
            }

            cellRow.add(westBetweenCellsWidget);

            CellWidget cellWidget = _widgetFactory.create(cell);

            cellRow.add(cellWidget);
        }

        Cell cell = _field.getCell(new MyPoint(rowIndex, _field.getWidth()));
        BetweenCellsWidget eastBetweenCellsWidget = new BetweenCellsWidget(Orientation.VERTICAL);
        WallSegment eastWallSegment = cell.getWallSegment(Direction.EAST);
        if (eastWallSegment != null) {
            WallSegmentWidget eastWallWidget = _widgetFactory.create(eastWallSegment, Orientation.VERTICAL);
            eastBetweenCellsWidget.setItem(eastWallWidget);
        }

        cellRow.add(eastBetweenCellsWidget);

        return cellRow;
    }

    private JPanel createRowHorizontalWalls(int rowIndex, Direction direction) {
        if (direction != Direction.NORTH && direction != Direction.SOUTH) throw new IllegalArgumentException("Неверно задано направление расположения стен");
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for (int i = 1; i <= _field.getWidth(); ++i) {
            Cell cell = _field.getCell(new MyPoint(i, rowIndex));

            BetweenCellsWidget betweenCellsWidget = new BetweenCellsWidget(Orientation.HORIZONTAL);
            WallSegment wallSegment = cell.getWallSegment(direction);

            if (wallSegment != null) {
                WallSegmentWidget wallSegmentWidget = _widgetFactory.create(wallSegment, Orientation.HORIZONTAL);
                betweenCellsWidget.setItem(wallSegmentWidget);
            }

            row.add(betweenCellsWidget);
        }

        return row;
    }
}
