package com.example.dagger_hilt.data.datasource.web.api

import com.example.dagger_hilt.data.datasource.web.models.MoviesResponse
import com.example.dagger_hilt.data.datasource.web.util.Resolve
import com.example.dagger_hilt.data.datasource.web.util.WebStatus
import javax.inject.Inject

class MovieHelperImp @Inject constructor(private val movieService: MovieService) : MovieHelper {
    override suspend fun loadMovies(apiKey: String, webStatus: WebStatus<MoviesResponse>) =
        Resolve(movieService.loadMovies(apiKey), webStatus).invoke()
}