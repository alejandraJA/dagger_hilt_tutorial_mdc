package com.example.gob_fact.data.datasource.web.repository

import androidx.lifecycle.LiveData
import com.example.gob_fact.data.datasource.database.dao.FactDao
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.database.entities.MovieEntity
import com.example.gob_fact.data.datasource.web.api.FactService
import com.example.gob_fact.data.datasource.web.models.MoviesResponse
import com.example.gob_fact.data.datasource.web.models.response.ApiResponse
import com.example.gob_fact.data.datasource.web.models.response.GobFactsResponse
import com.example.gob_fact.domain.IFactRepository
import com.example.gob_fact.domain.NetworkBoundResource
import com.example.gob_fact.sys.util.AppExecutors
import com.example.gob_fact.sys.util.Constants
import com.example.gob_fact.sys.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactRepository @Inject constructor(
    private val dao: FactDao,
    private val service: FactService,
    private val appExecutor: AppExecutors
) : IFactRepository {
    override fun loadMovies(): LiveData<Resource<List<MovieEntity>>> =
        object : NetworkBoundResource<List<MovieEntity>, MoviesResponse>(appExecutor) {
            override fun saveCallResult(response: MoviesResponse) =
                response.listMovies.forEach { movie ->
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

            override fun shouldFetch(data: List<MovieEntity>?): Boolean = data.isNullOrEmpty()

            override fun loadFromDb(): LiveData<List<MovieEntity>> = dao.getMovies()

            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> =
                service.loadMovies(Constants.API_KEY)
        }.asLiveData()

    override fun updateMovie(id: Int, check: Boolean) = dao.updateMovie(id, check)

    override fun loadFacts(): LiveData<Resource<List<FactEntity>>> =
        object : NetworkBoundResource<List<FactEntity>, GobFactsResponse>(appExecutor) {
            override fun saveCallResult(response: GobFactsResponse) {
                response.facts.forEach {
                    dao.setFact(
                        FactEntity(
                            id = it.id,
                            columns = it.columns,
                            createdAt = it.createdAt,
                            dataset = it.dataset,
                            dateInsert = it.dateInsert,
                            fact = it.fact,
                            operations = it.operations,
                            organization = it.organization,
                            resource = it.resource,
                            slug = it.slug,
                            url = it.url
                        )
                    )
                }
            }

            override fun shouldFetch(data: List<FactEntity>?): Boolean =
                data!!.isEmpty()


            override fun loadFromDb(): LiveData<List<FactEntity>> =
                dao.getFacts()

            override fun createCall(): LiveData<ApiResponse<GobFactsResponse>> =
                service.loadFacts()

        }.asLiveData()


}