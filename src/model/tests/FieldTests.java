package model.tests;

import model.field.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FieldTests {
    @Test
    public void fieldTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        // Проверить размеры построенного поля и корректность установки точки выхода
        Assertions.assertEquals(height, field.getHeight());
        Assertions.assertEquals(width, field.getWidth());
        Assertions.assertEquals(width * height, field.getCells().size());
        Assertions.assertTrue(field.getCell(exitPointCoordinates) instanceof ExitCell);

        // Проверить, что точка выхода установлена в поле единожды
        int i = 1;
        for (var cell : field.getCells().entrySet()) {
            if (i != exitPointX * exitPointY) {
                Assertions.assertFalse(cell.getValue() instanceof ExitCell);
            }
            ++i;
        }

        // Проверить соседство угловых ячеек
        // Левая нижняя
        Assertions.assertEquals(2, field.getCell(new MyPoint(1, 1)).getNeighbourCells().size());
        Assertions.assertEquals(field.getCell(new MyPoint(1, 2)), field.getCell(new MyPoint(1, 1)).getNeighbourCell(Direction.NORTH));
        Assertions.assertEquals(field.getCell(new MyPoint(2, 1)), field.getCell(new MyPoint(1, 1)).getNeighbourCell(Direction.EAST));

        // Правая нижняя
        Assertions.assertEquals(2, field.getCell(new MyPoint(width, 1)).getNeighbourCells().size());
        Assertions.assertEquals(field.getCell(new MyPoint(width, 2)), field.getCell(new MyPoint(width, 1)).getNeighbourCell(Direction.NORTH));
        Assertions.assertEquals(field.getCell(new MyPoint(width - 1, 1)), field.getCell(new MyPoint(width, 1)).getNeighbourCell(Direction.WEST));

        // Правая верхняя
        Assertions.assertEquals(2, field.getCell(new MyPoint(1, height)).getNeighbourCells().size());
        Assertions.assertEquals(field.getCell(new MyPoint(width, height - 1)), field.getCell(new MyPoint(width, height)).getNeighbourCell(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(width - 1, height)), field.getCell(new MyPoint(width, height)).getNeighbourCell(Direction.WEST));

        // Левая верхняя
        Assertions.assertEquals(2, field.getCell(new MyPoint(width, height)).getNeighbourCells().size());
        Assertions.assertEquals(field.getCell(new MyPoint(1, height - 1)), field.getCell(new MyPoint(1, height)).getNeighbourCell(Direction.SOUTH));
        Assertions.assertEquals(field.getCell(new MyPoint(2, height)), field.getCell(new MyPoint(1, height)).getNeighbourCell(Direction.EAST));

        // Проверить соседство ячеек, составляющих грани поля без учета угловых ячеек
        // Левая грань
        for (i = 2; i < width; ++i) {
            Assertions.assertEquals(3, field.getCell(new MyPoint(i, 1)).getNeighbourCells().size());
            Assertions.assertEquals(field.getCell(new MyPoint(i, 2)), field.getCell(new MyPoint(i, 1)).getNeighbourCell(Direction.NORTH));
            Assertions.assertEquals(field.getCell(new MyPoint(i - 1, 1)), field.getCell(new MyPoint(i, 1)).getNeighbourCell(Direction.WEST));
            Assertions.assertEquals(field.getCell(new MyPoint(i + 1, 1)), field.getCell(new MyPoint(i, 1)).getNeighbourCell(Direction.EAST));
        }

        // Нижняя грань
        for (i = 2; i < height; ++i) {
            Assertions.assertEquals(3, field.getCell(new MyPoint(1, i)).getNeighbourCells().size());
            Assertions.assertEquals(field.getCell(new MyPoint(2, i)), field.getCell(new MyPoint(1, i)).getNeighbourCell(Direction.EAST));
            Assertions.assertEquals(field.getCell(new MyPoint(1, i - 1)), field.getCell(new MyPoint(1, i)).getNeighbourCell(Direction.SOUTH));
            Assertions.assertEquals(field.getCell(new MyPoint(1, i + 1)), field.getCell(new MyPoint(1, i)).getNeighbourCell(Direction.NORTH));
        }

        // Верхняя грань
        for (i = 2; i < width; ++i) {
            Assertions.assertEquals(3, field.getCell(new MyPoint(i, height)).getNeighbourCells().size());
            Assertions.assertEquals(field.getCell(new MyPoint(i, height - 1)), field.getCell(new MyPoint(i, height)).getNeighbourCell(Direction.SOUTH));
            Assertions.assertEquals(field.getCell(new MyPoint(i - 1, height)), field.getCell(new MyPoint(i, height)).getNeighbourCell(Direction.WEST));
            Assertions.assertEquals(field.getCell(new MyPoint(i + 1, height)), field.getCell(new MyPoint(i, height)).getNeighbourCell(Direction.EAST));
        }

        // Правая грань
        for (i = 2; i < height; ++i) {
            Assertions.assertEquals(3, field.getCell(new MyPoint(width, i)).getNeighbourCells().size());
            Assertions.assertEquals(field.getCell(new MyPoint(width - 1, i)), field.getCell(new MyPoint(width, i)).getNeighbourCell(Direction.WEST));
            Assertions.assertEquals(field.getCell(new MyPoint(width, i - 1)), field.getCell(new MyPoint(width, i)).getNeighbourCell(Direction.SOUTH));
            Assertions.assertEquals(field.getCell(new MyPoint(width, i + 1)), field.getCell(new MyPoint(width, i)).getNeighbourCell(Direction.NORTH));
        }

        // Проверить соседство внутренних ячеек
        for (i = 2; i < width; ++i) {
            for (int j = 2; j < height; ++j) {
                Assertions.assertEquals(4, field.getCell(new MyPoint(i, j)).getNeighbourCells().size());
                Assertions.assertEquals(field.getCell(new MyPoint(i, j + 1)), field.getCell(new MyPoint(i, j)).getNeighbourCell(Direction.NORTH));
                Assertions.assertEquals(field.getCell(new MyPoint(i + 1, j)), field.getCell(new MyPoint(i, j)).getNeighbourCell(Direction.EAST));
                Assertions.assertEquals(field.getCell(new MyPoint(i, j - 1)), field.getCell(new MyPoint(i, j)).getNeighbourCell(Direction.SOUTH));
                Assertions.assertEquals(field.getCell(new MyPoint(i - 1, j)), field.getCell(new MyPoint(i, j)).getNeighbourCell(Direction.WEST));
            }
        }
    }

    @Test
    public void getCellByIndexTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        Assertions.assertEquals(field.getCell(new MyPoint(1, 2)), field.getCellByIndex(5));
    }

    @Test
    public void wrongGetCellByIndexTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);

        Assertions.assertNull(field.getCellByIndex(50));
    }

    @Test
    public void wrongGetIndexOfCellTest() {
        int exitPointX = 1, exitPointY = 1;
        MyPoint exitPointCoordinates = new MyPoint(exitPointX, exitPointY);
        int width = 5, height = 5;
        Field field = new Field(width, height, exitPointCoordinates);
        Cell cell = new Cell(new MyPoint(1, 1));

        Assertions.assertEquals(-1, field.getIndexOfCell(cell));
    }
}
