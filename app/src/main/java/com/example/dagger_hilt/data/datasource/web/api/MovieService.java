package com.example.dagger_hilt.data.datasource.web.api;

import androidx.lifecycle.LiveData;

import com.example.dagger_hilt.data.datasource.web.models.response.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    @GET("discover/movie")
    LiveData<ApiResponse> loadMovies(@Query("api_key") String apiKey);

}
