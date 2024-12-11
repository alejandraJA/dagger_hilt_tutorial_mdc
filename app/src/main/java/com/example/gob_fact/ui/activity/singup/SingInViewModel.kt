package com.example.gob_fact.ui.activity.singup

import androidx.lifecycle.ViewModel
import com.example.gob_fact.domain.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(private val storageRepository: StorageRepository) :
    ViewModel() {

    init {
        storageRepository.isEnableBiometric = false
    }

    fun singIn(userName: String, password: String) = storageRepository.registerUser(userName, password)
    fun enableBiometric() {
        storageRepository.isEnableBiometric = true
    }

    fun singInWithGoogle(idToken: String, displayName: String) {
        storageRepository.registerUser(displayName, idToken)
    }

    fun signInWithGoogle(idToken: String, displayName: String?) {
        storageRepository.registerUser(displayName ?: "User", idToken)
    }
}