package com.example.gob_fact.ui.sing.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.domain.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val storageRepository: StorageRepository): ViewModel() {

    val isBiometricDisabled: Boolean
        get() = !storageRepository.isEnableBiometric
    val userName = MutableLiveData<Boolean>().apply {
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 postValue(storageRepository.isUserRegistered())
             }
         }
    }

    fun login(userName: String, password: String) = storageRepository.loginUser(userName, password)
}