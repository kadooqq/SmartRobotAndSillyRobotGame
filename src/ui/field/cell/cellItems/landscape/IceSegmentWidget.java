package ui.field.cell.cellItems.landscape;

import ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IceSegmentWidget extends LandscapeSegmentWidget {
    private static File imageFile = null;
    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected File getImageFile() {
        if (imageFile == null) {
            imageFile = new File(ImageUtils.IMAGE_PATH + ImageUtils.ICE_IMAGE);
        }
        return imageFile;
    }
}
