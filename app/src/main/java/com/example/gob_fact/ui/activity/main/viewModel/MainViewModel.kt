package com.example.gob_fact.ui.activity.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.data.datasource.database.entities.MovieEntity
import com.example.gob_fact.domain.IFactRepository
import com.example.gob_fact.sys.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepositoryKts: IFactRepository
) : ViewModel() {

    val moviesList: LiveData<Resource<List<MovieEntity>>> = movieRepositoryKts.loadMovies()

    fun updateMovie(id: Int, check: Boolean) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieRepositoryKts.updateMovie(id, check)
            }
        }
}
