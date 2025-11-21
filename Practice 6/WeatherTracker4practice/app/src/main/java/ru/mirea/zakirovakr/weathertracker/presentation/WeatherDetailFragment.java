package ru.mirea.zakirovakr.weathertracker.presentation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import ru.mirea.zakirovakr.data.data.remote.WeatherCodeMapper;
import ru.mirea.zakirovakr.domain.domain.models.WeatherItem;
import ru.mirea.zakirovakr.weathertracker.R;

public class WeatherDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args == null) return;

        String city      = args.getString("city", "");
        String temp      = args.getString("temp", "");
        String cond      = args.getString("cond", "");
        String humidity  = args.getString("humidity", "");
        String wind      = args.getString("wind", "");
        String iconUrl   = args.getString("iconUrl", "");
        String feelsLike = args.getString("feelsLike", "");
        String pressure  = args.getString("pressure", "");
        String clouds    = args.getString("clouds", "");

        TextView tvCity      = v.findViewById(R.id.tvCity);
        TextView tvTempBig   = v.findViewById(R.id.tvTempBig);
        TextView tvCond      = v.findViewById(R.id.tvCondition);
        TextView tvHum       = v.findViewById(R.id.tvHumidity);
        TextView tvWind      = v.findViewById(R.id.tvWind);
        TextView tvFeelsLike = v.findViewById(R.id.tvFeelsLike);
        TextView tvPressure  = v.findViewById(R.id.tvPressure);
        TextView tvClouds    = v.findViewById(R.id.tvClouds);
        ImageView ivIcon     = v.findViewById(R.id.ivWeatherIcon);

        tvCity.setText(city);
        tvTempBig.setText(temp);
        tvCond.setText(cond);
        tvHum.setText(humidity);
        tvWind.setText(wind);

        if (!TextUtils.isEmpty(feelsLike)) tvFeelsLike.setText(feelsLike);
        if (!TextUtils.isEmpty(pressure))  tvPressure.setText(pressure);
        if (!TextUtils.isEmpty(clouds))    tvClouds.setText(clouds);

        // --- ЛОГИКА ПОДГРУЗКИ ИКОНКИ ---

        if (TextUtils.isEmpty(iconUrl)) {
            String code = mapConditionToOwIconCode(cond);
            iconUrl = WeatherCodeMapper.owIconUrl(code);
        }

        Picasso.get()
                .load(iconUrl)
                .placeholder(R.drawable.ic)
                .error(R.drawable.ic)
                .into(ivIcon);
    }

    // Фабричный метод для создания фрагмента из WeatherItem
    public static WeatherDetailFragment newInstance(WeatherItem item) {
        WeatherDetailFragment f = new WeatherDetailFragment();
        Bundle b = new Bundle();
        b.putString("city",     item.getCity());
        b.putString("temp",     item.getTemperature());
        b.putString("cond",     item.getCondition());
        b.putString("humidity", item.getHumidity());
        b.putString("wind",     item.getWind());
        b.putString("iconUrl",  item.getIconUrl());
        b.putString("feelsLike", item.getFeelsLike());
        b.putString("pressure",     item.getPressure());
        b.putString("clouds",  item.getClouds());
        f.setArguments(b);
        return f;
    }

    private String mapConditionToOwIconCode(String cond) {
        if (cond == null) return "03d";
        String c = cond.toLowerCase(Locale.getDefault());
        if (c.contains("ясн")) return "01d";
        if (c.contains("перемен") || c.contains("небольш")) return "02d";
        if (c.contains("пасмур") || c.contains("облач")) return "03d";
        if (c.contains("туман")) return "50d";
        if (c.contains("ливн")) return "09d";
        if (c.contains("дожд")) return "10d";
        if (c.contains("гроза")) return "11d";
        if (c.contains("снег"))  return "13d";
        return "03d";
    }
}
