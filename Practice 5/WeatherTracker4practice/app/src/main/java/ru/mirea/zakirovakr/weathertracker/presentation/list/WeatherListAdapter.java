package ru.mirea.zakirovakr.weathertracker.presentation.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.zakirovakr.data.data.remote.WeatherCodeMapper;
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

    public void addFirst(WeatherItem item) {
        if (item == null) return;
        items.add(0, item);
        notifyItemInserted(0);
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

        String owCode = mapConditionToOwIconCode(it.getCondition());
        String url = WeatherCodeMapper.owIconUrl(owCode);

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic)
                .error(R.drawable.ic)
                .into(h.ivWeatherIcon);
    }

    @Override
    public int getItemCount() { return items.size(); }

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

    private String mapConditionToOwIconCode(String cond) {
        if (cond == null) return "03d";
        String c = cond.toLowerCase();
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
