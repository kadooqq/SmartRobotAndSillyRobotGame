package model.listeners;

import model.events.RobotTeleportEvent;

public interface ExitCellListener {
    public void robotTeleported(RobotTeleportEvent e);
}
