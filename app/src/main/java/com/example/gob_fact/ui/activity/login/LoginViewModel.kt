package com.example.gob_fact.ui.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gob_fact.domain.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val storageRepository: StorageRepository): ViewModel() {

    val userName = MutableLiveData<Boolean>().apply {
        value = storageRepository.isUserRegistered()
    }

    fun login(userName: String, password: String) = storageRepository.loginUser(userName, password)
}