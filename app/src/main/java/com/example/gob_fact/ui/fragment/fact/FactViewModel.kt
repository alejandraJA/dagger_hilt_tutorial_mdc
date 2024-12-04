package com.example.gob_fact.ui.fragment.fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: FactRepository
): ViewModel() {

    private var _factId: String = ""

    var factId: String
        get() = _factId
        set(value) {
            _factId = value
        }

    private val _fact: MediatorLiveData<FactEntity?> = MediatorLiveData()
    val fact: LiveData<FactEntity?> get() = _fact

    fun loadFact() {
        _fact.addSource(factRepository.getFact(factId)) {
            _fact.value = it
        }
    }

}