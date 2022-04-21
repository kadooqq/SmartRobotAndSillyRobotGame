package model.tests;

import model.field.Direction;
import model.field.MyPoint;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MyPointTests {
    //------------------------------------------------ Конструктор, геттеры --------------------------------------------
    @Test
    public void simpleCreationAndGettersTest() {
        final int x = 1, y = 1;
        MyPoint myPoint = new MyPoint(x, y);

        Assertions.assertEquals(x, myPoint.getX());
        Assertions.assertEquals(y, myPoint.getY());
    }

    @Test
    public void negNumsCreationAndGettersTest() {
        final int x = -1, y = -1;
        MyPoint myPoint = new MyPoint(x, y);

        Assertions.assertEquals(x, myPoint.getX());
        Assertions.assertEquals(y, myPoint.getY());
    }

    //------------------------------------------------ equals ----------------------------------------------------------
    @Test
    public void equalsTest() {
        MyPoint point1 = new MyPoint(0, 0);
        MyPoint point2 = new MyPoint(0, 0);
        boolean expected = true;

        Assertions.assertEquals(expected, point1.equals(point2));
    }

    @Test
    public void notEqualXEqualsTest() {
        MyPoint point1 = new MyPoint(1, 0);
        MyPoint point2 = new MyPoint(0, 0);
        boolean expected = false;

        Assertions.assertEquals(expected, point1.equals(point2));
    }

    @Test
    public void notEqualYEqualsTest() {
        MyPoint point1 = new MyPoint(0, 1);
        MyPoint point2 = new MyPoint(0, 0);
        boolean expected = false;

        Assertions.assertEquals(expected, point1.equals(point2));
    }

    @Test
    public void notEqualButEqualInAbsoluteEqualsTest() {
        MyPoint point1 = new MyPoint(1, 1);
        MyPoint point2 = new MyPoint(-1, -1);
        boolean expected = false;

        Assertions.assertEquals(expected, point1.equals(point2));
    }

    //------------------------------------------------ compareTo -------------------------------------------------------
    @Test
    public void XBiggerYEqualsCompareToTest() {
        MyPoint point1 = new MyPoint(2, 1);
        MyPoint point2 = new MyPoint(1, 1);
        int expected = 1;

        Assertions.assertEquals(expected, point1.compareTo(point2));
    }

    @Test
    public void XSmallerYEqualsCompareToTest() {
        MyPoint point1 = new MyPoint(0, 1);
        MyPoint point2 = new MyPoint(1, 1);
        int expected = -1;

        Assertions.assertEquals(expected, point1.compareTo(point2));
    }

    @Test
    public void XEqualsYEqualsCompareToTest() {
        MyPoint point1 = new MyPoint(1, 1);
        MyPoint point2 = new MyPoint(1, 1);
        int expected = 0;

        Assertions.assertEquals(expected, point1.compareTo(point2));
    }

    @Test
    public void XBiggerYSmallerCompareToTest() {
        MyPoint point1 = new MyPoint(2, 0);
        MyPoint point2 = new MyPoint(1, 1);
        int expected = -1;

        Assertions.assertEquals(expected, point1.compareTo(point2));
    }

    @Test
    public void XEqualsYBiggerCompareToTest() {
        MyPoint point1 = new MyPoint(1, 3);
        MyPoint point2 = new MyPoint(1, 1);
        int expected = 1;

        Assertions.assertEquals(expected, point1.compareTo(point2));
    }

    @Test
    public void XEqualsYSmallerCompareToTest() {
        MyPoint point1 = new MyPoint(1, 0);
        MyPoint point2 = new MyPoint(1, 1);
        int expected = -1;

        Assertions.assertEquals(expected, point1.compareTo(point2));
    }

    //------------------------------------------------ getDistance -----------------------------------------------------
    @Test
    public void getDistanceTest() {
        MyPoint point1 = new MyPoint(1, 1);
        MyPoint point2 = new MyPoint(3, 3);
        MyPoint expected = new MyPoint(2, 2);

        Assertions.assertEquals(expected, point1.getDistance(point2));
    }

    @Test
    public void anotherGetDistanceTest() {
        MyPoint point1 = new MyPoint(3, 3);
        MyPoint point2 = new MyPoint(1, 1);
        MyPoint expected = new MyPoint(2, 2);

        Assertions.assertEquals(expected, point1.getDistance(point2));
    }

    @Test
    public void negativeValuesFirstPointGetDistanceTest() {
        MyPoint point1 = new MyPoint(-3, -3);
        MyPoint point2 = new MyPoint(1, 1);
        MyPoint expected = new MyPoint(4, 4);

        Assertions.assertEquals(expected, point1.getDistance(point2));
    }

    @Test
    public void negativeValuesSecondPointGetDistanceTest() {
        MyPoint point1 = new MyPoint(3, 3);
        MyPoint point2 = new MyPoint(-1, -1);
        MyPoint expected = new MyPoint(4, 4);

        Assertions.assertEquals(expected, point1.getDistance(point2));
    }

    @Test
    public void allValuesNegativeGetDistanceTest() {
        MyPoint point1 = new MyPoint(-3, -3);
        MyPoint point2 = new MyPoint(-1, -1);
        MyPoint expected = new MyPoint(2, 2);

        Assertions.assertEquals(expected, point1.getDistance(point2));
    }

    //------------------------------------------------ getDifference ---------------------------------------------------
    @Test
    public void getDifferenceTest() {
        MyPoint point1 = new MyPoint(1, 1);
        MyPoint point2 = new MyPoint(3, 3);
        MyPoint expected = new MyPoint(2, 2);

        Assertions.assertEquals(expected, point1.getDifference(point2));
    }

    @Test
    public void anotherGetDifferenceTest() {
        MyPoint point1 = new MyPoint(3, 3);
        MyPoint point2 = new MyPoint(1, 1);
        MyPoint expected = new MyPoint(-2, -2);

        Assertions.assertEquals(expected, point1.getDifference(point2));
    }

    @Test
    public void negativeValuesFirstPointGetDifferenceTest() {
        MyPoint point1 = new MyPoint(-3, -3);
        MyPoint point2 = new MyPoint(1, 1);
        MyPoint expected = new MyPoint(4, 4);

        Assertions.assertEquals(expected, point1.getDifference(point2));
    }

    @Test
    public void negativeValuesSecondPointGetDifferenceTest() {
        MyPoint point1 = new MyPoint(3, 3);
        MyPoint point2 = new MyPoint(-1, -1);
        MyPoint expected = new MyPoint(-4, -4);

        Assertions.assertEquals(expected, point1.getDifference(point2));
    }

    @Test
    public void allValuesNegativeGetDifferenceTest() {
        MyPoint point1 = new MyPoint(-3, -3);
        MyPoint point2 = new MyPoint(-1, -1);
        MyPoint expected = new MyPoint(2, 2);

        Assertions.assertEquals(expected, point1.getDifference(point2));
    }

    //------------------------------------------------ getNeighbourPoint -----------------------------------------------
    @Test
    public void northGetNeighbourPoint() {
        MyPoint point1 = new MyPoint(0, 0);
        MyPoint expected = new MyPoint(0, 1);
        Direction direction = Direction.NORTH;

        Assertions.assertEquals(expected, point1.getNeighbourPoint(direction));
    }

    @Test
    public void eastGetNeighbourPoint() {
        MyPoint point1 = new MyPoint(0, 0);
        MyPoint expected = new MyPoint(1, 0);
        Direction direction = Direction.EAST;

        Assertions.assertEquals(expected, point1.getNeighbourPoint(direction));
    }

    @Test
    public void southGetNeighbourPoint() {
        MyPoint point1 = new MyPoint(0, 0);
        MyPoint expected = new MyPoint(0, -1);
        Direction direction = Direction.SOUTH;

        Assertions.assertEquals(expected, point1.getNeighbourPoint(direction));
    }

    @Test
    public void westGetNeighbourPoint() {
        MyPoint point1 = new MyPoint(0, 0);
        MyPoint expected = new MyPoint(-1, 0);
        Direction direction = Direction.WEST;

        Assertions.assertEquals(expected, point1.getNeighbourPoint(direction));
    }
}
