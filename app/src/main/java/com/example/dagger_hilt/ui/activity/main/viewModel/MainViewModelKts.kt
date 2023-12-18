package com.example.dagger_hilt.ui.activity.main.viewModel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts
import com.example.dagger_hilt.domain.DatabaseRepositoryKts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModelKts @Inject constructor(
    private val databaseRepositoryKts: DatabaseRepositoryKts
) : ViewModel() {

    fun updateMovie(id: Int, check: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseRepositoryKts.updateMovie(id, check)
            }
        }
    }

    val movies = MediatorLiveData<List<MovieEntityKts>>().apply {
        addSource(databaseRepositoryKts.movies) {
            if (it.isNotEmpty()) value = it
        }
    }
}