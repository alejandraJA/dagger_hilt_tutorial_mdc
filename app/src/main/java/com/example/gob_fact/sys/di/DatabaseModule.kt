package com.example.gob_fact.sys.di

import android.content.Context
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.gob_fact.data.db.AppDatabase
import androidx.room.Room
import com.example.gob_fact.data.datasource.database.dao.FactDao
import com.example.gob_fact.data.datasource.storage.IStorage
import com.example.gob_fact.data.datasource.storage.Storage
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providerDatabase(@ApplicationContext app: Context?): AppDatabase = Room.databaseBuilder(
        app!!,
        AppDatabase::class.java,
        "fact"
    ).build()

    @Singleton
    @Provides
    fun provideFactDao(db: AppDatabase): FactDao = db.factDao()

    @Singleton
    @Provides
    fun provideStorageRepository(storage: Storage): IStorage = storage

}