package ru.mirea.zakirovakr.data.data.storage;

import ru.mirea.zakirovakr.data.data.storage.models.Movie;

public interface MovieStorage {
    Movie get();
    boolean save(Movie movie);
}
