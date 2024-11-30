package com.example.gob_fact.data.datasource.web.api

import androidx.lifecycle.LiveData
import com.example.gob_fact.data.datasource.web.models.MoviesResponse
import com.example.gob_fact.data.datasource.web.models.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FactService {
    @GET("discover/movie")
    fun loadMovies(
        @Query("api_key") apiKey: String
    ): LiveData<ApiResponse<MoviesResponse>>
}