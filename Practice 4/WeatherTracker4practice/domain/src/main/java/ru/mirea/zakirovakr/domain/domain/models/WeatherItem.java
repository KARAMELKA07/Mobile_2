package ru.mirea.zakirovakr.domain.domain.models;

public class WeatherItem {
    private final String city;
    private final String temperature;
    private final String condition;
    private final String iconName;
    private final String humidity;
    private final String wind;

    public WeatherItem(String city, String temperature, String condition,
                       String iconName, String humidity, String wind) {
        this.city = city;
        this.temperature = temperature;
        this.condition = condition;
        this.iconName = iconName;
        this.humidity = humidity;
        this.wind = wind;
    }

    public String getCity() { return city; }
    public String getTemperature() { return temperature; }
    public String getCondition() { return condition; }
    public String getIconName() { return iconName; }
    public String getHumidity() { return humidity; }
    public String getWind() { return wind; }
}
