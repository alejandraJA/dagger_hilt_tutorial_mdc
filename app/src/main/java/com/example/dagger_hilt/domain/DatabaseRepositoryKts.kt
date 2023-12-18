package com.example.dagger_hilt.domain

import javax.inject.Inject
import com.example.dagger_hilt.data.datasource.database.dao.MovieDaoKts
import androidx.lifecycle.LiveData
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts

class DatabaseRepositoryKts @Inject constructor(private val movieDaoKts: MovieDaoKts) {
    fun updateMovie(id: Int, check: Boolean) = movieDaoKts.updateMovie(id, check)

    val movies: LiveData<List<MovieEntityKts>>
        get() = movieDaoKts.getMovies()
}