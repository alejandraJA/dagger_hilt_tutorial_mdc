package com.example.dagger_hilt.data.datasource.web.api

import androidx.lifecycle.LiveData
import com.example.dagger_hilt.data.datasource.web.models.MoviesResponseKts
import com.example.dagger_hilt.data.datasource.web.models.response.ApiResponseKts
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServiceKts {
    @GET("discover/movie")
    fun loadMovies(
        @Query("api_key") apiKey: String
    ): LiveData<ApiResponseKts<MoviesResponseKts>>
}