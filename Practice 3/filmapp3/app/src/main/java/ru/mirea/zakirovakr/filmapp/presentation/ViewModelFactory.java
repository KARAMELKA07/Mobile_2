package ru.mirea.zakirovakr.filmapp.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.zakirovakr.data.data.repository.MovieRepositoryImpl;
import ru.mirea.zakirovakr.data.data.storage.MovieStorage;
import ru.mirea.zakirovakr.data.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.zakirovakr.domain.repository.MovieRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context appContext;

    public ViewModelFactory(Context context) {
        this.appContext = context.getApplicationContext();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MovieStorage storage = new SharedPrefMovieStorage(appContext);
        MovieRepository repo = new MovieRepositoryImpl(storage);
        return (T) new MainViewModel(repo);
    }
}
