package ui.field.cell.cellItems.landscape;

import ui.field.cell.cellItems.CellItemWidget;
import ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class LandscapeSegmentWidget extends CellItemWidget {
    protected Color _color;

    protected static File imageFile = null;

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = ImageUtils.resizeImage(image, 90, 90);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(120, 120);
    }
}
