package ru.mirea.zakirovakr.weathertracker.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.zakirovakr.data.data.repository.UserRepositoryImpl;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.weathertracker.R;

public class MainActivity extends AppCompatActivity {

    private WeatherViewModel vm;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userRepository = new UserRepositoryImpl(this);

        if (!userRepository.isUserLoggedIn()) {
            goHomeAndFinish();
            return;
        }

        setContentView(R.layout.activity_main_nav);

        vm = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(WeatherViewModel.class);

        if (savedInstanceState == null) {
            replace(new FavoritesFragment());
        }

        BottomNavigationView bottom = findViewById(R.id.bottomNav);
        bottom.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_favorites) {
                replace(new FavoritesFragment());
                return true;
            } else if (id == R.id.menu_recognition) {
                replace(new RecognitionFragment());
                return true;
            } else if (id == R.id.menu_profile) {
                replace(new ProfileFragment());
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!userRepository.isUserLoggedIn()) {
            goHomeAndFinish();
        }
    }

    private void goHomeAndFinish() {
        Intent i = new Intent(this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void replace(@NonNull Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, f)
                .commit();
    }
}
