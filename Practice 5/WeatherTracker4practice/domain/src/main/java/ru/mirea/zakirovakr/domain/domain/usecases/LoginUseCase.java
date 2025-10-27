package ru.mirea.zakirovakr.domain.domain.usecases;

import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;

public class LoginUseCase {
    private final UserRepository authRepository;

    public LoginUseCase(UserRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, UserRepository.AuthCallback callback) {
        authRepository.loginWithEmail(email, password, callback);
    }
}