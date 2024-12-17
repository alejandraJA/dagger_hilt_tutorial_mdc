package com.example.gob_fact.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.domain.usecase.LoadFactsUseCase
import com.example.gob_fact.sys.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val loadFactsUseCase: LoadFactsUseCase,
) : ViewModel() {

    private val _facts: MutableStateFlow<List<FactEntity>> =
        MutableStateFlow<List<FactEntity>>(emptyList()).apply {
            viewModelScope.launch {
                loadFactsUseCase().let { factsResource ->
                    factsResource.let {
                        factsResource.collect { facts ->
                            if (facts.status == Constants.StatusResponse.SUCCESS)
                                value = facts.data ?: emptyList()
                            if (facts.status == Constants.StatusResponse.ERROR)
                                value = emptyList()
                        }
                    }
                }
            }
        }

    val facts: Flow<List<FactEntity>> get() = _facts
}