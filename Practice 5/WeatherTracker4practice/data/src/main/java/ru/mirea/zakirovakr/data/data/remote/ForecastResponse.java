package ru.mirea.zakirovakr.data.data.remote;

import com.google.gson.annotations.SerializedName;

public class ForecastResponse {

    @SerializedName("current")
    public Current current;

    public static class Current {
        @SerializedName("temperature_2m")        public float temperature2m;
        @SerializedName("relative_humidity_2m")  public int relativeHumidity2m;
        @SerializedName("wind_speed_10m")        public float windSpeed10m;
        @SerializedName("weather_code")          public int weatherCode;
    }
}

