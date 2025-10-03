package ru.mirea.zakirovakr.weathertracker.domain.usecases;

import ru.mirea.zakirovakr.weathertracker.domain.repository.WeatherRepository;

public class SaveFavoriteCityUseCase {
    private final WeatherRepository weatherRepository;

    public SaveFavoriteCityUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public boolean execute(String city) {
        return weatherRepository.saveFavoriteCity(city);
    }
}