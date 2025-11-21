package ru.mirea.zakirovakr.data.data.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    @NonNull
    public String uid;

    public String email;
    public String displayName;
    public long createdAt;
    public long lastLogin;

    public UserEntity(@NonNull String uid, String email, String displayName, long createdAt, long lastLogin) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
    }
}
