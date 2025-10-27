package ru.mirea.zakirovakr.data.data.storage;

import android.content.Context;

import ru.mirea.zakirovakr.data.data.database.AppDatabase;
import ru.mirea.zakirovakr.data.data.database.UserDao;
import ru.mirea.zakirovakr.data.data.database.UserEntity;
import ru.mirea.zakirovakr.domain.domain.models.User;

public class DatabaseStorage {
    private final UserDao userDao;

    public DatabaseStorage(Context context) {
        this.userDao = AppDatabase.getInstance(context).userDao();
    }

    public void saveUser(User user) {
        new Thread(() -> {
            UserEntity existingUser = userDao.getUserById(user.getUid());
            long currentTime = System.currentTimeMillis();

            UserEntity userEntity = new UserEntity(
                    user.getUid(),
                    user.getEmail(),
                    user.getDisplayName(),
                    existingUser != null ? existingUser.createdAt : currentTime,
                    currentTime
            );

            if (existingUser != null) {
                userDao.updateUser(userEntity);
            } else {
                userDao.insertUser(userEntity);
            }
        }).start();
    }

    public User getUser(String uid) {
        try {
            UserEntity userEntity = userDao.getUserById(uid);
            if (userEntity != null) {
                return new User(
                        userEntity.uid,
                        userEntity.email,
                        userEntity.displayName
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser(String uid) {
        new Thread(() -> userDao.deleteUser(uid)).start();
    }
}