package ru.mirea.zakirovakr.weathertracker.domain.usecases;

import ru.mirea.zakirovakr.weathertracker.domain.repository.WeatherRepository;

public class RecognizeWeatherFromPhotoUseCase {
    private final WeatherRepository weatherRepository;

    public RecognizeWeatherFromPhotoUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public String execute() {
        return weatherRepository.recognizeWeatherFromPhoto();
    }
}