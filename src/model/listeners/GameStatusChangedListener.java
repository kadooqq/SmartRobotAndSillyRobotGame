package model.listeners;

import model.events.GameStatusChangeEvent;

public interface GameStatusChangedListener {
    public void gameStatusChanged(GameStatusChangeEvent e);
}
