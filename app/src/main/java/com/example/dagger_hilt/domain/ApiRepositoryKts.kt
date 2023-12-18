package com.example.dagger_hilt.domain

import com.example.dagger_hilt.data.datasource.database.dao.MovieDaoKts
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts
import com.example.dagger_hilt.data.datasource.web.api.MovieHelperKts
import com.example.dagger_hilt.data.datasource.web.models.MoviesResponseKts
import com.example.dagger_hilt.sys.util.ConstantsKts
import com.example.dagger_hilt.sys.util.ResourceKts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepositoryKts @Inject constructor(
    private val movieHelperKts: MovieHelperKts,
    private val dao: MovieDaoKts
) : SuperServiceKts() {

    suspend fun loadMovies(function: (ResourceKts<String>) -> Unit) =
        withContext(Dispatchers.IO) {
            val onSuccess: (MoviesResponseKts) -> Unit = { moviesResponse ->
                if (moviesResponse.totalResults == 0) {
                    function.invoke(ResourceKts.blank())
                } else {
                    moviesResponse.listMovies.forEach { movie ->
                        dao.setMovie(
                            MovieEntityKts(
                                movie.id,
                                movie.title,
                                movie.originalTitle,
                                movie.overview,
                                movie.posterPath,
                                like = false
                            )
                        )
                    }
                    function.invoke(ResourceKts.success(""))
                }
            }
            val onError: (String) -> Unit = { errorMessage ->
                function.invoke(ResourceKts.error(errorMessage))
            }
            if (dao.countMovies() == 0) {
                movieHelperKts.loadMovies(ConstantsKts.API_KEY, getWebStatus(onSuccess, onError))
            }
        }
}