package ru.mirea.zakirovakr.movieproject.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.zakirovakr.movieproject.domain.models.Movie;
import ru.mirea.zakirovakr.movieproject.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {
    private static final String PREFS_NAME = "favorite_movie_prefs";
    private static final String KEY_MOVIE_ID = "favorite_movie_id";
    private static final String KEY_MOVIE_NAME = "favorite_movie_name";

    private SharedPreferences sharedPreferences;

    public MovieRepositoryImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean saveMovie(Movie movie) {
        if (movie.getName().isEmpty()) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_MOVIE_ID, movie.getId());
        editor.putString(KEY_MOVIE_NAME, movie.getName());
        return editor.commit();
    }

    @Override
    public Movie getMovie() {
        int id = sharedPreferences.getInt(KEY_MOVIE_ID, -1);
        String name = sharedPreferences.getString(KEY_MOVIE_NAME, null);
        if (id == -1 || name == null) {
            return new Movie(1, "Doctor Strange");
        }
        return new Movie(id, name);
    }
}
