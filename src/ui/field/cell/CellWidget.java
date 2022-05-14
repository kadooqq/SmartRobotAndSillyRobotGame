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
        TOP(0),
        BOTTOM(-1);

        private final int _value;
        Layer(int value) {
            _value = value;
        }

        public int getValue() {
            return _value;
        }
    }

    private final HashMap<Layer, CellItemWidget> _widgetItems = new HashMap<>();

    public void addItem(CellItemWidget widgetItem) {
        if (_widgetItems.size() > 2)
            throw new IllegalArgumentException("Попытка установить в ячейку более двух виджетов");

        Layer layer = widgetItem instanceof RobotWidget ? layer = Layer.TOP : Layer.BOTTOM;

        _widgetItems.put(layer, widgetItem);
        add(widgetItem, layer.getValue());
    }

    public void removeItem(CellItemWidget widgetItem) {
        Layer itemLayer = null;
        for (var item : _widgetItems.entrySet()) {
            if (widgetItem == item.getValue()) {
                itemLayer = item.getKey();
            }
        }
        _widgetItems.remove(itemLayer);
        remove(itemLayer.getValue());
        repaint();
    }
}
