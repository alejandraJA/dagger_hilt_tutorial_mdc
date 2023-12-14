package com.example.dagger_hilt.domain

import com.example.dagger_hilt.data.datasource.database.dao.MovieDao
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity
import com.example.dagger_hilt.data.datasource.web.api.MovieHelper
import com.example.dagger_hilt.data.datasource.web.models.MoviesResponse
import com.example.dagger_hilt.sys.util.Constants
import com.example.dagger_hilt.sys.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val movieHelper: MovieHelper,
    private val dao: MovieDao
) : SuperService() {

    suspend fun loadMovies(function: (Resource<String>) -> Unit) =
        withContext(Dispatchers.IO) {
            val onSuccess: (MoviesResponse) -> Unit = { moviesResponse ->
                if (moviesResponse.totalResults == 0) {
                    function.invoke(Resource.blank())
                } else {
                    moviesResponse.listMovies.forEach { movie ->
                        dao.setMovie(
                            MovieEntity(
                                movie.id,
                                movie.title,
                                movie.originalTitle,
                                movie.overview,
                                movie.posterPath,
                                like = false
                            )
                        )
                    }
                    function.invoke(Resource.success(""))
                }
            }
            val onError: (String) -> Unit = { errorMessage ->
                function.invoke(Resource.error(errorMessage))
            }
            if (dao.countMovies() == 0) {
                movieHelper.loadMovies(Constants.API_KEY, getWebStatus(onSuccess, onError))
            }
        }
}