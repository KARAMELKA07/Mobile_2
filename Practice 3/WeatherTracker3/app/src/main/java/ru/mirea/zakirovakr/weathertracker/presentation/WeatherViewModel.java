package ru.mirea.zakirovakr.weathertracker.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository.WeatherCallback;
import ru.mirea.zakirovakr.domain.domain.usecases.GetWeatherByCityUseCase;
import ru.mirea.zakirovakr.domain.domain.usecases.LogoutUseCase;
import ru.mirea.zakirovakr.domain.domain.usecases.RecognizeWeatherFromPhotoUseCase;
import ru.mirea.zakirovakr.weathertracker.presentation.mocks.FakeDbDataSourceWeather;
import ru.mirea.zakirovakr.weathertracker.presentation.mocks.FakeNetworkDataSourceWeather;

public class WeatherViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final WeatherRepository weatherRepository;

    private final MutableLiveData<String> weatherText = new MutableLiveData<>("");
    private final MutableLiveData<String> statusText  = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> isLoading  = new MutableLiveData<>(false);

    // Пример объединения источников: MediatorLiveData
    private final MediatorLiveData<String> mergedSource = new MediatorLiveData<>();

    public WeatherViewModel(UserRepository userRepository,
                            WeatherRepository weatherRepository) {
        this.userRepository = userRepository;
        this.weatherRepository = weatherRepository;

        // Источник из "БД": сохранённый город + локальный use-case
        FakeDbDataSourceWeather db = new FakeDbDataSourceWeather(userRepository, weatherRepository);
        LiveData<String> dbSource = db.loadLocalWeatherLine();

        // Источник из "сети": имитация
        FakeNetworkDataSourceWeather net = new FakeNetworkDataSourceWeather();
        LiveData<String> netSource = net.fetchWeatherLine();

        mergedSource.addSource(dbSource, s -> mergedSource.setValue("DB: " + s));
        mergedSource.addSource(netSource, s -> mergedSource.setValue("NET: " + s));
    }

    public LiveData<String> getWeatherText()   { return weatherText; }
    public LiveData<String> getStatusText()    { return statusText;  }
    public LiveData<String> getMergedSource()  { return mergedSource;}
    public LiveData<Boolean> getIsLoading()    { return isLoading;   }

    public void getWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            weatherText.setValue("Введите название города");
            return;
        }
        statusText.setValue("Загрузка погоды для " + city + "...");
        isLoading.setValue(true);

        userRepository.fetchWeatherData(city, new WeatherCallback() {
            @Override
            public void onSuccess(String weatherData) {
                weatherText.postValue(weatherData);
                statusText.postValue("");
                isLoading.postValue(false);
            }

            @Override
            public void onError(String errorMessage) {
                GetWeatherByCityUseCase useCase = new GetWeatherByCityUseCase(weatherRepository);
                Weather w = useCase.execute(city);
                weatherText.postValue("Запасной вариант:\n" +
                        String.format("Погода в %s: %d°C", w.getCity(), w.getTemperature()));
                statusText.postValue("Ошибка: " + errorMessage);
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
        weatherText.setValue("Город " + city + " сохранен в избранное");
    }

    public void recognizeWeather() {
        RecognizeWeatherFromPhotoUseCase uc = new RecognizeWeatherFromPhotoUseCase(weatherRepository);
        String result = uc.execute();
        weatherText.setValue(String.format("Анализ фото: %s", result));
    }

    public void logout() {
        new LogoutUseCase(userRepository).execute();
        statusText.setValue("Вы вышли из системы");
    }
}
