package com.example.dagger_hilt.ui.activity.singup

import androidx.lifecycle.ViewModel
import com.example.dagger_hilt.domain.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(private val storageRepository: StorageRepository) :
    ViewModel() {
    fun singIn(userName: String, password: String) = storageRepository.registerUser(userName, password)
}