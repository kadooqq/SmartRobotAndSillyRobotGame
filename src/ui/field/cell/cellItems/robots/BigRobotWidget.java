package ui.field.cell.cellItems.robots;

import model.field.fieldObjects.robot.Robot;
import ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BigRobotWidget extends RobotWidget {
    public BigRobotWidget(Robot robot) {
        super(robot);
    }

    @Override
    protected File getImageFile() {
        return new File(ImageUtils.IMAGE_PATH + ImageUtils.BIG_ROBOT_IMAGE);
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = ImageUtils.resizeImage(image, 100, 96);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(100, 120);
    }
}
