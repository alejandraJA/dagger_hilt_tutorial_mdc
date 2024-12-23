package com.example.gob_fact.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.domain.usecase.CountFactsUseCase
import com.example.gob_fact.domain.usecase.LoadFactsPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadFactsPagingUseCase: LoadFactsPagingUseCase,
    countFactsUseCase: CountFactsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    var searchQuery: String = ""
        set(value) {
            field = value
            _searchQuery.value = value.uppercase().trim()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _facts: Flow<PagingData<FactEntity>> = _searchQuery
        .flatMapLatest { query ->
            loadFactsPagingUseCase(query)
        }.cachedIn(viewModelScope)
    val facts: Flow<PagingData<FactEntity>>
        get() = _facts

    private val _isEmptyFacts: Flow<Int> = countFactsUseCase()
    val isEmptyFacts: Flow<Int>
        get() = _isEmptyFacts

}