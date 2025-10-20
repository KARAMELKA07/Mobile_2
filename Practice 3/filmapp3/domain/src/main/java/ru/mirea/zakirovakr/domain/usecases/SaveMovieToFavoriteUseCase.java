package ru.mirea.zakirovakr.domain.usecases;

import ru.mirea.zakirovakr.domain.models.MovieD;
import ru.mirea.zakirovakr.domain.repository.MovieRepository;

public class SaveMovieToFavoriteUseCase {
    private final MovieRepository movieRepository;

    public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean execute(MovieD movie) {
        return movieRepository.saveMovie(movie);
    }
}
