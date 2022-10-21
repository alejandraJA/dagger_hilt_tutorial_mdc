package com.example.dagger_hilt.sys.di

import android.content.Context
import android.content.SharedPreferences
import com.example.dagger_hilt.sys.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(Constants.USER_MEMORY, Context.MODE_PRIVATE)

}