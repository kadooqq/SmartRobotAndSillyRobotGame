package model.field;

import model.events.RobotTeleportEvent;
import model.field.fieldObjects.robot.Robot;
import model.field.weather.SeasonController;
import model.listeners.ExitCellListener;

import java.util.ArrayList;
import java.util.TreeMap;

public class Field implements ExitCellListener {
    public Field(int width, int height, MyPoint exitPoint) {
        _width = width;
        _height = height;
        buildField(exitPoint);
        _cellsArray = new ArrayList<>(_cells.values());
    }

    // ----------------------------------------------- Погода ----------------------------------------------------------
    private SeasonController _seasonController = null;

    public void setSeason(SeasonController.Season season) {
        _seasonController = new SeasonController(this, season);
    }

    public SeasonController getSeasonController() {
        return _seasonController;
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

    private final ArrayList<Cell> _cellsArray;

    public TreeMap<MyPoint, Cell> getCells() {
        return _cells;
    }

    public Cell getCell(MyPoint coordinates) {
        return _cells.get(coordinates);
    }

    public int getIndexOfCell(Cell cell) {
        return _cellsArray.indexOf(cell);
    }

    public Cell getCellByIndex(int index) {
        Cell cell = null;
        try {
            cell = _cellsArray.get(index);
        } catch (Exception ignored) {}
        return cell;
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
        _robots.remove(e.getTeleportedRobot());
    }
}
