package model.field.weather;

import model.events.LittleRobotEndStepEvent;
import model.events.WeatherChangeEvent;
import model.field.Field;
import model.listeners.LittleRobotEndStepListener;
import model.listeners.WeatherChangeListener;

import java.util.ArrayList;
import java.util.List;

public class SeasonController implements LittleRobotEndStepListener {
    // ---------------------------------------------- Сезон ------------------------------------------------------------
    private static final int SEASON_DURATION = 3;
    private static final int NUM_OF_SEASONS = 4;
    private static final int SEASONS_CYCLE_DURATION = SEASON_DURATION * NUM_OF_SEASONS;

    public enum Season {
        SUMMER(0),
        AUTUMN(SEASON_DURATION),
        WINTER(SEASON_DURATION * 2),
        SPRING(SEASON_DURATION * 3);

        private int _value;
        Season(int value) {
            _value = value;
        }

        public int getSeasonValue() {
            return _value;
        }
    }

    private Season getSeasonByValue(int value) {
        if (0 <= value % SEASONS_CYCLE_DURATION && value % SEASONS_CYCLE_DURATION < SEASON_DURATION)
            return Season.SUMMER;
        else if (SEASON_DURATION <= value % SEASONS_CYCLE_DURATION && value % SEASONS_CYCLE_DURATION < SEASON_DURATION * 2)
            return Season.AUTUMN;
        else if (SEASON_DURATION * 2 <= value % SEASONS_CYCLE_DURATION && value % SEASONS_CYCLE_DURATION < SEASON_DURATION * 3)
            return Season.WINTER;
        else if (SEASON_DURATION * 3 <= value % SEASONS_CYCLE_DURATION)
            return Season.SPRING;

        return Season.SUMMER;
    }

    // ----------------------------------------------- Контроллер сезонов ----------------------------------------------
    private final Field _field;

    public SeasonController(Field field, Season startSeason) {
        _field = field;
        _weatherController = new WeatherController(_field);
        _currentSeason = startSeason;
        _currentTime = startSeason.getSeasonValue();
        changeSeason(_currentSeason);
    }

    private final WeatherController _weatherController;
    private int _currentTime;
    private Season _currentSeason;

    public void changeSeason(Season season) {
        _currentSeason = season;

        switch (season) {
            case SUMMER -> {
                _weatherController.changeWeather(WeatherController.Weather.DRY);
                _weatherController.changeWeather(WeatherController.Weather.HEAT);
            }
            case AUTUMN, SPRING -> {
                _weatherController.changeWeather(WeatherController.Weather.HEAT);
                _weatherController.changeWeather(WeatherController.Weather.RAINY);
            }
            case WINTER -> {
                _weatherController.changeWeather(WeatherController.Weather.DRY);
                _weatherController.changeWeather(WeatherController.Weather.ICILY);
            }
        }
    }

    @Override
    public void littleRobotEndedStep(LittleRobotEndStepEvent e) {
        _currentTime = ((_currentTime + 1) % SEASONS_CYCLE_DURATION);
        Season newSeason = getSeasonByValue(_currentTime);
        if (newSeason != _currentSeason) {
            changeSeason(newSeason);
            fireWeatherChanged();
        }
    }

    // ---------------------------------------- Добавление и удаление слушателей WeatherController ---------------------
    private final List<WeatherChangeListener> _weatherChangeListeners = new ArrayList<>();

    public void addWeatherChangeListener(WeatherChangeListener l) {
        if (l == null) return;
        _weatherChangeListeners.add(l);
    }

    public void addWeatherChangeListener(int index, WeatherChangeListener l) {
        if (l == null) return;
        _weatherChangeListeners.add(index, l);
    }

    public void removeWeatherChangeListener(WeatherChangeListener l) {
        _weatherChangeListeners.remove(l);
    }

    private void fireWeatherChanged() {
        for (WeatherChangeListener l : _weatherChangeListeners) {
            WeatherChangeEvent e = new WeatherChangeEvent(this);
            e.setChangedLandscape(_weatherController.takeChangedLandscapeCellsInfo());
            l.weatherChanged(e);
        }
    }
}
