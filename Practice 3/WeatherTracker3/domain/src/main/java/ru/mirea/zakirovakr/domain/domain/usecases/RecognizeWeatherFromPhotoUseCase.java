package ru.mirea.zakirovakr.domain.domain.usecases;

import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;

public class RecognizeWeatherFromPhotoUseCase {
    private final WeatherRepository weatherRepository;

    public RecognizeWeatherFromPhotoUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public String execute() {
        return weatherRepository.recognizeWeatherFromPhoto();
    }
}