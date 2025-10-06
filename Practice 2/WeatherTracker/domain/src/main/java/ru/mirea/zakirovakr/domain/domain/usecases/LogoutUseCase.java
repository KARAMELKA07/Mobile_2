package ru.mirea.zakirovakr.domain.domain.usecases;

import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;

public class LogoutUseCase {
    private final UserRepository authRepository;

    public LogoutUseCase(UserRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute() {
        authRepository.logout();
    }
}
