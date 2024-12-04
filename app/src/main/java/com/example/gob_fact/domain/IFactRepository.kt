package com.example.gob_fact.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.sys.util.Resource

interface IFactRepository {
    fun loadFacts(): LiveData<Resource<List<FactEntity>>>
    fun deleteFacts()
    fun getFacts(): LiveData<List<FactEntity>>
    fun getFactsPaginated(pageSize: Int, offset: Int): List<FactEntity>
    fun searchFactPaginated(query: String, pageSize: Int, offset: Int): List<FactEntity>
    fun getFact(factId: String): LiveData<FactEntity?>
    fun searchFact(query: String): LiveData<List<FactEntity>>
}