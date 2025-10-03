package ru.mirea.zakirovakr.filmapp.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.zakirovakr.data.data.repository.MovieRepositoryImpl;
import ru.mirea.zakirovakr.data.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.zakirovakr.filmapp.R;
import ru.mirea.zakirovakr.domain.models.MovieD;
import ru.mirea.zakirovakr.domain.repository.MovieRepository;
import ru.mirea.zakirovakr.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.zakirovakr.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private SaveMovieToFavoriteUseCase saveMovieUseCase;
    private GetFavoriteFilmUseCase getMovieUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация зависимостей
        SharedPrefMovieStorage storage = new SharedPrefMovieStorage(this);
        MovieRepository repository = new MovieRepositoryImpl(storage);
        saveMovieUseCase = new SaveMovieToFavoriteUseCase(repository);
        getMovieUseCase = new GetFavoriteFilmUseCase(repository);

        // Инициализация UI
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        Button saveButton = findViewById(R.id.saveButton);
        Button getButton = findViewById(R.id.getButton);

        saveButton.setOnClickListener(v -> saveMovie());
        getButton.setOnClickListener(v -> getMovie());
    }

    private void saveMovie() {
        String movieName = editText.getText().toString();
        if (!movieName.isEmpty()) {
            MovieD movie = new MovieD(1, movieName);
            saveMovieUseCase.execute(movie);
            editText.setText("");
        }
    }

    private void getMovie() {
        MovieD movie = getMovieUseCase.execute();
        textView.setText("Любимый фильм: " + movie.getName());
    }
}