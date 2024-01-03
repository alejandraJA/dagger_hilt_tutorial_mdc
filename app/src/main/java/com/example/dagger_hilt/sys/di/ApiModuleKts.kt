package com.example.dagger_hilt.sys.di

import com.example.dagger_hilt.BuildConfig
import com.example.dagger_hilt.data.datasource.web.api.MovieServiceKts
import com.example.dagger_hilt.sys.util.AppExecutorsKts
import com.example.dagger_hilt.sys.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModuleKts {

    @Singleton
    @Provides
    @Named("clientKts")
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    @Named("retrofitKts")
    fun provideRetrofit(@Named("clientKts") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(@Named("retrofitKts") retrofit: Retrofit): MovieServiceKts =
        retrofit.create(MovieServiceKts::class.java)

    @Provides
    @Singleton
    fun provideAppExecutor(): AppExecutorsKts = AppExecutorsKts()
}
