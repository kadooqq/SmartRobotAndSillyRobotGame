package ui.field.cell.cellItems.landscape;

import ui.utils.ImageUtils;

import java.io.File;

public class SwampSegmentWidget extends LandscapeSegmentWidget {
    private static File imageFile = null;

    @Override
    protected File getImageFile() {
        if (imageFile == null) {
            imageFile = new File(ImageUtils.IMAGE_PATH + ImageUtils.SWAMP_IMAGE);
        }
        return imageFile;
    }
}
