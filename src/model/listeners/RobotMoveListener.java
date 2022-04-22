package model.listeners;

import model.events.RobotMoveEvent;

public interface RobotMoveListener {
    public void robotMadeMove(RobotMoveEvent e);
}
