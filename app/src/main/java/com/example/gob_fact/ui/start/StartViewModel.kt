package com.example.gob_fact.ui.start

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.domain.IFactRepository
import com.example.gob_fact.sys.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val repository: IFactRepository
) : ViewModel() {

    private val _facts: MediatorLiveData<List<FactEntity>> =
        MediatorLiveData<List<FactEntity>>().apply {
            viewModelScope.launch { // Investigar sobre viewModelScope
                addSource(repository.loadFacts()) { factsResource ->
                    factsResource?.let {
                        if (factsResource.status == Constants.StatusResponse.SUCCESS)
                            postValue(factsResource.data)
                    }
                }
            }
        }
    val facts: MediatorLiveData<List<FactEntity>> get() = _facts

}