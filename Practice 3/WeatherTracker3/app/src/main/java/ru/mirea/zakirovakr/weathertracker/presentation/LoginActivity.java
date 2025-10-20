package ru.mirea.zakirovakr.weathertracker.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.zakirovakr.data.data.repository.UserRepositoryImpl;
import ru.mirea.zakirovakr.domain.domain.models.User;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;
import ru.mirea.zakirovakr.domain.domain.usecases.LoginUseCase;
import ru.mirea.zakirovakr.domain.domain.usecases.RegisterUseCase;
import ru.mirea.zakirovakr.weathertracker.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private ProgressBar progressBar;
    private TextView textViewStatus;

    private LoginUseCase loginUseCase;
    private RegisterUseCase registerUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupDependencies();
        setupClickListeners();
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressBar);
        textViewStatus = findViewById(R.id.textViewStatus);
    }

    private void setupDependencies() {
        UserRepository userRepository = new UserRepositoryImpl(this);
        loginUseCase = new LoginUseCase(userRepository);
        registerUseCase = new RegisterUseCase(userRepository);
    }

    private void setupClickListeners() {
        buttonLogin.setOnClickListener(v -> login());
        buttonRegister.setOnClickListener(v -> register());
    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (validateInput(email, password)) {
            showLoading(true);
            loginUseCase.execute(email, password, new UserRepository.AuthCallback() {
                @Override
                public void onSuccess(User user) {
                    showLoading(false);
                    showStatus("Вход выполнен: " + user.getEmail());
                    navigateToMainActivity();
                }

                @Override
                public void onError(String errorMessage) {
                    showLoading(false);
                    showStatus("Ошибка: " + errorMessage);
                }
            });
        }
    }

    private void register() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (validateInput(email, password)) {
            showLoading(true);
            registerUseCase.execute(email, password, new UserRepository.AuthCallback() {
                @Override
                public void onSuccess(User user) {
                    showLoading(false);
                    showStatus("Регистрация успешна: " + user.getEmail());
                    navigateToMainActivity();
                }

                @Override
                public void onError(String errorMessage) {
                    showLoading(false);
                    showStatus("Ошибка: " + errorMessage);
                }
            });
        }
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showStatus("Заполните все поля");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showStatus("Введите корректный email");
            return false;
        }
        if (password.length() < 6) {
            showStatus("Пароль должен быть не менее 6 символов");
            return false;
        }
        return true;
    }

    private void showLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        buttonLogin.setEnabled(!loading);
        buttonRegister.setEnabled(!loading);
    }

    private void showStatus(String message) {
        textViewStatus.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}