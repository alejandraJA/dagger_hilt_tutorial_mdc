package com.example.dagger_hilt.data.datasource.web.api;

import com.example.dagger_hilt.data.datasource.web.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    @GET("discover/movie")
    Call<MoviesResponse> loadMovies(@Query("api_key") String apiKey);
}
