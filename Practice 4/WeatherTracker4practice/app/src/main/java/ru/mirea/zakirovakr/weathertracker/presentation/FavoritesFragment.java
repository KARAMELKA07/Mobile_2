package ru.mirea.zakirovakr.weathertracker.presentation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.zakirovakr.domain.domain.models.WeatherItem;
import ru.mirea.zakirovakr.weathertracker.R;
import ru.mirea.zakirovakr.weathertracker.presentation.list.WeatherListAdapter;

public class FavoritesFragment extends Fragment {

    private WeatherViewModel vm;

    private EditText editTextCity;
    private TextView textStatus;
    private RecyclerView recycler;
    private WeatherListAdapter adapter;

    private String lastRenderedWeatherRaw = null;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        vm = new ViewModelProvider(requireActivity(), new ViewModelFactory(requireContext()))
                .get(WeatherViewModel.class);

        editTextCity = v.findViewById(R.id.editTextCity);
        textStatus   = v.findViewById(R.id.textViewStatus);
        recycler     = v.findViewById(R.id.recyclerWeather);

        adapter = new WeatherListAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);

        vm.getWeatherList().observe(getViewLifecycleOwner(), adapter::setItems);

        vm.getStatusText().observe(getViewLifecycleOwner(), s -> {
            textStatus.setText(s == null ? "" : s);
        });

        vm.getWeatherText().observe(getViewLifecycleOwner(), raw -> {
            if (raw == null || raw.trim().isEmpty()) return;
            if (raw.equals(lastRenderedWeatherRaw)) return;
            lastRenderedWeatherRaw = raw;

            WeatherItem item = parseRawToItem(raw);
            if (item != null) {
                adapter.addFirst(item);
                recycler.scrollToPosition(0);
            }
        });

        Button btnGet = v.findViewById(R.id.buttonGetWeather);
        Button btnSave = v.findViewById(R.id.buttonSaveCity);

        btnGet.setOnClickListener(view -> {
            String city = editTextCity.getText().toString().trim();
            vm.getWeather(city);
        });

        btnSave.setOnClickListener(view -> {
            String city = editTextCity.getText().toString().trim();
            vm.saveCity(city);
        });
    }

    private WeatherItem parseRawToItem(@NonNull String raw) {
        String city = "", condition = "", temp = "", humidity = "", wind = "";
        try {
            String[] lines = raw.split("\n");
            for (String line : lines) {
                String l = line.trim();
                if (l.startsWith("Город:")) {
                    city = l.replace("Город:", "").trim();
                } else if (l.startsWith("Температура:")) {
                    temp = l.replace("Температура:", "").trim();
                } else if (l.startsWith("Влажность:")) {
                    humidity = l;
                } else if (l.startsWith("Ветер:")) {
                    wind = l;
                } else if (!TextUtils.isEmpty(l)) {
                    condition = l;
                }
            }
        } catch (Exception ignored) {}

        if (TextUtils.isEmpty(city) || TextUtils.isEmpty(temp)) return null;

        String iconName = resolveIconName(condition);

        return new WeatherItem(
                city,
                temp,
                condition,
                iconName,
                TextUtils.isEmpty(humidity) ? "" : humidity,
                TextUtils.isEmpty(wind) ? "" : wind
        );
    }

    private String resolveIconName(String conditionRaw) {
        if (conditionRaw == null) return "ic";
        String c = conditionRaw.toLowerCase();
        if (c.contains("солне") || c.contains("ясно"))   return "icon_sun";
        if (c.contains("облач") || c.contains("пасмур")) return "icon_cloud";
        if (c.contains("дожд")  || c.contains("ливн"))   return "icon_rain";
        if (c.contains("туман") || c.contains("дымк"))   return "icon_fog";
        if (c.contains("ветер") || c.contains("ветрен")) return "icon_wind";
        if (c.contains("снег")  || c.contains("снеж"))   return "icon_snow";
        return "ic";
    }
}

