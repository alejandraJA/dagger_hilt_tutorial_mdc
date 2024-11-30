package com.example.gob_fact.ui.activity.singup.viewModel

import androidx.lifecycle.ViewModel
import com.example.gob_fact.domain.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(private val storageRepository: StorageRepository) :
    ViewModel() {
    fun singIn(userName: String, password: String) = storageRepository.registerUser(userName, password)
}