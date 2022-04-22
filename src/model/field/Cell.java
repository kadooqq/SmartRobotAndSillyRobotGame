package model.field;

import model.fieldObjects.WallSegment;
import model.fieldObjects.landscape.LandscapeSegment;
import model.fieldObjects.robot.Robot;

import java.util.Stack;
import java.util.TreeMap;

public class Cell {
    public Cell(MyPoint coordinates) {
        _coordinates = coordinates;
    }

    // ----------------------------------------------- Координаты ------------------------------------------------------
    private final MyPoint _coordinates;

    // ----------------------------------------------- Соседние клетки -------------------------------------------------
    private final TreeMap<Direction, Cell> _neighbourCells = new TreeMap<>();

    public TreeMap<Direction, Cell> getNeighbourCells() {
        return _neighbourCells;
    }

    public boolean setNeighbourCell(Cell cell, final Direction direction) {
        if (cell == null || direction == null) return false;
        if (_neighbourCells.get(direction) == null) {
            _neighbourCells.put(direction, cell);
            return cell.setNeighbourCell(this, direction.getOppositeDirection());
        }
        return cell._neighbourCells.get(direction.getOppositeDirection()) == this;
    }

    public Cell getNeighbourCell(final Direction direction) {
        if (direction == null) return null;
        return _neighbourCells.get(direction);
    }

    public Direction getNeighbourDirection(final Cell cell) {
        if (cell == null) return null;
        Direction direction = null;
        for (var neighbourCell : _neighbourCells.entrySet()) {
            if (neighbourCell.getValue() == cell) {
                direction = neighbourCell.getKey();
            }
        }
        return direction;
    }

    // ----------------------------------------------- Работа с клетками -----------------------------------------------
    public MyPoint getDistanceFromCellToCell(final Cell otherCell) {
        if (otherCell == null) return null;
        return _coordinates.getDistance(otherCell._coordinates);
    }

    public Stack<Direction> getDirectionsToMove(final Cell otherCell) {
        if (otherCell == null) return null;
        Stack<Direction> directions = new Stack<>();
        var difference = _coordinates.getDifference(otherCell._coordinates);

        if (difference.getY() > 0) directions.push(Direction.NORTH);
        else if (difference.getY() < 0) directions.push(Direction.SOUTH);

        if (difference.getX() > 0) directions.push(Direction.EAST);
        else if (difference.getX() < 0) directions.push(Direction.WEST);

        return directions;
    }

    // ----------------------------------------------- Робот -----------------------------------------------------------
    private Robot _robot = null;

    public boolean setRobot(Robot robot) {
        if (robot == null) return false;
        if (_robot == robot) return true;
        if (!robot.canBeLocatedAtPosition(this)) return false;
        _robot = robot;
        return _robot.setPosition(this);
    }

    public Robot getRobot() {
        return _robot;
    }

    public Robot takeRobot() {
        _robot.setNullPosition();
        Robot temp = _robot;
        _robot = null;
        return temp;
    }

    // ----------------------------------------------- Стены -----------------------------------------------------------
    private final TreeMap<Direction, WallSegment> _wallSegments = new TreeMap<>();

    public WallSegment getWallSegment(final Direction direction) {
        if (direction == null) return null;
        return _wallSegments.get(direction);
    }

    public boolean setWallSegment(WallSegment wallSegment, final Direction direction) {
        if (wallSegment == null || direction == null) return false;
        if (_wallSegments.get(direction) != null) return _wallSegments.get(direction) == wallSegment;
        _wallSegments.put(direction, wallSegment);
        if (_neighbourCells.get(direction) != null) {
            _neighbourCells.get(direction).setWallSegment(wallSegment, direction.getOppositeDirection());
            return wallSegment.setPosition(new BetweenCellPosition(this, _neighbourCells.get(direction)));
        }
        return wallSegment.setPosition(new BetweenCellPosition(this, direction));
    }

    // ----------------------------------------------- Ландшафт --------------------------------------------------------
    private LandscapeSegment _landscapeSegment = null;

    public LandscapeSegment getLandscapeSegment() {
        return _landscapeSegment;
    }

    public boolean setLandscapeSegment(final LandscapeSegment landscapeSegment) {
        if (landscapeSegment == null) return false;
        if (landscapeSegment.setPosition(this)) {
            _landscapeSegment = landscapeSegment;
            return true;
        }

        return false;
    }
}
