package ru.mirea.zakirovakr.data.data.network;

import java.util.Random;

public class NetworkApi {
    private static final String[] WEATHER_CONDITIONS = {
            "Солнечно", "Облачно", "Дождливо", "Снежно", "Ветрено", "Туманно"
    };

    private static final String[] TEMPERATURES = {
            "+15°C", "+20°C", "+25°C", "+10°C", "+5°C", "+30°C"
    };

    private static final String[] WIND_SPEEDS = {
            "5 км/ч", "10 км/ч", "15 км/ч", "20 км/ч", "25 км/ч"
    };

    private final Random random = new Random();

    public String fetchWeatherData(String city) {
        // Имитация сетевой задержки 2–3 секунды
        try {
            Thread.sleep(2000 + random.nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String condition   = WEATHER_CONDITIONS[random.nextInt(WEATHER_CONDITIONS.length)];
        String temperature = TEMPERATURES[random.nextInt(TEMPERATURES.length)];
        String wind        = WIND_SPEEDS[random.nextInt(WIND_SPEEDS.length)];
        int humidity       = random.nextInt(50) + 30; // 30–80%

        // Без эмодзи
        return String.format(
                "Город: %s\n%s\nТемпература: %s\nВлажность: %d%%\nВетер: %s",
                city, condition, temperature, humidity, wind
        );
    }

    public String fetchWeatherForecast(String city) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        StringBuilder forecast = new StringBuilder();
        forecast.append("Прогноз погоды для ").append(city).append(":\n\n");

        String[] days = {"Сегодня", "Завтра", "Послезавтра"};
        for (String day : days) {
            String condition = WEATHER_CONDITIONS[random.nextInt(WEATHER_CONDITIONS.length)];
            String temp      = TEMPERATURES[random.nextInt(TEMPERATURES.length)];
            forecast.append(day).append(": ").append(condition).append(", ").append(temp).append("\n");
        }

        return forecast.toString();
    }

    public boolean isNetworkAvailable() {
        return true;
    }
}
