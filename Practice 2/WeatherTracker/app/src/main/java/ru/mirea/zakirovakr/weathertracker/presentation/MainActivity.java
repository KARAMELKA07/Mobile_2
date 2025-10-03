package ru.mirea.zakirovakr.weathertracker.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.zakirovakr.weathertracker.R;
import ru.mirea.zakirovakr.weathertracker.data.repository.WeatherRepositoryImpl;
import ru.mirea.zakirovakr.weathertracker.domain.models.Weather;
import ru.mirea.zakirovakr.weathertracker.domain.repository.WeatherRepository;
import ru.mirea.zakirovakr.weathertracker.domain.usecases.GetWeatherByCityUseCase;
import ru.mirea.zakirovakr.weathertracker.domain.usecases.RecognizeWeatherFromPhotoUseCase;
import ru.mirea.zakirovakr.weathertracker.domain.usecases.SaveFavoriteCityUseCase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextCity = findViewById(R.id.editTextCity);
        TextView textViewResult = findViewById(R.id.textViewResult);

        WeatherRepository weatherRepository = new WeatherRepositoryImpl();
        GetWeatherByCityUseCase getWeatherUseCase = new GetWeatherByCityUseCase(weatherRepository);
        SaveFavoriteCityUseCase saveCityUseCase = new SaveFavoriteCityUseCase(weatherRepository);
        RecognizeWeatherFromPhotoUseCase recognizeWeatherUseCase = new RecognizeWeatherFromPhotoUseCase(weatherRepository);

        findViewById(R.id.buttonGetWeather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editTextCity.getText().toString();
                Weather weather = getWeatherUseCase.execute(city);
                textViewResult.setText(String.format("Погода в %s: %d°C", weather.getCity(), weather.getTemperature()));
            }
        });

        findViewById(R.id.buttonSaveCity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editTextCity.getText().toString();
                boolean result = saveCityUseCase.execute(city);
                textViewResult.setText(String.format("Сохранение %s: %s", city, result ? "Успешно" : "Ошибка"));
            }
        });

        findViewById(R.id.buttonRecognizeWeather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = recognizeWeatherUseCase.execute();
                textViewResult.setText(String.format("Анализ фото: %s", result));
            }
        });
    }
}