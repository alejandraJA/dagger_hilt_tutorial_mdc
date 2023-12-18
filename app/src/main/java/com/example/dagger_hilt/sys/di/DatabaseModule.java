package com.example.dagger_hilt.sys.di;

import android.content.Context;

import androidx.room.Room;

import com.example.dagger_hilt.data.datasource.database.dao.MovieDao;
import com.example.dagger_hilt.data.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DatabaseModule {
    @Singleton
    @Provides
    public static AppDatabase providerDatabase(@ApplicationContext Context app) {
        return Room.databaseBuilder(
                app,
                AppDatabase.class,
                "moviesJvm"
        ).build();
    }

    @Singleton
    @Provides
    public static MovieDao provideQuotesDao(AppDatabase db) {
        return db.quoteDao();
    }

}
