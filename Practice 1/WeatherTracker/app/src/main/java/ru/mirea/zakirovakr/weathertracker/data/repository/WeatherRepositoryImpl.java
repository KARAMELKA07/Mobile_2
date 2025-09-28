package ru.mirea.zakirovakr.weathertracker.data.repository;

import ru.mirea.zakirovakr.weathertracker.domain.models.Weather;
import ru.mirea.zakirovakr.weathertracker.domain.repository.WeatherRepository;

public class WeatherRepositoryImpl implements WeatherRepository {
    @Override
    public Weather getWeatherByCity(String city) {
        // Тестовые данные: фиксированная погода для любого города
        return new Weather(city, 25); // Возвращаем 25°C для примера
    }

    @Override
    public boolean saveFavoriteCity(String city) {
        // Тестовый результат: всегда успешно
        return true;
    }

    @Override
    public String recognizeWeatherFromPhoto() {
        // Тестовые данные: фиксированное значение
        return "Sunny"; // Возвращаем "Sunny" как тестовый результат
    }
}