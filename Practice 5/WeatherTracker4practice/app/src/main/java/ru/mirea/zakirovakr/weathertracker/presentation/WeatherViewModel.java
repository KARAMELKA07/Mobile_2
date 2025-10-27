package ru.mirea.zakirovakr.weathertracker.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.models.WeatherItem;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;
import ru.mirea.zakirovakr.domain.domain.usecases.GetWeatherByCityUseCase;
import ru.mirea.zakirovakr.domain.domain.usecases.LogoutUseCase;
import ru.mirea.zakirovakr.domain.domain.usecases.RecognizeWeatherFromPhotoUseCase;
import ru.mirea.zakirovakr.weathertracker.presentation.mocks.FakeDbDataSourceWeather;
import ru.mirea.zakirovakr.weathertracker.presentation.mocks.FakeNetworkDataSourceWeather;

public class WeatherViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final WeatherRepository weatherRepository;

    private final MutableLiveData<String> statusText  = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> isLoading  = new MutableLiveData<>(false);

    private final MutableLiveData<List<WeatherItem>> weatherList = new MutableLiveData<>();
    private final MutableLiveData<WeatherItem> latestItem = new MutableLiveData<>();

    private final MediatorLiveData<String> mergedSource = new MediatorLiveData<>();

    public WeatherViewModel(UserRepository userRepository,
                            WeatherRepository weatherRepository) {
        this.userRepository = userRepository;
        this.weatherRepository = weatherRepository;

        weatherList.setValue(weatherRepository.loadStubWeatherList());

        FakeDbDataSourceWeather db = new FakeDbDataSourceWeather(userRepository, weatherRepository);
        LiveData<String> dbSource = db.loadLocalWeatherLine();
        FakeNetworkDataSourceWeather net = new FakeNetworkDataSourceWeather();
        LiveData<String> netSource = net.fetchWeatherLine();

        mergedSource.addSource(dbSource, s -> mergedSource.setValue("DB: " + s));
        mergedSource.addSource(netSource, s -> mergedSource.setValue("NET: " + s));
    }

    public LiveData<String> getStatusText()    { return statusText;  }
    public LiveData<String> getMergedSource()  { return mergedSource;}
    public LiveData<Boolean> getIsLoading()    { return isLoading;   }
    public LiveData<List<WeatherItem>> getWeatherList() { return weatherList; }
    public LiveData<WeatherItem> getLatestItem() { return latestItem; }

    public void getWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            statusText.setValue("Введите название города");
            return;
        }
        statusText.setValue("Загрузка погоды для " + city + "...");
        isLoading.setValue(true);

        weatherRepository.fetchWeatherRemote(city, new WeatherRepository.WeatherRemoteCallback() {
            @Override
            public void onSuccess(WeatherItem item) {
                latestItem.postValue(item);
                statusText.postValue("");
                isLoading.postValue(false);
            }

            @Override
            public void onError(String message) {
                Weather w = new GetWeatherByCityUseCase(weatherRepository).execute(city);
                WeatherItem fallback = new WeatherItem(
                        w.getCity(),
                        w.getTemperature() + "°C",
                        "Локальные данные",
                        null,
                        null,
                        "",
                        ""
                );
                latestItem.postValue(fallback);
                statusText.postValue(message);
                isLoading.postValue(false);
            }
        });
    }

    public void saveCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            statusText.setValue("Введите город для сохранения");
            return;
        }
        userRepository.saveFavoriteCity(city);
        statusText.setValue("Город " + city + " сохранён в избранное");
    }

    public void recognizeWeather() {
        String result = new RecognizeWeatherFromPhotoUseCase(weatherRepository).execute();
        statusText.setValue(String.format("Анализ фото: %s", result));
    }

    public void logout() {
        new LogoutUseCase(userRepository).execute();
        statusText.setValue("Вы вышли из системы");
    }
}
