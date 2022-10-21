package com.example.dagger_hilt.domain

import javax.inject.Inject
import com.example.dagger_hilt.data.datasource.database.dao.MovieDao
import androidx.lifecycle.LiveData
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity

class DatabaseRepository @Inject constructor(private val movieDao: MovieDao) {
    fun updateMovie(id: Int, check: Boolean) = movieDao.updateMovie(id, check)

    val movies: LiveData<List<MovieEntity>>
        get() = movieDao.getMovies()
}