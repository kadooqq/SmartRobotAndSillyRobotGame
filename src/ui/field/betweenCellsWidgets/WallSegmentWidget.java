package ui.field.betweenCellsWidgets;

import ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WallSegmentWidget extends JPanel {
    public WallSegmentWidget(Orientation orientation) {
        _orientation = orientation;
        setPreferredSize(getDimensionByOrientation());
    }

    private static File _verticalWallFile = null;
    private static File _horizontalWallFile = null;

    private final Orientation _orientation;

    public Orientation getOrientation() {
        return _orientation;
    }

    private BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            Dimension dimension = getDimensionByOrientation();
            image = ImageUtils.resizeImage(image, dimension.width, dimension.height);
        } catch (IOException e) {
            e.printStackTrace();    // !!! Для конечного пользователя это не лучшее решение (более дружественное сообщение для пользователя) TODO
        }
        return image;
    }

    protected File getImageFile() {
        switch(_orientation) {
            case VERTICAL -> {if (_verticalWallFile == null) _verticalWallFile = new File(ImageUtils.IMAGE_PATH + ImageUtils.VERTICAL_WALL_IMAGE);}
            case HORIZONTAL -> {if (_horizontalWallFile == null) _horizontalWallFile = new File(ImageUtils.IMAGE_PATH + ImageUtils.HORIZONTAL_WALL_IMAGE);}
        }
        return (_orientation == Orientation.VERTICAL) ? _verticalWallFile
                : _horizontalWallFile;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, 0, null);
    }

    private Dimension getDimensionByOrientation() {
        return (_orientation == Orientation.VERTICAL) ? new Dimension(5, 120) : new Dimension(125, 5);
    }
}
