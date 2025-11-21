package ru.mirea.zakirovakr.domain.domain.models;

import java.io.Serializable;

public class WeatherItem implements Serializable {
    private final String city;
    private final String temperature;
    private final String condition;
    private final String iconName;
    private final String iconUrl;
    private final String humidity;
    private final String wind;

    // Детальные поля
    private final String feelsLike;
    private final String pressure;
    private final String clouds;

    public WeatherItem(String city,
                       String temperature,
                       String condition,
                       String iconName,
                       String iconUrl,
                       String humidity,
                       String wind,
                       String feelsLike,
                       String pressure,
                       String clouds) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
        this.iconName = iconName;
        this.iconUrl = iconUrl;
        this.humidity = humidity;
        this.wind = wind;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.clouds = clouds;
    }

    public String getCity()       { return city; }
    public String getTemperature(){ return temperature; }
    public String getCondition()  { return condition; }
    public String getIconName()   { return iconName; }
    public String getIconUrl()    { return iconUrl; }
    public String getHumidity()   { return humidity; }
    public String getWind()       { return wind; }
    public String getFeelsLike()  { return feelsLike; }
    public String getPressure()   { return pressure; }
    public String getClouds()     { return clouds; }
}
