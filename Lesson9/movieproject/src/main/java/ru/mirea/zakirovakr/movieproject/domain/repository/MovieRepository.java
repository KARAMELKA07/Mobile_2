package ru.mirea.zakirovakr.movieproject.domain.repository;

import ru.mirea.zakirovakr.movieproject.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}
