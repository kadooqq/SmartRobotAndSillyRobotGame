package model.events;

import model.field.Cell;

import java.util.EventObject;
import java.util.HashSet;

public class WeatherChangeEvent extends EventObject {
    public WeatherChangeEvent(Object source) {
        super(source);
    }

    private HashSet<Cell> _changedLandscape;

    public void setChangedLandscape(HashSet<Cell> changedLandscape) {
        _changedLandscape = changedLandscape;
    }

    public HashSet<Cell> getChangedLandscape() {
        return _changedLandscape;
    }
}
