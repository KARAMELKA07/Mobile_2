package ru.mirea.zakirovakr.data.data.repository;


import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;

public class WeatherRepositoryImpl implements WeatherRepository {
    @Override
    public Weather getWeatherByCity(String city) {
        return new Weather(city, 25);
    }

    @Override
    public boolean saveFavoriteCity(String city) {
        return true;
    }

    @Override
    public String recognizeWeatherFromPhoto() {
        return "Sunny";
    }
}