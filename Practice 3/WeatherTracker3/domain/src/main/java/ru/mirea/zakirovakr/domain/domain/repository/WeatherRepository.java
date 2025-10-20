package ru.mirea.zakirovakr.domain.domain.repository;

import ru.mirea.zakirovakr.domain.domain.models.Weather;

public interface WeatherRepository {
    Weather getWeatherByCity(String city);
    boolean saveFavoriteCity(String city);
    String recognizeWeatherFromPhoto();
}