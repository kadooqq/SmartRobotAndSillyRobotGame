package model.field;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    private Direction _oppositeDirection;

    static {
        NORTH._oppositeDirection = SOUTH;
        SOUTH._oppositeDirection = NORTH;
        EAST._oppositeDirection = WEST;
        WEST._oppositeDirection = EAST;
    }

    public Direction getOppositeDirection() {
        return _oppositeDirection;
    }

    private Direction _clockwiseRotatedDirection;

    static {
        NORTH._clockwiseRotatedDirection = EAST;
        EAST._clockwiseRotatedDirection = SOUTH;
        SOUTH._clockwiseRotatedDirection = WEST;
        WEST._clockwiseRotatedDirection = NORTH;
    }

    public Direction getClockwiseRotatedDirection() {
        return _clockwiseRotatedDirection;
    }
}
