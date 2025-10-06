package ru.mirea.zakirovakr.data.data.repository;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.zakirovakr.data.data.network.NetworkApi;
import ru.mirea.zakirovakr.data.data.storage.DatabaseStorage;
import ru.mirea.zakirovakr.data.data.storage.PreferencesStorage;
import ru.mirea.zakirovakr.domain.domain.models.User;
import ru.mirea.zakirovakr.domain.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private final FirebaseAuth firebaseAuth;
    private final PreferencesStorage preferencesStorage;
    private final DatabaseStorage databaseStorage;
    private final NetworkApi networkApi;

    public UserRepositoryImpl(Context context) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.preferencesStorage = new PreferencesStorage(context);
        this.databaseStorage = new DatabaseStorage(context);
        this.networkApi = new NetworkApi();
    }

    @Override
    public void loginWithEmail(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            User user = mapToDomainUser(firebaseUser);

                            preferencesStorage.saveUserEmail(user.getEmail());
                            preferencesStorage.saveUserName(user.getDisplayName());
                            preferencesStorage.saveLastLogin(System.currentTimeMillis());

                            databaseStorage.saveUser(user);

                            callback.onSuccess(user);
                        } else {
                            callback.onError("User not found");
                        }
                    } else {
                        callback.onError(task.getException() != null ?
                                task.getException().getMessage() : "Login failed");
                    }
                });
    }

    @Override
    public void registerWithEmail(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            User user = mapToDomainUser(firebaseUser);

                            preferencesStorage.saveUserEmail(user.getEmail());
                            preferencesStorage.saveUserName(user.getDisplayName());
                            preferencesStorage.saveLastLogin(System.currentTimeMillis());

                            databaseStorage.saveUser(user);

                            callback.onSuccess(user);
                        } else {
                            callback.onError("User creation failed");
                        }
                    } else {
                        callback.onError(task.getException() != null ?
                                task.getException().getMessage() : "Registration failed");
                    }
                });
    }

    @Override
    public void logout() {
        preferencesStorage.clearUserData();
        firebaseAuth.signOut();
    }

    @Override
    public boolean isUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            return mapToDomainUser(firebaseUser);
        }
        return null;
    }

    @Override
    public void saveUserPreferences(String key, String value) {
        preferencesStorage.savePreference(key, value);
    }

    @Override
    public String getUserPreferences(String key) {
        return preferencesStorage.getPreference(key);
    }

    @Override
    public void saveUserToDatabase(User user) {
        databaseStorage.saveUser(user);
    }

    @Override
    public User getUserFromDatabase() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            return databaseStorage.getUser(firebaseUser.getUid());
        }
        return null;
    }


    @Override
    public void fetchWeatherData(String city, WeatherCallback callback) {
        new Thread(() -> {
            try {

                saveFavoriteCity(city);


                String weatherData = networkApi.fetchWeatherData(city);
                callback.onSuccess(weatherData);
            } catch (Exception e) {
                callback.onError("Ошибка получения данных: " + e.getMessage());
            }
        }).start();
    }

    @Override
    public void saveFavoriteCity(String city) {

        preferencesStorage.savePreference("favorite_city", city);
    }

    private User mapToDomainUser(FirebaseUser firebaseUser) {
        String displayName = firebaseUser.getDisplayName();
        if (displayName == null) {
            String email = firebaseUser.getEmail();
            if (email != null && email.contains("@")) {
                displayName = email.substring(0, email.indexOf("@"));
            } else {
                displayName = "Пользователь";
            }
        }

        return new User(
                firebaseUser.getUid(),
                firebaseUser.getEmail(),
                displayName
        );
    }
}