package com.example.gob_fact.ui.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val factRepository: FactRepository
) : ViewModel() {
    fun searchFact(query: String?) {
        if (query.isNullOrEmpty()) _facts.apply {
            removeSource(_facts)
            addSource(factRepository.getFacts()) { value = it }
        } else _facts.apply {
            removeSource(_facts)
            addSource(factRepository.searchFact(query)) { value = it }
        }
    }

    private val _facts: MediatorLiveData<List<FactEntity>> = MediatorLiveData()
    val facts: LiveData<List<FactEntity>> get() = _facts
}