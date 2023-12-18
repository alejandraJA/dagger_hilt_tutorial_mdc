package com.example.dagger_hilt.sys.di

import com.example.dagger_hilt.BuildConfig
import com.example.dagger_hilt.data.datasource.web.api.MovieHelperKts
import com.example.dagger_hilt.data.datasource.web.api.MovieHelperKtsImp
import com.example.dagger_hilt.data.datasource.web.api.MovieServiceKts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModuleKts {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val build = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
        return build
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieServiceKts = retrofit.create(MovieServiceKts::class.java)

    @Provides
    @Singleton
    fun provideMovieHelper(movieHelperImp: MovieHelperKtsImp): MovieHelperKts = movieHelperImp
}