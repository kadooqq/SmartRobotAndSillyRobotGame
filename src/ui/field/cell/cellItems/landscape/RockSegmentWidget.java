package ui.field.cell.cellItems.landscape;

import ui.utils.ImageUtils;

import java.io.File;

public class RockSegmentWidget extends LandscapeSegmentWidget {
    private static File imageFile = null;

    @Override
    protected File getImageFile() {
        if (imageFile == null) {
            imageFile = new File(ImageUtils.IMAGE_PATH + ImageUtils.ROCK_IMAGE);
        }
        return imageFile;
    }
}
