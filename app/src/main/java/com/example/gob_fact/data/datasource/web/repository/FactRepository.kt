package com.example.gob_fact.data.datasource.web.repository

import androidx.lifecycle.asFlow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gob_fact.data.datasource.database.dao.FactDao
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.api.FactService
import com.example.gob_fact.data.datasource.web.models.response.ApiResponse
import com.example.gob_fact.data.datasource.web.models.response.GobFactsResponse
import com.example.gob_fact.data.datasource.web.repository.FactMapper.mapGobFactsResponseToEntities
import com.example.gob_fact.domain.NetworkBoundResourceWithFlow
import com.example.gob_fact.domain.repository.IFactRepository
import com.example.gob_fact.sys.util.AppExecutors
import com.example.gob_fact.sys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FactRepository(
    private val dao: FactDao,
    private val service: FactService,
    private val appExecutor: AppExecutors
) : IFactRepository {

    override fun loadFacts(loadSize: Int, page: Int): Flow<Resource<List<FactEntity>>> =
        object : NetworkBoundResourceWithFlow<List<FactEntity>, GobFactsResponse>(appExecutor) {
            override fun saveCallResult(response: GobFactsResponse) {
                if (response.facts.isNotEmpty()) {
                    dao.clear()
                    dao.insertFacts(mapGobFactsResponseToEntities(response))
                }
            }

            override fun shouldFetch(data: List<FactEntity>?): Boolean =
                true

            override fun loadFromDb(): Flow<List<FactEntity>> =
                dao.getFacts()

            override fun createCall(): Flow<ApiResponse<GobFactsResponse>> =
                service.loadFacts(page, loadSize).asFlow()

        }.asFlow()

    override fun loadFacts(): Flow<Resource<List<FactEntity>>> =
        object : NetworkBoundResourceWithFlow<List<FactEntity>, GobFactsResponse>(appExecutor) {
            override fun saveCallResult(response: GobFactsResponse) =
                dao.insertFacts(mapGobFactsResponseToEntities(response))

            override fun shouldFetch(data: List<FactEntity>?): Boolean =
                true

            override fun loadFromDb(): Flow<List<FactEntity>> =
                dao.getFacts()

            override fun createCall(): Flow<ApiResponse<GobFactsResponse>> =
                service.loadFacts().asFlow()

        }.asFlow()

    override fun getFact(factId: String): Flow<FactEntity?> = dao.getFact(factId)

    override fun loadFactsPaging(organization: String): Flow<PagingData<FactEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { FactPagingSource(this, dao, organization) }
        ).flow
    }

    override fun countFacts(organization: String): Flow<Int> {
        val facts = if (organization.isEmpty()) dao.getAllFacts()
        else dao.getAllFacts(organization)
        return flow { emit(facts!!.size) }
    }

}

