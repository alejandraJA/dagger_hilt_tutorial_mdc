package com.example.dagger_hilt.ui.activity.singup

import androidx.lifecycle.ViewModel
import com.example.dagger_hilt.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    fun singIn(userName: String, password: String) = userRepository.registerUser(userName, password)
}