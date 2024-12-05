package com.example.gob_fact.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gob_fact.data.datasource.database.dao.FactDao
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.utils.toLiveData

class FakeFactDao : FactDao {

    private val facts = mutableListOf<FactEntity>()

    override fun setFact(factEntity: FactEntity) {
        facts.add(factEntity)
    }

    override fun getFacts(): LiveData<List<FactEntity>> = facts.toLiveData()

    override fun deleteFacts() {
        facts.clear()
    }

    override fun getFact(factId: String): LiveData<FactEntity?> =
        facts.find { it.id == factId }?.toLiveData() ?: MutableLiveData(null)

    override fun searchFact(query: String): LiveData<List<FactEntity>> {
        return facts.filter {
            it.organization.contains(query, ignoreCase = true)
        }.toLiveData()
    }

    override fun searchFactPaginated(
        query: String,
        pageSize: Int,
        offset: Int
    ): List<FactEntity> {
        return facts.filter {
            it.organization.contains(query, ignoreCase = true)
        }.drop(offset).take(pageSize)
    }

    override fun getFactsPaginated(pageSize: Int, offset: Int): List<FactEntity> {
        return facts.drop(offset).take(pageSize)
    }

}