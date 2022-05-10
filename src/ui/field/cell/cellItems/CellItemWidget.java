package ui.field.cell.cellItems;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class CellItemWidget extends JPanel {

    public CellItemWidget() {
        setOpaque(false);
        setPreferredSize(getDimension());
    }

    protected abstract BufferedImage getImage();

    protected abstract File getImageFile();

    protected abstract Dimension getDimension();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }
}
