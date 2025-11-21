package ru.mirea.zakirovakr.weathertracker.presentation.mocks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/** Имитация сетевого источника для MediatorLiveData */
public class FakeNetworkDataSourceWeather {

    public LiveData<String> fetchWeatherLine() {
        MutableLiveData<String> live = new MutableLiveData<>();
        new Thread(() -> {
            try {
                Thread.sleep(1200);
            } catch (InterruptedException ignored) {}
            live.postValue("☁️ Cloudy, +18°C (network)");
        }).start();
        return live;
    }
}

