package com.example.gob_fact.ui.sing.login

import androidx.lifecycle.ViewModel
import com.example.gob_fact.domain.AuthenticationRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository): ViewModel() {

    val isBiometricDisabled: Boolean
        get() = !authenticationRepository.isEnableBiometric

    fun login(
        userName: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = authenticationRepository.loginUser(userName, password, onSuccess, onError)

    fun firebaseAuthWithGoogle(
        account: GoogleSignInAccount,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = authenticationRepository.registerUserWithGoogle(account, onSuccess, onError)

}