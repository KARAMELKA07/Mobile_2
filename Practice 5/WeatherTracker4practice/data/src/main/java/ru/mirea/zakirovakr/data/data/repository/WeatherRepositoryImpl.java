package ru.mirea.zakirovakr.data.data.repository;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;
import ru.mirea.zakirovakr.data.data.remote.OpenMeteoService;
import ru.mirea.zakirovakr.data.data.remote.RetrofitProviders;
import ru.mirea.zakirovakr.data.data.remote.WeatherCodeMapper;
import ru.mirea.zakirovakr.data.data.remote.ForecastResponse;
import ru.mirea.zakirovakr.data.data.remote.GeoResponse;
import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.models.WeatherItem;
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

    @Override
    public List<WeatherItem> loadStubWeatherList() {
        List<WeatherItem> items = new ArrayList<>();
        items.add(new WeatherItem("Москва", "+18°C", "Облачно",  "icon_cloud", null,  "Влажность: 61%", "Ветер: 10 км/ч"));
        items.add(new WeatherItem("Евпатория", "+15°C", "Дождь", "icon_rain",  null,  "Влажность: 80%", "Ветер: 12 км/ч"));
        items.add(new WeatherItem("Казань", "+22°C", "Солнечно","icon_sun",   null,  "Влажность: 45%", "Ветер:  6 км/ч"));
        return items;
    }

    @Override
    public void fetchWeatherRemote(String city, WeatherRemoteCallback callback) {
        new Thread(() -> {
            try {
                OpenMeteoService geo = RetrofitProviders.geoService();
                Response<GeoResponse> geoResp = geo.geocode(city, 1, "ru").execute();
                if (!geoResp.isSuccessful() || geoResp.body() == null
                        || geoResp.body().results == null || geoResp.body().results.isEmpty()) {
                    callback.onError("Город не найден");
                    return;
                }
                GeoResponse.Result r = geoResp.body().results.get(0);

                OpenMeteoService meteo = RetrofitProviders.meteoService();
                String current = "temperature_2m,weather_code,relative_humidity_2m,wind_speed_10m";
                Response<ForecastResponse> meteoResp = meteo.current(
                        r.latitude, r.longitude, current, "auto", "ms", "celsius"
                ).execute();

                if (!meteoResp.isSuccessful() || meteoResp.body() == null || meteoResp.body().current == null) {
                    callback.onError("Ошибка ответа погодного сервиса");
                    return;
                }

                ForecastResponse.Current c = meteoResp.body().current;

                String cityName  = (r.name != null ? r.name : city);
                String condition = WeatherCodeMapper.toRu(c.weatherCode);
                String temp      = Math.round(c.temperature2m) + "°C";
                String humidity  = "Влажность: " + c.relativeHumidity2m + "%";
                String wind      = "Ветер: " + Math.round(c.windSpeed10m) + " м/с";

                String iconName = mapConditionToIconName(condition);

                String iconUrl = null;

                WeatherItem item = new WeatherItem(
                        cityName,
                        temp,
                        condition,
                        iconName,
                        iconUrl,
                        humidity,
                        wind
                );

                callback.onSuccess(item);
            } catch (Exception e) {
                callback.onError("Ошибка сети: " + e.getMessage());
            }
        }).start();
    }

    private String mapConditionToIconName(@Nullable String conditionRaw) {
        if (conditionRaw == null) return "ic";
        String c = conditionRaw.toLowerCase(Locale.getDefault());
        if (c.contains("солне") || c.contains("ясно"))   return "icon_sun";
        if (c.contains("облач") || c.contains("пасмур")) return "icon_cloud";
        if (c.contains("дожд")  || c.contains("ливн"))   return "icon_rain";
        if (c.contains("туман") || c.contains("дымк"))   return "icon_fog";
        if (c.contains("ветер") || c.contains("ветрен")) return "icon_wind";
        if (c.contains("снег")  || c.contains("снеж"))   return "icon_snow";
        return "ic";
    }
}
