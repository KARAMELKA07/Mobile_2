package ru.mirea.zakirovakr.weathertracker.domain.usecases;

import ru.mirea.zakirovakr.weathertracker.domain.models.Weather;
import ru.mirea.zakirovakr.weathertracker.domain.repository.WeatherRepository;

public class GetWeatherByCityUseCase {
    private final WeatherRepository weatherRepository;

    public GetWeatherByCityUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather execute(String city) {
        return weatherRepository.getWeatherByCity(city);
    }
}