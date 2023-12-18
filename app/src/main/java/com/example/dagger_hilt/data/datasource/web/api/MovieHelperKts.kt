package com.example.dagger_hilt.data.datasource.web.api

import com.example.dagger_hilt.data.datasource.web.models.MoviesResponseKts
import com.example.dagger_hilt.data.datasource.web.util.WebStatusKts

interface MovieHelperKts {
    suspend fun loadMovies(apiKey: String, webStatusKts: WebStatusKts<MoviesResponseKts>)
}