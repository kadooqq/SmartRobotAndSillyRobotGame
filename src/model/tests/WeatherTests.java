package model.tests;

import model.field.Cell;
import model.field.Direction;
import model.field.Field;
import model.field.MyPoint;
import model.field.fieldObjects.WallSegment;
import model.field.fieldObjects.landscape.IceSegment;
import model.field.fieldObjects.landscape.SandSegment;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.weather.WeatherController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class WeatherTests {
    @Test
    public void makeLandscapeRainyTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.RAINY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        int cnt = 0;
        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(cnt, modifiedLandscape.size());

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() instanceof SwampSegment) {
                cnt++;
            }
        }

        Assertions.assertEquals(9, cnt);
    }

    @Test
    public void makeLandscapeRainyNearExitCellTest() {
        MyPoint exitCellPosition = new MyPoint(4, 4);
        Field field = new Field(10, 10, exitCellPosition);
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.RAINY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        int cnt = 0;
        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX() ||
                        curPosition.getY() == exitCellPosition.getY() && curPosition.getX() == exitCellPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(cnt, modifiedLandscape.size());

        Assertions.assertNull(field.getExitCell().getLandscapeSegment());
    }

    @Test
    public void makeLandscapeRainyNearWallSegmentTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        field.getCell(new MyPoint(5, 5)).setWallSegment(new WallSegment(), Direction.EAST);
        field.getCell(new MyPoint(5, 5)).setWallSegment(new WallSegment(), Direction.NORTH);
        field.getCell(new MyPoint(5, 5)).setWallSegment(new WallSegment(), Direction.SOUTH);
        field.getCell(new MyPoint(5, 5)).setWallSegment(new WallSegment(), Direction.WEST);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.RAINY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        int cnt = 0;
        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(cnt, modifiedLandscape.size());
    }

    @Test
    public void makeLandscapeRainyNearBorderOfFieldTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 10);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.RAINY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        int cnt = 0;
        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY(); ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(cnt, modifiedLandscape.size());
    }

    @Test
    public void makeLandscapeRainyTwoNeighbouringSwampSegmentsTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);
        MyPoint initialSwampSegmentPosition1 = new MyPoint(5, 6);
        SwampSegment initialSwampSegment1 = new SwampSegment();
        field.getCell(initialSwampSegmentPosition1).setLandscapeSegment(initialSwampSegment1);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.RAINY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());
        Assertions.assertEquals(initialSwampSegment1, field.getCell(initialSwampSegmentPosition1).getLandscapeSegment());

        int cnt = 0;
        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition1.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX()
                        || curPosition.getY() == initialSwampSegmentPosition1.getY() && curPosition.getX() == initialSwampSegmentPosition1.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(cnt, modifiedLandscape.size());
    }

    @Test
    public void makeLandscapeRainyNearSandSegmentTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        MyPoint sandSegmentPosition = new MyPoint(5, 6);
        SandSegment sandSegment = new SandSegment();
        field.getCell(sandSegmentPosition).setLandscapeSegment(sandSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.RAINY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        int cnt = 0;
        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(cnt, modifiedLandscape.size());
    }

    @Test
    public void makeLandscapeDryWithoutRainBeforeTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.DRY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        Assertions.assertEquals(0, modifiedLandscape.size());

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() instanceof SwampSegment) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);
    }

    @Test
    public void makeLandscapeDryAfterRainTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.RAINY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        int cnt = 0;
        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(cnt, modifiedLandscape.size());

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() instanceof SwampSegment) {
                cnt++;
            }
        }

        Assertions.assertEquals(9, cnt);

        weather.changeWeather(WeatherController.Weather.DRY);

        modifiedLandscape = weather.getChangedLandscapeCells();

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNull(currentCell.getLandscapeSegment());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        Assertions.assertEquals(8, modifiedLandscape.size());

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() instanceof SwampSegment) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);
    }

    @Test
    public void makeLandscapeDryAfterRainNearSandSegmentTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        MyPoint sandSegmentPosition = new MyPoint(5, 6);
        SandSegment sandSegment = new SandSegment();
        field.getCell(sandSegmentPosition).setLandscapeSegment(sandSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.RAINY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        int cnt = 0;
        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(cnt, modifiedLandscape.size());

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() instanceof SwampSegment) {
                cnt++;
            }
        }

        Assertions.assertEquals(9, cnt);

        weather.changeWeather(WeatherController.Weather.DRY);

        modifiedLandscape = weather.getChangedLandscapeCells();

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertFalse(currentCell.getLandscapeSegment() instanceof SwampSegment);
                    Assertions.assertTrue(modifiedLandscape.contains(currentCell));
                    cnt++;
                }
            }
        }

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        Assertions.assertEquals(8, modifiedLandscape.size());

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() instanceof SwampSegment) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);

        Assertions.assertEquals(sandSegment, field.getCell(sandSegmentPosition).getLandscapeSegment());
    }

    @Test
    public void makeLandscapeIcilyTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.ICILY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof IceSegment);

        Assertions.assertTrue(modifiedLandscape.contains(field.getCell(initialSwampSegmentPosition)));

        Assertions.assertEquals(1, modifiedLandscape.size());

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);
    }

    @Test
    public void makeLandscapeIcilyTwoSwampSegmentsTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        MyPoint initialSwampSegmentPosition1 = new MyPoint(5, 6);
        SwampSegment initialSwampSegment1 = new SwampSegment();
        field.getCell(initialSwampSegmentPosition1).setLandscapeSegment(initialSwampSegment1);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.ICILY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof IceSegment);
        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition1).getLandscapeSegment() instanceof IceSegment);

        Assertions.assertTrue(modifiedLandscape.contains(field.getCell(initialSwampSegmentPosition)));
        Assertions.assertTrue(modifiedLandscape.contains(field.getCell(initialSwampSegmentPosition1)));

        Assertions.assertEquals(2, modifiedLandscape.size());

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(2, cnt);
    }

    @Test
    public void makeLandscapeHeatWithoutIcilyBeforeTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.HEAT);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(0, modifiedLandscape.size());

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);
    }

    @Test
    public void makeLandscapeHeatAfterIcilyTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        WeatherController weather = new WeatherController(field);
        weather.changeWeather(WeatherController.Weather.ICILY);

        var modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof IceSegment);

        Assertions.assertTrue(modifiedLandscape.contains(field.getCell(initialSwampSegmentPosition)));

        Assertions.assertEquals(1, modifiedLandscape.size());

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);

        weather.changeWeather(WeatherController.Weather.HEAT);

        modifiedLandscape = weather.getChangedLandscapeCells();

        Assertions.assertEquals(1, modifiedLandscape.size());

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        Assertions.assertTrue(modifiedLandscape.contains(field.getCell(initialSwampSegmentPosition)));

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);
    }
}
