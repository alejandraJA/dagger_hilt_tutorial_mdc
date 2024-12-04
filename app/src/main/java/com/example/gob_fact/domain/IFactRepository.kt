package com.example.gob_fact.domain

import androidx.lifecycle.LiveData
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.sys.util.Resource

interface IFactRepository {
    fun loadFacts(): LiveData<Resource<List<FactEntity>>>
    fun deleteFacts()
    fun getFacts(): LiveData<List<FactEntity>>
    fun getFact(factId: String): LiveData<FactEntity?>
    fun searchFact(query: String): LiveData<List<FactEntity>>
}