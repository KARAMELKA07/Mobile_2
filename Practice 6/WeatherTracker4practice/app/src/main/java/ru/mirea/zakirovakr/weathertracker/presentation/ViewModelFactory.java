package ru.mirea.zakirovakr.weathertracker.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.zakirovakr.data.data.repository.UserRepositoryImpl;
import ru.mirea.zakirovakr.data.data.repository.WeatherRepositoryImpl;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context appContext;

    public ViewModelFactory(Context context) {
        this.appContext = context.getApplicationContext();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        UserRepository userRepo = new UserRepositoryImpl(appContext);
        WeatherRepository weatherRepo = new WeatherRepositoryImpl();
        return (T) new WeatherViewModel(userRepo, weatherRepo);
    }
}
