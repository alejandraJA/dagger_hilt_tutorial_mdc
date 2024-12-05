package com.example.gob_fact.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val factRepository: FactRepository
) : ViewModel() {

    private val _facts = MutableLiveData<List<FactEntity>>()
    val facts: MutableLiveData<List<FactEntity>> get() = _facts

    private var currentPage = 0
    private val pageSize = 10
    private var searchQuery: String? = null

    init {
        loadMoreFacts(null)
    }

    fun loadMoreFacts(query: String?) {
        searchQuery = query ?: searchQuery
        viewModelScope.launch {
            val newFacts = fetchFacts()
            if (newFacts.isNotEmpty()) {
                updateFacts(newFacts)
                currentPage++
            }
        }
    }

    fun searchFacts(query: String) {
        currentPage = 0
        loadMoreFacts(query)
    }

    private suspend fun fetchFacts(): List<FactEntity> {
        val offset = currentPage * pageSize
        return withContext(Dispatchers.IO) {
            searchQuery?.let {
                factRepository.searchFactPaginated(it, pageSize, offset)
            } ?: factRepository.getFactsPaginated(pageSize, offset)
        }
    }

    private fun updateFacts(newFacts: List<FactEntity>) {
        val currentList = _facts.value.orEmpty().toMutableList()
        if (currentPage == 0) {
            _facts.postValue(newFacts)
        } else {
            currentList.addAll(newFacts)
            _facts.postValue(currentList)
        }
    }
}