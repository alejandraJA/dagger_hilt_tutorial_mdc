package com.example.dagger_hilt.data.datasource.web.api

import com.example.dagger_hilt.data.datasource.web.models.MoviesResponseKts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServiceKts {
    @GET("discover/movie")
    suspend fun loadMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponseKts>
}