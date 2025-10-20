package ru.mirea.zakirovakr.filmapp.presentation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.zakirovakr.filmapp.R;
import ru.mirea.zakirovakr.domain.models.MovieD;

public class MainActivity extends AppCompatActivity {

    private MainViewModel vm;

    private EditText editText;
    private TextView textView;
    private Button saveButton;
    private Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(MainActivity.class.getSimpleName(), "MainActivity created");

        vm = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(MainViewModel.class);

        initViews();
        bindObservers();
        bindClicks();
    }

    private void initViews() {
        editText   = findViewById(R.id.editText);
        textView   = findViewById(R.id.textView);
        saveButton = findViewById(R.id.buttonSaveMovie);
        getButton  = findViewById(R.id.buttonGetMovie);
    }

    private void bindObservers() {
        vm.getFavoriteMovie().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
    }

    private void bindClicks() {
        saveButton.setOnClickListener(v -> {
            String name = editText.getText().toString().trim();
            if (!name.isEmpty()) {
                vm.setText(new MovieD(1, name));
                editText.setText("");
            }
        });

        getButton.setOnClickListener(v -> vm.getText());
    }
}
