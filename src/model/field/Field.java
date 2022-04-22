package model.field;

import model.events.RobotTeleportEvent;
import model.fieldObjects.robot.Robot;
import model.listeners.ExitCellListener;

import java.util.ArrayList;
import java.util.TreeMap;

public class Field implements ExitCellListener {
    public Field(int width, int height, MyPoint exitPoint) {
        _width = width;
        _height = height;
        buildField(exitPoint);
    }

    // ----------------------------------------------- Высота и ширина поле --------------------------------------------
    private final int _width, _height;

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    // ----------------------------------------------- Ячейки поля -----------------------------------------------------
    private final TreeMap<MyPoint, Cell> _cells = new TreeMap<>();

    public TreeMap<MyPoint, Cell> getCells() {
        return _cells;
    }

    public Cell getCell(MyPoint coordinates) {
        return _cells.get(coordinates);
    }

    public int getIndexOfCell(Cell cell) {
        int i = 0;
        for (Cell c : _cells.values()) {
            if (c == cell) return i;
            ++i;
        }
        return -1;
    }

    public Cell getCellByIndex(int index) {
        int i = 0;
        for (Cell cell : _cells.values()) {
            if (i == index) return cell;
            ++i;
        }
        return null;
    }

    public boolean contains(Cell cell) {
        return _cells.containsValue(cell);
    }

    // ----------------------------------------------- Роботы, находящиеся на поле -------------------------------------
    private final ArrayList<Robot> _robots = new ArrayList<>();

    public ArrayList<Robot> getRobots() {
        if (!_robots.isEmpty()) return _robots;
        for (var cell : getCells().values()) {
            if (cell.getRobot() != null) {
                _robots.add(cell.getRobot());
            }
        }
        return _robots;
    }

    // ----------------------------------------------- Точка выхода ----------------------------------------------------

    private ExitCell _exitCell;

    public ExitCell getExitCell() {
        return _exitCell;
    }

    // ----------------------------------------------- Построение поля из ячеек с точкой выхода в exitPoint ------------

    private void buildField(MyPoint exitPoint) {
        for (int i = 1; i <= _width; ++i) {
            for (int j = 1; j <= _height; ++j) {
                MyPoint cellCoordinates = new MyPoint(i, j);
                if (cellCoordinates.equals(exitPoint)) {
                    _exitCell = new ExitCell(cellCoordinates);
                    _cells.put(cellCoordinates, _exitCell);
                } else {
                    _cells.put(cellCoordinates, new Cell(cellCoordinates));
                }
            }
        }

        for (var cell : _cells.entrySet()) {
            for (var direction : Direction.values()) {
                Cell neighbourCell = _cells.get(cell.getKey().getNeighbourPoint(direction));
                if (neighbourCell != null) {
                    cell.getValue().setNeighbourCell(neighbourCell, direction);
                }
            }
        }
    }

    // ----------------------------------------------- Наблюдение за точкой выхода -------------------------------------

    @Override
    public void robotTeleported(RobotTeleportEvent e) {
        _robots.remove((Robot)e.getTeleportedRobot());
    }
}
