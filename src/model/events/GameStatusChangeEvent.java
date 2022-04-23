package model.events;

import model.game.GameStatus;

import java.util.EventObject;

public class GameStatusChangeEvent extends EventObject {
    public GameStatusChangeEvent(Object source) {
        super(source);
    }

    private GameStatus _gameStatus = null;

    public void setGameStatus(GameStatus gameStatus) {
        _gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return _gameStatus;
    }
}
