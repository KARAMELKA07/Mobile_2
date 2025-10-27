package ru.mirea.zakirovakr.weathertracker.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.zakirovakr.weathertracker.R;

public class ProfileFragment extends Fragment {

    private WeatherViewModel vm;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        vm = new ViewModelProvider(requireActivity(), new ViewModelFactory(requireContext()))
                .get(WeatherViewModel.class);

        Button btnLogout = v.findViewById(R.id.buttonLogout);
        btnLogout.setOnClickListener(view -> {
            vm.logout();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}
