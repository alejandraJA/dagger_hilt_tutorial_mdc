package com.example.dagger_hilt.ui.activity.singup

import androidx.lifecycle.ViewModel
import com.example.dagger_hilt.domain.StorageRepositoryKts
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingInViewModelKts @Inject constructor(private val storageRepositoryKts: StorageRepositoryKts) :
    ViewModel() {
    fun singIn(userName: String, password: String) = storageRepositoryKts.registerUser(userName, password)
}