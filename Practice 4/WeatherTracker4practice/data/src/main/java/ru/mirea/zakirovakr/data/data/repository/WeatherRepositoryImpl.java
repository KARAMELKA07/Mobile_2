package ru.mirea.zakirovakr.data.data.repository;

import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.models.WeatherItem;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public List<WeatherItem> loadStubWeatherList() {
        List<WeatherItem> items = new ArrayList<>();

        items.add(new WeatherItem("Москва",            "+18°C", "Облачно",  "icon_cloud", "Влажность: 61%", "Ветер: 10 км/ч"));
        items.add(new WeatherItem("Евпатория",         "+15°C", "Дождь",     "icon_rain",  "Влажность: 80%", "Ветер: 12 км/ч"));
        items.add(new WeatherItem("Казань",            "+22°C", "Солнечно",  "icon_sun",   "Влажность: 45%", "Ветер:  6 км/ч"));
        items.add(new WeatherItem("Екатеринбург",      "+12°C", "Туман",     "icon_fog",   "Влажность: 70%", "Ветер:  9 км/ч"));
        items.add(new WeatherItem("Новосибирск",       "+9°C",  "Ветрено",   "icon_wind",  "Влажность: 55%", "Ветер: 22 км/ч"));
        items.add(new WeatherItem("Сочи",              "+24°C", "Солнечно",  "icon_sun",   "Влажность: 50%", "Ветер:  8 км/ч"));
        items.add(new WeatherItem("Нижний Новгород",   "+16°C", "Облачно",   "icon_cloud", "Влажность: 60%", "Ветер: 11 км/ч"));

        return items;
    }
}