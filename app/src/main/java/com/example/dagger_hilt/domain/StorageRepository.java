package com.example.dagger_hilt.domain;

import static com.example.dagger_hilt.sys.util.Constants.PASSWORD;
import static com.example.dagger_hilt.sys.util.Constants.REGISTERED_USER;

import com.example.dagger_hilt.data.datasource.storage.Storage;

import javax.inject.Inject;

public class StorageRepository {

    @Inject
    Storage storage;
    private String userName;

    @Inject
    public StorageRepository() {
    }

    public boolean isUserRegistered() {
        if (storage != null) {
            userName = storage.getString(REGISTERED_USER);
            return !userName.isEmpty();
        } else return false;
    }

    public void registerUser(String userName, String password) {
        storage.setString(REGISTERED_USER, userName);
        storage.setString(userName + PASSWORD, password);
    }


    public boolean loginUser(String userName, String password) {
        isUserRegistered();
        if (!this.userName.equals(userName)) return false;
        var registeredPassword = storage.getString(userName + PASSWORD);
        return registeredPassword.equals(password);
    }

    public void unregister() {
        storage.setString(REGISTERED_USER, "");
        storage.setString(userName + PASSWORD, "");
    }
}
