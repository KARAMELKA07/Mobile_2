package ru.mirea.zakirovakr.weathertracker.presentation.mocks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;
import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.usecases.GetWeatherByCityUseCase;

/** Имитация локального источника: берём сохранённый город и получаем температуру локальным use-case */
public class FakeDbDataSourceWeather {

    private final UserRepository userRepository;
    private final WeatherRepository weatherRepository;

    public FakeDbDataSourceWeather(UserRepository userRepository,
                                   WeatherRepository weatherRepository) {
        this.userRepository = userRepository;
        this.weatherRepository = weatherRepository;
    }

    public LiveData<String> loadLocalWeatherLine() {
        MutableLiveData<String> live = new MutableLiveData<>();
        new Thread(() -> {
            String city = userRepository.getUserPreferences("favorite_city");
            if (city == null || city.isEmpty()) city = "Unknown";
            Weather w = new GetWeatherByCityUseCase(weatherRepository).execute(city);
            live.postValue("Local " + city + ": " + w.getTemperature() + "°C");
        }).start();
        return live;
    }
}

