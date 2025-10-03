package ru.mirea.zakirovakr.domain.models;

public class MovieD {
    private final int id;
    private final String name;

    public MovieD(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}