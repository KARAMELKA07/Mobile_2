package ru.mirea.zakirovakr.recyclerviewapp;

public class Event {
    private final String title;
    private final String description;
    private final String imageName;

    public Event(String title, String description, String imageName) {
        this.title = title;
        this.description = description;
        this.imageName = imageName;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageName() { return imageName; }
}

