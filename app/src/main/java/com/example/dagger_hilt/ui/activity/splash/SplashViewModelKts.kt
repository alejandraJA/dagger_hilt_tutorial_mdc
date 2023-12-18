package com.example.dagger_hilt.ui.activity.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dagger_hilt.domain.ApiRepositoryKts
import com.example.dagger_hilt.sys.util.ConstantsKts
import com.example.dagger_hilt.sys.util.ResourceKts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModelKts @Inject constructor(private val apiRepositoryKts: ApiRepositoryKts) :
    ViewModel() {
    fun loadMovies(): MutableLiveData<ConstantsKts.StatusResponse> {
        val result = MutableLiveData<ConstantsKts.StatusResponse>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                apiRepositoryKts.loadMovies { errorResourceKts: ResourceKts<String> ->
                    result.postValue(errorResourceKts.status)
                }
            }
        }
        return result
    }
}