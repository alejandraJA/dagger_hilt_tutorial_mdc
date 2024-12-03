package com.example.gob_fact.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.data.datasource.database.entities.MovieEntity
import com.example.gob_fact.domain.IFactRepository
import com.example.gob_fact.sys.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    factRepository: IFactRepository
) : ViewModel() {

    private val _facts: LiveData<List<FactEntity>> = factRepository.getFacts()
    val facts: LiveData<List<FactEntity>> get() = _facts

}
