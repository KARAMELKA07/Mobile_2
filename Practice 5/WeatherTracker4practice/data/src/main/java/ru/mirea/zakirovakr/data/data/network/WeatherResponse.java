package ru.mirea.zakirovakr.data.data.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

    @SerializedName("name")
    public String name;

    @SerializedName("weather")
    public List<WeatherDto> weather;

    @SerializedName("main")
    public MainDto main;

    @SerializedName("wind")
    public WindDto wind;

    public static class WeatherDto {
        @SerializedName("description")
        public String description;
        @SerializedName("icon")
        public String icon;
    }

    public static class MainDto {
        @SerializedName("temp")
        public float temp;
        @SerializedName("humidity")
        public int humidity;
    }

    public static class WindDto {
        @SerializedName("speed")
        public float speed;
    }
}
