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

import com.squareup.picasso.Picasso;

import java.util.Locale;

import ru.mirea.zakirovakr.data.data.repository.UserRepositoryImpl;
import ru.mirea.zakirovakr.domain.domain.models.WeatherItem;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.weathertracker.R;

public class HomeActivity extends AppCompatActivity {

    private EditText editTextCity;
    private TextView textViewAuthStatus, textViewStatus;
    private Button buttonGetWeather, buttonLogin, buttonLogout;

    private FrameLayout weatherCardContainer;
    private View weatherCardView;

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
        editTextCity         = findViewById(R.id.editTextCity);
        textViewAuthStatus   = findViewById(R.id.textViewAuthStatus);
        textViewStatus       = findViewById(R.id.textViewStatus);
        buttonGetWeather     = findViewById(R.id.buttonGetWeather);
        buttonLogin          = findViewById(R.id.buttonLogin);
        buttonLogout         = findViewById(R.id.buttonLogout);
        weatherCardContainer = findViewById(R.id.weatherCardContainer);
    }

    private void bindObservers() {
        vm.getStatusText().observe(this, s ->
                textViewStatus.setText(s == null ? "" : s)
        );
        vm.getLatestItem().observe(this, this::renderWeatherCard);
    }

    private void setupClickListeners() {
        buttonGetWeather.setOnClickListener(v -> {
            String city = editTextCity.getText().toString().trim();
            vm.getWeather(city);
        });

        buttonLogin.setOnClickListener(v -> navigateToLogin());
        buttonLogout.setOnClickListener(v -> doLogout());
    }

    private void navigateToLogin() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    private void navigateToMain() {
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        finish();
    }

    private void doLogout() {
        vm.logout();
        clearCard();

        checkAuthStatus();
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

    private void renderWeatherCard(WeatherItem item) {
        if (item == null) {
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

        tvCity.setText(item.getCity());
        tvTempBig.setText(item.getTemperature());
        tvCond.setText(item.getCondition());
        tvHum.setText(item.getHumidity());
        tvWind.setText(item.getWind());

        if (item.getIconUrl() != null && !item.getIconUrl().isEmpty()) {
            Picasso.get()
                    .load(item.getIconUrl())
                    .placeholder(R.drawable.ic)
                    .error(R.drawable.ic)
                    .into(ivIcon);
        } else {
            int resId = resolveIconRes(item);
            Picasso.get()
                    .load(resId)
                    .placeholder(R.drawable.ic)
                    .error(R.drawable.ic)
                    .into(ivIcon);
        }
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

    private int resolveIconRes(WeatherItem item) {
        if (item.getIconName() != null && !item.getIconName().isEmpty()) {
            int fromName = getResources().getIdentifier(
                    item.getIconName(), "drawable", getPackageName()
            );
            if (fromName != 0) return fromName;
        }

        String c = item.getCondition() == null ? "" :
                item.getCondition().toLowerCase(Locale.getDefault());

        if (c.contains("солне") || c.contains("ясно"))    return R.drawable.icon_sun;
        if (c.contains("облач") || c.contains("пасмур"))  return R.drawable.icon_cloud;
        if (c.contains("дожд") || c.contains("ливн"))     return R.drawable.icon_rain;
        if (c.contains("туман") || c.contains("дымк"))    return R.drawable.icon_fog;
        if (c.contains("ветер") || c.contains("ветрен"))  return R.drawable.icon_wind;
        if (c.contains("снег")  || c.contains("снеж"))    return R.drawable.icon_snow;

        return R.drawable.ic;
    }
}
