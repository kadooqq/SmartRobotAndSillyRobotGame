package ui.field.cell.cellItems.robots;

import model.field.Direction;
import model.field.fieldObjects.robot.LittleRobot;
import model.field.fieldObjects.robot.Robot;
import ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LittleRobotWidget extends RobotWidget {
    public LittleRobotWidget(Robot robot) {
        super(robot);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyController());
    }

    private File _imageFile = new File(ImageUtils.IMAGE_PATH + ImageUtils.LITTLE_ROBOT_IMAGE);

    public void setDestroyedRobotImage() {
        _imageFile = new File(ImageUtils.IMAGE_PATH + ImageUtils.BROKEN_ROBOT_IMAGE);
    }

    @Override
    protected File getImageFile() {
        return _imageFile;
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getImageFile());
            image = ImageUtils.resizeImage(image, 70, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(70, 120);
    }

    private class KeyController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();

            moveAction(keyCode);
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
        }

        private void moveAction(int keyCode){
            Direction direction = directionByKeyCode(keyCode);
            if(direction != null) {
                ((LittleRobot)_robot).makeStep(direction);
            }
        }

        private Direction directionByKeyCode(int keyCode) {
            Direction direction = switch (keyCode) {
                case KeyEvent.VK_W -> Direction.NORTH;
                case KeyEvent.VK_S -> Direction.SOUTH;
                case KeyEvent.VK_A -> Direction.WEST;
                case KeyEvent.VK_D -> Direction.EAST;
                default -> null;
            };
            return direction;
        }
    }
}
