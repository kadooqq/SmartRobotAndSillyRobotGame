package model.tests;

import model.events.LittleRobotEndStepEvent;
import model.field.Cell;
import model.field.Field;
import model.field.MyPoint;
import model.field.fieldObjects.landscape.IceSegment;
import model.field.fieldObjects.landscape.SwampSegment;
import model.field.weather.SeasonController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SeasonTests {
    @Test
    public void changeSeasonFromSummerToAutumnTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        field.setSeason(SeasonController.Season.SUMMER);

        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof SwampSegment);

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNull(currentCell.getLandscapeSegment());
                }
            }
        }

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);

        field.getSeasonController().changeSeason(SeasonController.Season.AUTUMN);

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                }
            }
        }

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(9, cnt);
    }

    @Test
    public void changeSeasonFromAutumnToWinterTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        field.setSeason(SeasonController.Season.AUTUMN);

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                }
            }
        }

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(9, cnt);

        field.getSeasonController().changeSeason(SeasonController.Season.WINTER);

        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof IceSegment);

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNull(currentCell.getLandscapeSegment());
                }
            }
        }

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);
    }

    @Test
    public void changeSeasonFromWinterToSpringTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        field.setSeason(SeasonController.Season.WINTER);

        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof IceSegment);

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNull(currentCell.getLandscapeSegment());
                }
            }
        }

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);

        field.getSeasonController().changeSeason(SeasonController.Season.SPRING);

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                }
            }
        }

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(9, cnt);
    }

    @Test
    public void changeSeasonFromSpringToSummerTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        field.setSeason(SeasonController.Season.SPRING);

        Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNotNull(currentCell.getLandscapeSegment());
                    Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                }
            }
        }

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(9, cnt);

        field.getSeasonController().changeSeason(SeasonController.Season.SUMMER);

        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof SwampSegment);

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNull(currentCell.getLandscapeSegment());
                }
            }
        }

        cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);
    }

    @Test
    public void changingSeasonsTest() {
        Field field = new Field(10, 10, new MyPoint(1, 1));
        MyPoint initialSwampSegmentPosition = new MyPoint(5, 5);
        SwampSegment initialSwampSegment = new SwampSegment();
        field.getCell(initialSwampSegmentPosition).setLandscapeSegment(initialSwampSegment);

        field.setSeason(SeasonController.Season.SUMMER);

        // Лето
        int numOfStep = 0;
        for (; numOfStep < 3; ++numOfStep) {
            Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof SwampSegment);

            for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
                for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                    MyPoint curPosition = new MyPoint(i, j);
                    Cell currentCell = field.getCell(curPosition);
                    if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                        Assertions.assertNull(currentCell.getLandscapeSegment());
                    }
                }
            }

            int cnt = 0;
            for (var cell : field.getCells().values()) {
                if (cell.getLandscapeSegment() != null) {
                    cnt++;
                }
            }

            Assertions.assertEquals(1, cnt);

            field.getSeasonController().littleRobotEndedStep(new LittleRobotEndStepEvent(this));
        }

        // Осень
        for (; numOfStep < 6; ++numOfStep) {
            Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

            for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
                for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                    MyPoint curPosition = new MyPoint(i, j);
                    Cell currentCell = field.getCell(curPosition);
                    if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                        Assertions.assertNotNull(currentCell.getLandscapeSegment());
                        Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    }
                }
            }

            int cnt = 0;
            for (var cell : field.getCells().values()) {
                if (cell.getLandscapeSegment() != null) {
                    cnt++;
                }
            }

            Assertions.assertEquals(9, cnt);
            field.getSeasonController().littleRobotEndedStep(new LittleRobotEndStepEvent(this));
        }

        // Зима
        for (; numOfStep < 9; ++numOfStep) {
            Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof IceSegment);

            for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
                for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                    MyPoint curPosition = new MyPoint(i, j);
                    Cell currentCell = field.getCell(curPosition);
                    if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                        Assertions.assertNull(currentCell.getLandscapeSegment());
                    }
                }
            }

            int cnt = 0;
            for (var cell : field.getCells().values()) {
                if (cell.getLandscapeSegment() != null) {
                    cnt++;
                }
            }

            Assertions.assertEquals(1, cnt);
            field.getSeasonController().littleRobotEndedStep(new LittleRobotEndStepEvent(this));
        }

        // Весна
        for (; numOfStep < 12; ++numOfStep) {
            Assertions.assertEquals(initialSwampSegment, field.getCell(initialSwampSegmentPosition).getLandscapeSegment());

            for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
                for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                    MyPoint curPosition = new MyPoint(i, j);
                    Cell currentCell = field.getCell(curPosition);
                    if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                        Assertions.assertNotNull(currentCell.getLandscapeSegment());
                        Assertions.assertEquals(SwampSegment.class, currentCell.getLandscapeSegment().getClass());
                    }
                }
            }

            int cnt = 0;
            for (var cell : field.getCells().values()) {
                if (cell.getLandscapeSegment() != null) {
                    cnt++;
                }
            }

            Assertions.assertEquals(9, cnt);
            field.getSeasonController().littleRobotEndedStep(new LittleRobotEndStepEvent(this));
        }

        // Лето
        Assertions.assertTrue(field.getCell(initialSwampSegmentPosition).getLandscapeSegment() instanceof SwampSegment);

        for (int i = initialSwampSegmentPosition.getX() - 1; i <= initialSwampSegmentPosition.getX() + 1; ++i) {
            for (int j = initialSwampSegmentPosition.getY() - 1; j <= initialSwampSegmentPosition.getY() + 1; ++j) {
                MyPoint curPosition = new MyPoint(i, j);
                Cell currentCell = field.getCell(curPosition);
                if (!(curPosition.getY() == initialSwampSegmentPosition.getY() && curPosition.getX() == initialSwampSegmentPosition.getX())) {
                    Assertions.assertNull(currentCell.getLandscapeSegment());
                }
            }
        }

        int cnt = 0;
        for (var cell : field.getCells().values()) {
            if (cell.getLandscapeSegment() != null) {
                cnt++;
            }
        }

        Assertions.assertEquals(1, cnt);
    }
}
