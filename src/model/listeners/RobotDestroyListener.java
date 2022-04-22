package model.listeners;

import model.events.RobotDestroyEvent;

public interface RobotDestroyListener {
    public void robotDestroyed(RobotDestroyEvent e);
}
