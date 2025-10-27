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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.zakirovakr.weathertracker.R;
import ru.mirea.zakirovakr.weathertracker.presentation.list.WeatherListAdapter ;

public class MainActivity extends AppCompatActivity {

    private WeatherViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void replace(@NonNull Fragment f) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, f)
                .commit();
    }
}
