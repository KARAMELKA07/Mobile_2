package ru.mirea.zakirovakr.domain.domain.usecases;

import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;

public class GetWeatherByCityUseCase {
    private final WeatherRepository weatherRepository;

    public GetWeatherByCityUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather execute(String city) {
        return weatherRepository.getWeatherByCity(city);
    }
}