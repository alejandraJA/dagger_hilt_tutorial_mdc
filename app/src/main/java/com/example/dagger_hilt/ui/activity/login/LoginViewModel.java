package com.example.dagger_hilt.ui.activity.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dagger_hilt.domain.StorageRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    @Inject
    StorageRepository storageRepository;
    private MutableLiveData<Boolean> registerUser;

    @Inject
    public LoginViewModel() {
    }

    public MutableLiveData<Boolean> isRegistered() {
        if (registerUser == null) {
            var isRegisterUser = storageRepository.isUserRegistered();
            registerUser = new MutableLiveData<>();
            registerUser.postValue(isRegisterUser);
        }
        return registerUser;
    }

    public boolean login(String userName, String password) {
        return storageRepository.loginUser(userName, password);
    }
}
