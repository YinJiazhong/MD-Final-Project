package com.zen.agency.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);

    @Query("DELETE  FROM user_table")
    void deleteAllNotes();

    @Query("SELECT *  FROM user_table order by id desc")
    LiveData<List<User>> getAllUser();

}
