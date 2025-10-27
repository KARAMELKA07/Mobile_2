package ru.mirea.zakirovakr.domain.domain.repository;

import java.util.List;

import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.models.WeatherItem;

public interface WeatherRepository {
    Weather getWeatherByCity(String city);
    boolean saveFavoriteCity(String city);
    String recognizeWeatherFromPhoto();

    List<WeatherItem> loadStubWeatherList();

    interface WeatherRemoteCallback {
        void onSuccess(WeatherItem item);
        void onError(String message);
    }

    void fetchWeatherRemote(String city, WeatherRemoteCallback callback);
}
