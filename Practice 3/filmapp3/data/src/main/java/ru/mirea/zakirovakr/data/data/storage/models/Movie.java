package ru.mirea.zakirovakr.data.data.storage.models;

public class Movie {
    private final int id;
    private final String name;
    private final String localDate;

    public Movie(int id, String name, String localDate) {
        this.id = id;
        this.name = name;
        this.localDate = localDate;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLocalDate() {
        return localDate;
    }
}
