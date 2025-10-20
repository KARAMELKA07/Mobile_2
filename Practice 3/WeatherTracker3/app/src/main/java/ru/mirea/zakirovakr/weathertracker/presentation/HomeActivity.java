package ru.mirea.zakirovakr.weathertracker.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.zakirovakr.data.data.repository.UserRepositoryImpl;
import ru.mirea.zakirovakr.data.data.repository.WeatherRepositoryImpl;
import ru.mirea.zakirovakr.domain.domain.models.Weather;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.domain.domain.repository.WeatherRepository;
import ru.mirea.zakirovakr.domain.domain.usecases.GetWeatherByCityUseCase;
import ru.mirea.zakirovakr.weathertracker.R;

public class HomeActivity extends AppCompatActivity {

    private EditText editTextCity;
    private TextView textViewResult, textViewAuthStatus, textViewStatus;
    private Button buttonGetWeather, buttonLogin, buttonLogout;

    private UserRepository userRepository;
    private WeatherViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userRepository = new UserRepositoryImpl(this);
        vm = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(WeatherViewModel.class);

        initViews();
        setupClickListeners();
        bindObservers();
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
        textViewStatus = findViewById(R.id.textViewStatus);
        buttonGetWeather = findViewById(R.id.buttonGetWeather);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogout = findViewById(R.id.buttonLogout);
    }

    private void bindObservers() {
        vm.getWeatherText().observe(this, s -> textViewResult.setText(s));
        vm.getStatusText().observe(this, s -> {
            if (textViewStatus != null) textViewStatus.setText(s);
        });
    }

    private void setupClickListeners() {
        buttonGetWeather.setOnClickListener(v -> {
            String city = editTextCity.getText().toString().trim();
            vm.getWeather(city);
        });
        buttonLogin.setOnClickListener(v -> navigateToLogin());
        buttonLogout.setOnClickListener(v -> logout());
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
        vm.logout();
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