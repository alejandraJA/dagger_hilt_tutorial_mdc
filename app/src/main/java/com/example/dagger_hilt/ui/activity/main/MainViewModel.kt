package com.example.dagger_hilt.ui.activity.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntity
import com.example.dagger_hilt.domain.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    fun updateMovie(id: Int, check: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseRepository.updateMovie(id, check)
            }
        }
    }

    val movies = MediatorLiveData<List<MovieEntity>>().apply {
        addSource(databaseRepository.movies) {
            if (it.isNotEmpty()) value = it
        }
    }
}