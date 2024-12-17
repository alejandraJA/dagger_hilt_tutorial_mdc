package com.example.gob_fact.ui.main.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.domain.usecase.GetFactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getFactUseCase: GetFactUseCase
) : ViewModel() {

    private var _factId: String = ""
    var factId: String
        get() = _factId
        set(value) {
            loadFact()
            _factId = value
        }

    private val _fact: MutableStateFlow<FactEntity?> = MutableStateFlow(null)
    val fact: Flow<FactEntity?> get() = _fact

    fun loadFact() {
        _fact.apply {
            viewModelScope.launch {
                getFactUseCase(_factId).let { factsResource ->
                    factsResource.collect { facts ->
                        value = facts ?: null
                    }
                }
            }
        }
    }

}
