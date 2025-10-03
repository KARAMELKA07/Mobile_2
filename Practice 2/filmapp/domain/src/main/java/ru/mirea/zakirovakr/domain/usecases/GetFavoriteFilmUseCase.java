package ru.mirea.zakirovakr.domain.usecases;

import ru.mirea.zakirovakr.domain.models.MovieD;
import ru.mirea.zakirovakr.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private final MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieD execute() {
        return movieRepository.getMovie();
    }
}
