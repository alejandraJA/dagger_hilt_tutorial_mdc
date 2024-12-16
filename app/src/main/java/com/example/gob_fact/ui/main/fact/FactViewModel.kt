package com.example.gob_fact.ui.main.fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.domain.usecase.GetFactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getFactUseCase: GetFactUseCase
) : ViewModel() {

    private var _factId: String = ""

    var factId: String
        get() = _factId
        set(value) {
            _factId = value
        }

    private val _fact: MediatorLiveData<FactEntity?> = MediatorLiveData()
    val fact: LiveData<FactEntity?> get() = _fact

    fun loadFact() {
        _fact.addSource(getFactUseCase(factId)) {
            _fact.value = it
        }
    }

}

// Deshacer filtrado al volver o algo asi