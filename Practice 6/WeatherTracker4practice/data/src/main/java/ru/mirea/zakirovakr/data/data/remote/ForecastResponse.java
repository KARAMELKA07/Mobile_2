package ru.mirea.zakirovakr.data.data.remote;

import com.google.gson.annotations.SerializedName;

public class ForecastResponse {

    @SerializedName("current")
    public Current current;

    public static class Current {

        // Температура воздуха на высоте 2 м
        @SerializedName("temperature_2m")
        public float temperature2m;

        // Относительная влажность воздуха
        @SerializedName("relative_humidity_2m")
        public int relativeHumidity2m;

        // Скорость ветра на высоте 10 м
        @SerializedName("wind_speed_10m")
        public float windSpeed10m;

        // Код погодного состояния
        @SerializedName("weather_code")
        public int weatherCode;

        // Ощущаемая температура (apparent_temperature)
        @SerializedName("apparent_temperature")
        public float apparentTemperature;

        // Давление на уровне моря (pressure_msl)
        @SerializedName("pressure_msl")
        public float pressureMsl;

        // Облачность, % (cloud_cover)
        @SerializedName("cloud_cover")
        public int cloudCover;
    }
}
