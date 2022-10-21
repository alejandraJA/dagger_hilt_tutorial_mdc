package com.example.dagger_hilt.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dagger_hilt.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val userName = MutableLiveData<Boolean>().apply {
        value = userRepository.isUserRegistered()
    }

    fun login(userName: String, password: String) = userRepository.loginUser(userName, password)
}