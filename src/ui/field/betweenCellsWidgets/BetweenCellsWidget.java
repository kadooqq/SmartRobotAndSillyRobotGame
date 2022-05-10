package ui.field.betweenCellsWidgets;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class BetweenCellsWidget extends JPanel {
    public BetweenCellsWidget(Orientation orientation) {
        super(new BorderLayout());
        _orientation = orientation;
        setPreferredSize(getDimensionsByOrientation());
        setBackground(Color.BLACK);
    }

    private final Orientation _orientation;

    public void setItem(@NotNull WallSegmentWidget wallSegmentWidget) {
        if (wallSegmentWidget.getOrientation() != _orientation) throw new IllegalArgumentException("Ориентация контейнера не совпадает с ориентацией стены");
        add(wallSegmentWidget);
    }

    private Dimension getDimensionsByOrientation() {
        return (_orientation == Orientation.VERTICAL) ? new Dimension(3, 120) : new Dimension(125, 3);
    }
}
