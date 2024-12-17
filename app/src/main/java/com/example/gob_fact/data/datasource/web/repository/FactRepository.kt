package com.example.gob_fact.data.datasource.web.repository

import androidx.lifecycle.asFlow
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

class FactRepository (
    private val dao: FactDao,
    private val service: FactService,
    private val appExecutor: AppExecutors
) : IFactRepository {

    override fun loadFacts(): Flow<Resource<List<FactEntity>>> =
        object : NetworkBoundResourceWithFlow<List<FactEntity>, GobFactsResponse>(appExecutor) {
            override fun saveCallResult(response: GobFactsResponse) =
                dao.insertFacts(mapGobFactsResponseToEntities(response))

            override fun shouldFetch(data: List<FactEntity>?): Boolean =
                data!!.isEmpty()

            override fun loadFromDb(): Flow<List<FactEntity>> =
                dao.getFacts()

            override fun createCall(): Flow<ApiResponse<GobFactsResponse>> =
                service.loadFacts().asFlow()

        }.asFlow()

    override fun deleteFacts() = dao.deleteFacts()

    override fun getFacts(): Flow<List<FactEntity>> = dao.getFacts()
    override fun getFactsPaginated(pageSize: Int, offset: Int): List<FactEntity> =
        dao.getFactsPaginated(pageSize, offset)

    override fun searchFactPaginated(query: String, pageSize: Int, offset: Int): List<FactEntity> =
        dao.searchFactPaginated(query, pageSize, offset)

    override fun getFact(factId: String): Flow<FactEntity?> = dao.getFact(factId)
    override fun searchFact(query: String): Flow<List<FactEntity>> = dao.searchFact(query)

}