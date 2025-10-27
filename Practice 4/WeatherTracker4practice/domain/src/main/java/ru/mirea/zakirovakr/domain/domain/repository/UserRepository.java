package ru.mirea.zakirovakr.domain.domain.repository;

import ru.mirea.zakirovakr.domain.domain.models.User;

public interface UserRepository {
    interface AuthCallback {
        void onSuccess(User user);
        void onError(String errorMessage);
    }

    interface WeatherCallback {
        void onSuccess(String weatherData);
        void onError(String errorMessage);
    }

    void loginWithEmail(String email, String password, AuthCallback callback);
    void registerWithEmail(String email, String password, AuthCallback callback);
    void logout();
    boolean isUserLoggedIn();
    User getCurrentUser();


    void saveUserPreferences(String key, String value);
    String getUserPreferences(String key);
    void saveUserToDatabase(User user);
    User getUserFromDatabase();

    void fetchWeatherData(String city, WeatherCallback callback);

    void saveFavoriteCity(String city);
}
