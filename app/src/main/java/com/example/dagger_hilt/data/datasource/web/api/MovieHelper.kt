package com.example.dagger_hilt.data.datasource.web.api

import com.example.dagger_hilt.data.datasource.web.models.MoviesResponse
import com.example.dagger_hilt.data.datasource.web.util.WebStatus
import retrofit2.Response

interface MovieHelper {
    suspend fun loadMovies(apiKey: String, webStatus: WebStatus<MoviesResponse>)
}