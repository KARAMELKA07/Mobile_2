package ru.mirea.zakirovakr.domain.repository;

import ru.mirea.zakirovakr.domain.models.MovieD;

public interface MovieRepository {
    boolean saveMovie(MovieD movie);
    MovieD getMovie();
}
