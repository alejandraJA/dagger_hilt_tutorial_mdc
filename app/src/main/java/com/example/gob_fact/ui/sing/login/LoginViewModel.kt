package com.example.gob_fact.ui.sing.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gob_fact.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository): ViewModel() {

    val isBiometricDisabled: Boolean
        get() = !authenticationRepository.isEnableBiometric
    val userName = MutableLiveData<Boolean>().apply {
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 postValue(authenticationRepository.isUserRegistered())
             }
         }
    }

    fun login(
        userName: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = authenticationRepository.loginUser(userName, password, onSuccess, onError)

}