package com.example.dagger_hilt.domain

import com.example.dagger_hilt.data.datasource.database.dao.MovieDao
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity
import com.example.dagger_hilt.data.datasource.web.api.MovieHelper
import com.example.dagger_hilt.sys.util.Constants
import com.example.dagger_hilt.sys.util.Resource
import org.json.JSONObject
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val movieHelper: MovieHelper,
    private val dao: MovieDao
) {
    suspend fun loadMovies(function: (Resource<String>) -> Unit) {
        if (dao.countMovies() == 0) {
            val moviesResponse = movieHelper.loadMovies(Constants.API_KEY)
            function.invoke(Resource.loading())
            if (moviesResponse.code() == 200) {
                val movies = moviesResponse.body()!!
                if (movies.totalResults == 0) {
                    function.invoke(Resource.blank())
                    return
                }
                movies.listMovies.forEach { movie ->
                    dao.setMovie(
                        MovieEntity(
                            movie.id,
                            movie.title,
                            movie.originalTitle,
                            movie.overview,
                            movie.posterPath,
                            false
                        )
                    )
                }
                function.invoke(Resource.success(""))
            } else {
                val error = JSONObject(moviesResponse.errorBody().toString())
                function.invoke(Resource.error(error.getString("status_message")))
            }

        } else function.invoke(Resource.success(""))
    }
}