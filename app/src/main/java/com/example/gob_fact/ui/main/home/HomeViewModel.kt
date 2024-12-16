package com.example.gob_fact.ui.main.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.domain.usecase.GetFactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFactsUseCase: GetFactsUseCase,
) : ViewModel() {

    private val _facts = MediatorLiveData<List<FactEntity>>()
    val facts: MutableLiveData<List<FactEntity>> get() = _facts

    init {
        loadMoreFacts(null)
    }

    fun loadMoreFacts(query: String?) {
        _facts.addSource(getFactsUseCase(query)) { factsResource ->
            factsResource?.let {
                if (factsResource.isNotEmpty())
                    _facts.postValue(factsResource)
            }
        }
    }

    fun searchFacts(query: String?) {
        _facts.addSource(getFactsUseCase()) { factsResource ->
            factsResource?.let {
                if (factsResource.isNotEmpty())
                    _facts.postValue(factsResource)
            }
        }
    }

}