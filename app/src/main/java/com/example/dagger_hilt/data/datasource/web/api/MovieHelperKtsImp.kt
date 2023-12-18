package com.example.dagger_hilt.data.datasource.web.api

import com.example.dagger_hilt.data.datasource.web.models.MoviesResponseKts
import com.example.dagger_hilt.data.datasource.web.util.Resolve
import com.example.dagger_hilt.data.datasource.web.util.WebStatusKts
import javax.inject.Inject

class MovieHelperKtsImp @Inject constructor(private val movieServiceKts: MovieServiceKts) : MovieHelperKts {
    override suspend fun loadMovies(apiKey: String, webStatusKts: WebStatusKts<MoviesResponseKts>) =
        Resolve(movieServiceKts.loadMovies(apiKey), webStatusKts).invoke()
}