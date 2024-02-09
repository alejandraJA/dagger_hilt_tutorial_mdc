package com.example.dagger_hilt.domain

import androidx.lifecycle.LiveData
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts
import com.example.dagger_hilt.sys.util.ResourceKts

interface IMovieRepositoryKts {
    fun loadMovies(): LiveData<ResourceKts<List<MovieEntityKts>>>
    fun updateMovie(id: Int, check: Boolean)
}