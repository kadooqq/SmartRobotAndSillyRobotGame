package model.listeners;

import model.events.WeatherChangeEvent;

public interface WeatherChangeListener {
    void weatherChanged(WeatherChangeEvent e);
}
