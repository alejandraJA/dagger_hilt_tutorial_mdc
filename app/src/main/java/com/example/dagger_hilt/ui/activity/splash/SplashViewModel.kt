package com.example.dagger_hilt.ui.activity.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dagger_hilt.domain.ApiRepository
import com.example.dagger_hilt.sys.util.Constants
import com.example.dagger_hilt.sys.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    ViewModel() {
    fun loadMovies(): MutableLiveData<Constants.StatusResponse> {
        val result = MutableLiveData<Constants.StatusResponse>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                apiRepository.loadMovies { errorResource: Resource<String> ->
                    result.postValue(errorResource.status)
                }
            }
        }
        return result
    }
}