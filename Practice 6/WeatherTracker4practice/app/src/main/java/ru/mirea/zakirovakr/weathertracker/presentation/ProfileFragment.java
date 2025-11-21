package ru.mirea.zakirovakr.weathertracker.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Date;

import ru.mirea.zakirovakr.data.data.storage.PreferencesStorage;
import ru.mirea.zakirovakr.data.data.repository.UserRepositoryImpl;
import ru.mirea.zakirovakr.domain.domain.models.User;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.weathertracker.R;

public class ProfileFragment extends Fragment {

    private UserRepository userRepo;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        userRepo = new UserRepositoryImpl(requireContext());

        TextView tvName  = v.findViewById(R.id.tvProfileName);
        TextView tvEmail = v.findViewById(R.id.tvProfileEmail);
        TextView tvLast  = v.findViewById(R.id.tvProfileLastLogin);

        Button btnLogin  = v.findViewById(R.id.buttonLogin);
        Button btnLogout = v.findViewById(R.id.buttonLogout);

        boolean logged = userRepo.isUserLoggedIn();
        if (logged) {
            User u = userRepo.getCurrentUser();
            PreferencesStorage prefs = new PreferencesStorage(requireContext());

            String name  = (u != null && u.getDisplayName() != null) ? u.getDisplayName() : prefs.getUserName();
            String email = (u != null && u.getEmail() != null) ? u.getEmail() : prefs.getUserEmail();
            long last    = prefs.getLastLogin();

            tvName.setText("Имя: " + (name == null ? "—" : name));
            tvEmail.setText("Email: " + (email == null ? "—" : email));
            String lastStr = (last == 0) ? "—" :
                    DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
                            .format(new Date(last));
            tvLast.setText("Последний вход: " + lastStr);

            tvName.setVisibility(View.VISIBLE);
            tvEmail.setVisibility(View.VISIBLE);
            tvLast.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);

            btnLogin.setVisibility(View.GONE);

            btnLogout.setOnClickListener(view -> {
                userRepo.logout();
                requireActivity().recreate();
            });
        } else {
            tvName.setVisibility(View.GONE);
            tvEmail.setVisibility(View.GONE);
            tvLast.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);

            btnLogin.setVisibility(View.VISIBLE);
            btnLogin.setOnClickListener(view -> {
                startActivity(new Intent(requireContext(), LoginActivity.class));
                requireActivity().finish();
            });
        }
    }
}
