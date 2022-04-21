package model.field;

public class MyPoint implements Comparable {
    public MyPoint(int x, int y) {
        _x = x;
        _y = y;
    }

    private final int _x, _y;

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public MyPoint getDistance(final MyPoint otherPoint) {
        if (otherPoint == null) return null;
        return new MyPoint(Math.abs(getX() - otherPoint.getX()), Math.abs(getY() - otherPoint.getY()));
    }

    public MyPoint getDifference(final MyPoint otherPoint) {
        if (otherPoint == null) return null;
        return new MyPoint(otherPoint.getX() - getX(), otherPoint.getY() - getY());
    }

    public MyPoint getNeighbourPoint(Direction direction) {
        if (direction == null) return null;
        return switch (direction) {
            case NORTH -> new MyPoint(getX(), getY() + 1);
            case EAST -> new MyPoint(getX() + 1, getY());
            case SOUTH -> new MyPoint(getX(), getY() - 1);
            case WEST -> new MyPoint(getX() - 1, getY());
        };
    }

    public boolean equals(Object obj) {
        if (obj instanceof MyPoint) {
            MyPoint pt = (MyPoint) obj;
            return (getX() == pt.getX()) && (getY() == pt.getY());
        }
        return super.equals(obj);
    }

    public int compareTo(Object obj) {
        MyPoint p = (MyPoint) obj;
        if (getY() < p.getY() || getY() == p.getY() && getX() < p.getX()) return -1;
        if (getY() > p.getY() || getY() == p.getY() && getX() > p.getX()) return 1;
        return 0;
    }
}
