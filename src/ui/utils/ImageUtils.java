package ui.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    public static BufferedImage resizeImage(BufferedImage img, Integer width, Integer height) {
        Image tmpImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(tmpImg, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }
    public static final Color BACKGROUND_COLOR = Color.decode("#9B9B9B");

    public static final String IMAGE_PATH = "resources/";

    public static final String LITTLE_ROBOT_IMAGE = "LittleRobot.png";
    public static final String BIG_ROBOT_IMAGE = "BigRobot.png";
    public static final String BROKEN_ROBOT_IMAGE = "BrokenRobot.png";
    public static final String SWAMP_IMAGE = "Swamp.png";
    public static final String ICE_IMAGE = "Ice.png";
    public static final String SAND_IMAGE = "Sand.png";
    public static final String VERTICAL_WALL_IMAGE = "VerticalWall.png";
    public static final String HORIZONTAL_WALL_IMAGE = "HorizontalWall.png";

}
