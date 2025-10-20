package ru.mirea.zakirovakr.weathertracker.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.zakirovakr.weathertracker.R;

public class MainActivity extends AppCompatActivity {

    private WeatherViewModel vm;

    private EditText editTextCity;
    private TextView textViewStatus;
    private TextView textMerged;
    private Button buttonGetWeather;
    private Button buttonLogout;
    private FrameLayout weatherCardContainer;
    private View weatherCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vm = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(WeatherViewModel.class);

        initViews();
        bindObservers();
        setupClickListeners();
    }

    private void initViews() {
        editTextCity         = findViewById(R.id.editTextCity);
        textViewStatus       = findViewById(R.id.textViewStatus);
        textMerged           = findViewById(R.id.textMerged);
        buttonGetWeather     = findViewById(R.id.buttonGetWeather);
        buttonLogout         = findViewById(R.id.buttonLogout);
        weatherCardContainer = findViewById(R.id.weatherCardContainer);
    }

    private void bindObservers() {

        vm.getWeatherText().observe(this, this::renderWeatherCard);


        vm.getStatusText().observe(this, s -> {
            if (textViewStatus != null) textViewStatus.setText(s);
        });


        vm.getMergedSource().observe(this, s -> {
            if (textMerged != null) {
                textMerged.setText(s == null ? "" : s);
                textMerged.setVisibility(s == null || s.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });


        vm.getIsLoading().observe(this, isLoading -> {
            if (buttonGetWeather != null) {
                buttonGetWeather.setEnabled(isLoading == null || !isLoading);
            }
        });
    }

    private void setupClickListeners() {
        findViewById(R.id.buttonGetWeather).setOnClickListener(v -> {
            String city = editTextCity.getText().toString().trim();
            vm.getWeather(city);
        });

        findViewById(R.id.buttonSaveCity).setOnClickListener(v -> {
            String city = editTextCity.getText().toString().trim();
            vm.saveCity(city);
        });

        findViewById(R.id.buttonRecognizeWeather).setOnClickListener(v -> vm.recognizeWeather());

        buttonLogout.setOnClickListener(v -> {
            vm.logout();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }



    private void renderWeatherCard(String raw) {
        if (raw == null) raw = "";

        // Если это подсказка "Введите город..." — показываем простую надпись и не рисуем карточку
        String trimmed = raw.trim();
        String lower   = trimmed.toLowerCase();
        if (lower.startsWith("введите")) {
            clearCard();
            if (textViewStatus != null) textViewStatus.setText(trimmed);
            return;
        }


        if (trimmed.isEmpty()) {
            clearCard();
            return;
        }

        ensureCard();

        TextView tvCity    = weatherCardView.findViewById(R.id.tvCity);
        TextView tvTempBig = weatherCardView.findViewById(R.id.tvTempBig);
        TextView tvCond    = weatherCardView.findViewById(R.id.tvCondition);
        TextView tvHum     = weatherCardView.findViewById(R.id.tvHumidity);
        TextView tvWind    = weatherCardView.findViewById(R.id.tvWind);
        ImageView ivIcon   = weatherCardView.findViewById(R.id.ivWeatherIcon);

        // Ожидаемый формат из NetworkApi:
        // "🌍 Город: %s\n%s\n🌡️ Температура: %s\n💧 Влажность: %d%%\n💨 Ветер: %s"
        String city = "", condition = "", temp = "", humidity = "", wind = "";
        try {
            String[] lines = raw.split("\n");
            for (String line : lines) {
                String l = line.trim();
                if (l.startsWith("🌍") || l.startsWith("Город")) {
                    city = l.replace("🌍", "").replace("Город:", "").trim();
                } else if (l.startsWith("🌡") || l.startsWith("Температура")) {
                    temp = l.replace("🌡️", "").replace("Температура:", "").trim();
                } else if (l.startsWith("💧") || l.startsWith("Влажность")) {
                    humidity = l.replace("💧", "").trim();
                } else if (l.startsWith("💨") || l.startsWith("Ветер")) {
                    wind = l.replace("💨", "").trim();
                } else {
                    condition = l
                            .replace("☀️", "")
                            .replace("⛅", "")
                            .replace("🌧️", "")
                            .replace("❄️", "")
                            .replace("💨", "")
                            .replace("🌫️", "")
                            .trim();
                }
            }
        } catch (Exception ignore) { }

        tvCity.setText(city.isEmpty() ? "Город" : city);
        tvTempBig.setText(
                temp.isEmpty() ? "—" : temp.replace("Температура", "")
                        .replace(":", "")
                        .trim()
        );
        tvCond.setText(condition);
        tvHum.setText(humidity.isEmpty() ? "" : humidity);
        tvWind.setText(wind.isEmpty() ? "" : wind);

        // Всегда одна и та же картинка (пока), как просили
        ivIcon.setImageResource(R.drawable.ic);
    }

    private void ensureCard() {
        if (weatherCardView == null) {
            weatherCardView = LayoutInflater.from(this)
                    .inflate(R.layout.item_weather_card, weatherCardContainer, false);
            weatherCardContainer.removeAllViews();
            weatherCardContainer.addView(weatherCardView);
        }
    }

    private void clearCard() {
        if (weatherCardContainer != null) {
            weatherCardContainer.removeAllViews();
        }
        weatherCardView = null;
    }
}
