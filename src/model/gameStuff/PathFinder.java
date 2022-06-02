package model.gameStuff;

import model.field.Cell;
import model.field.Direction;
import model.field.Field;
import model.field.fieldObjects.landscape.RockSegment;
import model.field.fieldObjects.robot.BigRobot;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PathFinder {
    public PathFinder(Field field) {
        _field = field;
    }

    private final Field _field;

    private BigRobot _robot;

    public void setRobot(BigRobot robot) {
        if (_robot == robot) return;
        forgetRobot();
        _robot = robot;
        _robot.setPathFinder(this);
    }

    public BigRobot getRobot() {
        return _robot;
    }

    public void forgetRobot() {
        var robot = _robot;
        _robot = null;
        if (robot != null) robot.dropPathFinder();
    }

    public Stack<Cell> findPath(Cell fromCell, Cell toCell) {
        if (_field == null || !_field.contains(fromCell) || !_field.contains(toCell)) return null;
        int indexFromCell = _field.getIndexOfCell(fromCell);
        int indexToCell = _field.getIndexOfCell(toCell);

        var markedCells = WavePropagation(indexFromCell, indexToCell);

        return PathRecovery(indexFromCell, indexToCell, markedCells);
    }

    private int[] WavePropagation(int fromCellIndex, int toCellIndex) {  // распространение волны
        int[] markedCells = new int[_field.getCells().size()]; // массив, где будут хранится "отметки" каждого узла
        int markNumber = 1;                        // счетчик
        markedCells[fromCellIndex] = markNumber;         // инициализация стартового узла
        while (markedCells[toCellIndex] == 0) {          // пока не достигли финишного узла
            var cpyMarkedCells = markedCells.clone();
            for (int i = 0; i < markedCells.length; i++) {
                if (markedCells[i] == markNumber) {                                          // начинаем со стартового узла
                    var neighbours = getAvailableNeighbours(_field.getCellByIndex(i));
                    for (Cell neighbour : neighbours) {       // просматриваем все соседние узлы
                        int cellIndex = _field.getIndexOfCell(neighbour);
                        if (markedCells[cellIndex] == 0) {    // если он еще не получил "отметку"
                            markedCells[cellIndex] = (markNumber + 1);
                        }
                    }
                }
            }
            markNumber++;
            if (compareArrays(markedCells, cpyMarkedCells)) return new int[0];
        }
        return markedCells;
    }

    private boolean compareArrays(int[] first, int[] second) {
        if (first.length != second.length) return false;

        for (int i = 0; i < first.length; ++i) {
            if (first[i] != second[i]) return false;
        }

        return true;
    }

    private Stack<Cell> PathRecovery(int fromCellIndex, int toCellIndex, int[] markedCells) {  // восстановление пути
        Stack<Cell> paramsPaveTheRoute = new Stack<>();      // массив, где хранится путь
        if (markedCells.length == 0) return paramsPaveTheRoute;
        if (markedCells[toCellIndex] != 0) {   // еще раз проверяем дошел ли алгоритм до финишного узла
            Cell currentNode = _field.getCellByIndex(toCellIndex);
            paramsPaveTheRoute.add(currentNode);                             // добавляем финишный узел к пути
            int currentCellIndex = _field.getIndexOfCell(currentNode);
            while (currentCellIndex != fromCellIndex) {                     // пока не дошли до стартового узла
                var neighbours = getAvailableNeighbours(currentNode);
                for (Cell neighbour : neighbours) {      // проверяем соседние узлы
                    if (markedCells[_field.getIndexOfCell(neighbour)]
                            == markedCells[currentCellIndex] - 1) {     // если значение пометки узла на 1 меньше, чем у предыдущего узла
                        currentNode = neighbour; //узел становится текущим
                        currentCellIndex = _field.getIndexOfCell(currentNode);
                        paramsPaveTheRoute.push(currentNode);      // заносится в массив
                        break;
                    }
                }
            }
        }
        return paramsPaveTheRoute;
    }

    private List<Cell> getAvailableNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        for (var cellEntry : cell.getNeighbourCells().entrySet()) {
            if (moveIsPossible(cell, cellEntry.getKey())) {
                neighbours.add(cellEntry.getValue());
            }
        }

        return neighbours;
    }

    private boolean moveIsPossible(Cell position, Direction direction){
        return position.getNeighbourCell(direction) != null && position.getWallSegment(direction) == null && !(position.getNeighbourCell(direction).getLandscapeSegment() instanceof RockSegment);
    }
}
