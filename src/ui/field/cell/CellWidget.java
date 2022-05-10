package ui.field.cell;

import ui.field.cell.cellItems.CellItemWidget;
import ui.field.cell.cellItems.robots.RobotWidget;
import ui.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CellWidget extends JPanel {

    public CellWidget() {
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        setBackground(ImageUtils.BACKGROUND_COLOR);
    }

    private static final int CELL_SIZE = 120;

    private enum Layer {
        TOP,
        BOTTOM
    }

    private final HashMap<Layer, CellItemWidget> _widgetItems = new HashMap<>();

    public void addItem(CellItemWidget widgetItem) {
        if (_widgetItems.size() > 2)
            throw new IllegalArgumentException("Попытка установить в ячейку более двух виджетов");

        int index = -1;
        Layer layer = Layer.BOTTOM;

        if (widgetItem instanceof RobotWidget) {
            index = 0;
            layer = Layer.TOP;
        }

        _widgetItems.put(layer, widgetItem);
        add(widgetItem, index);
    }

    public void removeItem(CellItemWidget widgetItem) {
        for (var item : _widgetItems.entrySet()) {
            if (widgetItem == item.getValue()) {
                remove(item.getKey() == Layer.TOP ? 0 : -1);
                _widgetItems.remove(item.getKey());
                repaint();
            }
        }
    }
}
