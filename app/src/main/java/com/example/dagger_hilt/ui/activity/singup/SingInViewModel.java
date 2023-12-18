package com.example.dagger_hilt.ui.activity.singup;

import androidx.lifecycle.ViewModel;

import com.example.dagger_hilt.domain.StorageRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SingInViewModel extends ViewModel {
    private final StorageRepository storageRepository;

    @Inject
    public SingInViewModel(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public void singIn(String userName, String password) {
        storageRepository.registerUser(userName, password);
    }
}