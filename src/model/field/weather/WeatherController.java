package model.field.weather;

import model.field.Cell;
import model.field.Direction;
import model.field.ExitCell;
import model.field.Field;
import model.field.fieldObjects.landscape.IceSegment;
import model.field.fieldObjects.landscape.LandscapeSegment;
import model.field.fieldObjects.landscape.SwampSegment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WeatherController {
    public enum Weather {
        RAINY,
        HEAT,
        DRY,
        ICILY
    }

    private final Field _field;

    public WeatherController(Field field) {
        _field = field;
    }

    private final HashMap<Cell, LandscapeSegment> _previousStateOfModifiedLandscape = new HashMap<>();

    private final HashMap<Cell, LandscapeSegment> _stateBuffer = new HashMap<>();

    private final ArrayList<Cell> _deltaBuffer = new ArrayList<>();

    private final HashSet<Cell> _delta = new HashSet<>();

    public HashSet<Cell> getChangedLandscapeCells() {
        return _delta;
    }

    HashSet<Cell> takeChangedLandscapeCellsInfo() {
        var temp = (HashSet<Cell>) _delta.clone();
        _delta.clear();
        return temp;
    }

    public void changeWeather(Weather weather) {
        switch (weather) {
            case DRY -> makeLandscapeDry();
            case RAINY -> makeLandscapeRainy();
            case ICILY -> makeLandscapeIcily();
            case HEAT -> makeLandscapeHeat();
        }
        _previousStateOfModifiedLandscape.clear();
        _previousStateOfModifiedLandscape.putAll(_stateBuffer);
        _stateBuffer.clear();
        _deltaBuffer.clear();
    }

    private void makeLandscapeRainy() {
        for (var cell : _field.getCells().values()) {
            if (!_deltaBuffer.contains(cell) && cell.getLandscapeSegment() instanceof SwampSegment) {
                makePuddle(cell);
            }
        }
    }

    private void makePuddle(Cell cell) {
        for (var neighbour : cell.getNeighbourCells().entrySet()) {
            setSwampSegment(neighbour.getValue());
            if (neighbour.getKey() == Direction.NORTH || neighbour.getKey() == Direction.SOUTH) {
                setSwampSegment(neighbour.getValue().getNeighbourCell(Direction.WEST));
                setSwampSegment(neighbour.getValue().getNeighbourCell(Direction.EAST));
            }
        }
    }

    private void setSwampSegment(Cell cell) {
        if (cell == null) return;
        if (!(cell.getLandscapeSegment() instanceof SwampSegment) && !(cell instanceof ExitCell)) {
            _stateBuffer.put(cell, cell.getLandscapeSegment());
            _delta.add(cell);
            _deltaBuffer.add(cell);
            cell.setLandscapeSegment(new SwampSegment());
        }
    }

    private void makeLandscapeDry() {
        if (_previousStateOfModifiedLandscape.isEmpty()) return;
        for (var landscape : _field.getCells().values()) {
            if (landscape.getLandscapeSegment() instanceof SwampSegment
                    && _previousStateOfModifiedLandscape.containsKey(landscape) && !(_previousStateOfModifiedLandscape.get(landscape) instanceof SwampSegment)) {
                LandscapeSegment curLandscape = landscape.getLandscapeSegment();
                landscape.setLandscapeSegment(_previousStateOfModifiedLandscape.get(landscape));
                _stateBuffer.put(landscape, curLandscape);
                _delta.add(landscape);
                _deltaBuffer.add(landscape);
            }
        }
    }

    private void makeLandscapeIcily() {
        for (var landscape : _field.getCells().values()) {
            if (landscape.getLandscapeSegment() instanceof SwampSegment) {
                LandscapeSegment buf = landscape.getLandscapeSegment();
                landscape.setLandscapeSegment(_previousStateOfModifiedLandscape.get(landscape) instanceof IceSegment ?
                        _previousStateOfModifiedLandscape.get(landscape) : new IceSegment());

                _stateBuffer.put(landscape, buf);
                _delta.add(landscape);
                _deltaBuffer.add(landscape);
            }
        }
    }

    private void makeLandscapeHeat() {
        for (var landscape : _field.getCells().values()) {
            if (landscape.getLandscapeSegment() instanceof IceSegment) {
                LandscapeSegment buf = landscape.getLandscapeSegment();
                landscape.setLandscapeSegment(_previousStateOfModifiedLandscape.get(landscape) instanceof SwampSegment ?
                        _previousStateOfModifiedLandscape.get(landscape) : new SwampSegment());

                _stateBuffer.put(landscape, buf);
                _delta.add(landscape);
                _deltaBuffer.add(landscape);
            }
        }
    }
}
