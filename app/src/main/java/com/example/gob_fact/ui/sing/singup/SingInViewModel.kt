package com.example.gob_fact.ui.sing.singup

import androidx.lifecycle.ViewModel
import com.example.gob_fact.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private var isBiometricEnable: Boolean
        get() = authenticationRepository.isEnableBiometric
        set(value) {
            authenticationRepository.isEnableBiometric = value
        }

    init {
        authenticationRepository.isEnableBiometric = false
    }

    fun singIn(
        userName: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = authenticationRepository.registerUser(userName, password, onSuccess, onError)

    fun loginWithEmailPasswordBiometric(
        userName: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        isBiometricEnable = true
        singIn(userName, password, {
            isBiometricEnable = true
            onSuccess()
        }, onError)
    }
}