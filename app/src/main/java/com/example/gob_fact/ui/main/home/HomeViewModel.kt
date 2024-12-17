package com.example.gob_fact.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.domain.usecase.GetFactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFactsUseCase: GetFactsUseCase,
) : ViewModel() {


    private val _facts: MutableStateFlow<List<FactEntity>> =
        MutableStateFlow(emptyList())

    val facts: Flow<List<FactEntity>> get() = _facts

    init {
        searchFacts(null)
    }

    fun searchFacts(query: String?) {
        _facts.apply {
            viewModelScope.launch {
                getFactsUseCase(query).let { factsResource ->
                    factsResource.collect { facts ->
                        value = facts
                    }
                }
            }
        }
    }

}