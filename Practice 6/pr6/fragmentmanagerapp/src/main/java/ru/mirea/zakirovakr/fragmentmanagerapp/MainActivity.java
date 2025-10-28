package ru.mirea.zakirovakr.fragmentmanagerapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private ShareViewModel viewModel;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        viewModel = new ViewModelProvider(this).get(ShareViewModel.class);
        viewModel.getSomeValue().observe(this, item -> {
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.headerContainerView, HeaderFragment.class, null)
                    .add(R.id.detailsContainerView, DetailsFragment.class, null)
                    .commit();
        }
    }
}