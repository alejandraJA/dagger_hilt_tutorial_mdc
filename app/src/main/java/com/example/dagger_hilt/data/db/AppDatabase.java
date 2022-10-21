package com.example.dagger_hilt.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dagger_hilt.data.datasource.database.dao.MovieDao;
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao quoteDao();
}
