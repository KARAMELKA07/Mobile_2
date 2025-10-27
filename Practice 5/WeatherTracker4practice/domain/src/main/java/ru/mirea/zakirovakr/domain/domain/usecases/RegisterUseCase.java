package ru.mirea.zakirovakr.domain.domain.usecases;

import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;

public class RegisterUseCase {
    private final UserRepository authRepository;

    public RegisterUseCase(UserRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, UserRepository.AuthCallback callback) {
        authRepository.registerWithEmail(email, password, callback);
    }
}
