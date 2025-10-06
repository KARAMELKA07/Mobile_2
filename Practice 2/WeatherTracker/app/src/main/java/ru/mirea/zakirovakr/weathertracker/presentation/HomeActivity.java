package ru.mirea.zakirovakr.weathertracker.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.zakirovakr.data.data.repository.UserRepositoryImpl;
import ru.mirea.zakirovakr.data.data.repository.WeatherRepositoryImpl;
import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;
import ru.mirea.zakirovakr.domain.domain.usecases.GetWeatherByCityUseCase;
import ru.mirea.zakirovakr.weathertracker.R;

public class HomeActivity extends AppCompatActivity {

    private EditText editTextCity;
    private TextView textViewResult, textViewAuthStatus;
    private Button buttonGetWeather, buttonLogin, buttonLogout;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userRepository = new UserRepositoryImpl(this);

        initViews();
        setupClickListeners();
        checkAuthStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthStatus();
    }

    private void initViews() {
        editTextCity = findViewById(R.id.editTextCity);
        textViewResult = findViewById(R.id.textViewResult);
        textViewAuthStatus = findViewById(R.id.textViewAuthStatus);
        buttonGetWeather = findViewById(R.id.buttonGetWeather);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogout = findViewById(R.id.buttonLogout);
    }

    private void setupClickListeners() {
        buttonGetWeather.setOnClickListener(v -> getWeather());
        buttonLogin.setOnClickListener(v -> navigateToLogin());
        buttonLogout.setOnClickListener(v -> logout());
    }

    private void getWeather() {
        String city = editTextCity.getText().toString().trim();
        if (city.isEmpty()) {
            textViewResult.setText("Введите название города");
            return;
        }

        textViewResult.setText("Загрузка погоды для " + city + "...");
        buttonGetWeather.setEnabled(false);

        userRepository.fetchWeatherData(city, new UserRepository.WeatherCallback() {
            @Override
            public void onSuccess(String weatherData) {
                runOnUiThread(() -> {
                    textViewResult.setText(weatherData);
                    buttonGetWeather.setEnabled(true);
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    textViewResult.setText("Ошибка: " + errorMessage);
                    buttonGetWeather.setEnabled(true);

                    WeatherRepository weatherRepository = new WeatherRepositoryImpl();
                    GetWeatherByCityUseCase getWeatherUseCase = new GetWeatherByCityUseCase(weatherRepository);
                    Weather weather = getWeatherUseCase.execute(city);
                    textViewResult.setText("Запасной вариант:\n" +
                            String.format("Погода в %s: %d°C", weather.getCity(), weather.getTemperature()));
                });
            }
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void navigateToMain() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void logout() {
        userRepository.logout();
        checkAuthStatus();
        textViewResult.setText("Вы вышли из системы");
    }

    private void checkAuthStatus() {
        boolean isLoggedIn = userRepository.isUserLoggedIn();

        if (isLoggedIn) {
            textViewAuthStatus.setText("Вы авторизованы");
            buttonLogin.setVisibility(View.GONE);
            buttonLogout.setVisibility(View.VISIBLE);
            navigateToMain();
        } else {
            textViewAuthStatus.setText("Войдите, чтобы использовать все возможности приложения");
            buttonLogin.setVisibility(View.VISIBLE);
            buttonLogout.setVisibility(View.GONE);
        }
    }
}