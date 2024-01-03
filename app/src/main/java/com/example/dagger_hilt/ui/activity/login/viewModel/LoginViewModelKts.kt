package com.example.dagger_hilt.ui.activity.login.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dagger_hilt.domain.StorageRepositoryKts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModelKts @Inject constructor(private val storageRepositoryKts: StorageRepositoryKts): ViewModel() {

    val userName = MutableLiveData<Boolean>().apply {
        value = storageRepositoryKts.isUserRegistered()
    }

    fun login(userName: String, password: String) = storageRepositoryKts.loginUser(userName, password)
}