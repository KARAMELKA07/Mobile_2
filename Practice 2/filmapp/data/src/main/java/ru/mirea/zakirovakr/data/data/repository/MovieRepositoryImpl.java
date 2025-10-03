package ru.mirea.zakirovakr.data.data.repository;

import java.time.LocalDate;

import ru.mirea.zakirovakr.data.data.storage.MovieStorage;
import ru.mirea.zakirovakr.data.data.storage.models.Movie;
import ru.mirea.zakirovakr.domain.models.MovieD;
import ru.mirea.zakirovakr.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {
    private final MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(MovieD movie) {
        Movie storageMovie = mapToStorage(movie);
        return movieStorage.save(storageMovie);
    }

    @Override
    public MovieD getMovie() {
        Movie movie = movieStorage.get();
        return mapToDomain(movie);
    }

    private Movie mapToStorage(MovieD movie) {
        String name = movie.getName();
        return new Movie(2, name, LocalDate.now().toString());
    }

    private MovieD mapToDomain(Movie movie) {
        return new MovieD(movie.getId(), movie.getName());
    }
}
