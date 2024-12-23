package com.example.gob_fact.domain.repository

import androidx.paging.PagingData
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.sys.util.Resource
import kotlinx.coroutines.flow.Flow

interface IFactRepository {
    fun loadFacts(loadSize: Int, page: Int): Flow<Resource<List<FactEntity>>>
    fun loadFacts(): Flow<Resource<List<FactEntity>>>
    fun getFact(factId: String): Flow<FactEntity?>
    fun loadFactsPaging(organization: String): Flow<PagingData<FactEntity>>
    fun countFacts(): Flow<Int>
}