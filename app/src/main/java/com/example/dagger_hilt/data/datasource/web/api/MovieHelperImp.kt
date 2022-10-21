package com.example.dagger_hilt.data.datasource.web.api

import javax.inject.Inject

class MovieHelperImp @Inject constructor(private val movieService: MovieService): MovieHelper {
    override suspend fun loadMovies(apiKey: String) = movieService.loadMovies(apiKey)
}