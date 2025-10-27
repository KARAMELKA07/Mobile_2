package ru.mirea.zakirovakr.weathertracker.presentation.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.zakirovakr.domain.domain.models.WeatherItem;
import ru.mirea.zakirovakr.weathertracker.R;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.VH> {

    private final List<WeatherItem> items = new ArrayList<>();
    private Context context;

    public void setItems(List<WeatherItem> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_weather_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        WeatherItem it = items.get(position);

        h.tvCity.setText(it.getCity());
        h.tvTempBig.setText(it.getTemperature());
        h.tvCondition.setText(it.getCondition());
        h.tvHumidity.setText(it.getHumidity());
        h.tvWind.setText(it.getWind());

        int resId = context.getResources()
                .getIdentifier(it.getIconName(), "drawable", context.getPackageName());
        if (resId != 0) {
            h.ivWeatherIcon.setImageResource(resId);
        } else {
            h.ivWeatherIcon.setImageResource(R.drawable.ic);
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    public void addFirst(WeatherItem item) {
        if (item == null) return;
        items.add(0, item);
        notifyItemInserted(0);
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvCity, tvTempBig, tvCondition, tvHumidity, tvWind;
        ImageView ivWeatherIcon;
        VH(@NonNull View v) {
            super(v);
            tvCity        = v.findViewById(R.id.tvCity);
            tvTempBig     = v.findViewById(R.id.tvTempBig);
            tvCondition   = v.findViewById(R.id.tvCondition);
            tvHumidity    = v.findViewById(R.id.tvHumidity);
            tvWind        = v.findViewById(R.id.tvWind);
            ivWeatherIcon = v.findViewById(R.id.ivWeatherIcon);
        }
    }
}


