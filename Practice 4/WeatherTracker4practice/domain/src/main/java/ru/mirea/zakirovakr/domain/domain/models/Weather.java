package ru.mirea.zakirovakr.domain.domain.models;

public class Weather {
    private String city;
    private int temperature;

    public Weather(String city, int temperature) {
        this.city = city;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public int getTemperature() {
        return temperature;
    }
}