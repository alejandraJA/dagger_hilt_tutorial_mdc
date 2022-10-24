package com.example.dagger_hilt.sys.di

import android.content.Context
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.dagger_hilt.data.db.AppDatabase
import androidx.room.Room
import com.example.dagger_hilt.data.datasource.database.dao.MovieDao
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providerDatabase(@ApplicationContext app: Context?) = Room.databaseBuilder(
        app!!,
        AppDatabase::class.java,
        "movies"
    ).build()

    @Singleton
    @Provides
    fun provideQuotesDao(db: AppDatabase): MovieDao = db.quoteDao()
}