package ru.mirea.zakirovakr.weathertracker.domain.repository;

import ru.mirea.zakirovakr.weathertracker.domain.models.Weather;

public interface WeatherRepository {
    Weather getWeatherByCity(String city);
    boolean saveFavoriteCity(String city);
    String recognizeWeatherFromPhoto();
}