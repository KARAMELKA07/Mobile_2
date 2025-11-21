package ru.mirea.zakirovakr.weathertracker.presentation;

import android.os.Bundle;
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

    @Nullable
    @Override
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

        // ==== клик по карточке ====
        adapter.setOnItemClickListener(item -> {
            WeatherDetailFragment fragment = WeatherDetailFragment.newInstance(item);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        vm.getWeatherList().observe(getViewLifecycleOwner(), adapter::setItems);
        vm.getStatusText().observe(getViewLifecycleOwner(), s ->
                textStatus.setText(s == null ? "" : s)
        );

        vm.getLatestItem().observe(getViewLifecycleOwner(), item -> {
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
}
