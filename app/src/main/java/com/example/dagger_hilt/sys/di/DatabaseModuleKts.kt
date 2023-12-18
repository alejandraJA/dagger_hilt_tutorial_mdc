package com.example.dagger_hilt.sys.di

import android.content.Context
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.dagger_hilt.data.db.AppDatabaseKts
import androidx.room.Room
import com.example.dagger_hilt.data.datasource.database.dao.MovieDaoKts
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleKts {
    @Singleton
    @Provides
    fun providerDatabase(@ApplicationContext app: Context?) = Room.databaseBuilder(
        app!!,
        AppDatabaseKts::class.java,
        "movies"
    ).build()

    @Singleton
    @Provides
    fun provideQuotesDao(db: AppDatabaseKts): MovieDaoKts = db.quoteDao()
}