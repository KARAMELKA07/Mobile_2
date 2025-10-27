package ru.mirea.zakirovakr.data.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE uid = :uid")
    UserEntity getUserById(String uid);

    @Query("SELECT * FROM users WHERE email = :email")
    UserEntity getUserByEmail(String email);

    @Insert
    void insertUser(UserEntity user);

    @Update
    void updateUser(UserEntity user);

    @Query("DELETE FROM users WHERE uid = :uid")
    void deleteUser(String uid);
}
