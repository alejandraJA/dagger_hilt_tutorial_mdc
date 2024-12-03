package com.example.gob_fact.ui.activity.start

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.web.repository.FactRepository
import com.example.gob_fact.sys.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val repository: FactRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteFacts()
            }
        }
    }

    private val _facts: MediatorLiveData<List<FactEntity>> =
        MediatorLiveData<List<FactEntity>>().apply {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    addSource(repository.loadFacts()) { factsResource ->
                        factsResource?.let {
                            if (factsResource.status == Constants.StatusResponse.SUCCESS)
                                postValue(factsResource.data)
                        }
                    }
                }
            }
        }
    val facts: MediatorLiveData<List<FactEntity>> get() = _facts

}