package com.example.dagger_hilt.sys.di;

import androidx.annotation.NonNull;

import com.example.dagger_hilt.BuildConfig;
import com.example.dagger_hilt.data.datasource.web.api.MovieService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class ApiModule {

    @NonNull
    @Singleton
    @Provides
    @Named("clientJvm")
    public static OkHttpClient provideOkHttpClient() {
        if (BuildConfig.DEBUG) {
            var loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        } else {
            return new OkHttpClient.Builder().build();
        }
    }

    @Singleton
    @Provides
    @Named("retrofitJvm")
    public static Retrofit provideRetrofit(@Named("clientJvm") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public static MovieService provideMovieService(@Named("retrofitJvm") @NonNull Retrofit retrofit) {
        return retrofit.create(MovieService.class);
    }
}
