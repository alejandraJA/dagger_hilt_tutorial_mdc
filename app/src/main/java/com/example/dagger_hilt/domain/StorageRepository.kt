package com.example.dagger_hilt.domain

import com.example.dagger_hilt.data.datasource.storage.Storage
import com.example.dagger_hilt.sys.util.Constants.PASSWORD
import com.example.dagger_hilt.sys.util.Constants.REGISTERED_USER
import javax.inject.Inject

class StorageRepository @Inject constructor(private val storage: Storage) {

    private val userName: String
        get() = storage.getString(REGISTERED_USER)

    fun registerUser(userName: String, password: String) {
        storage.setString(REGISTERED_USER, userName)
        storage.setString("$userName$PASSWORD", password)
    }

    fun isUserRegistered() = userName.isNotEmpty()

    fun loginUser(userName: String, password: String): Boolean {
        val registeredUser = this.userName
        if (registeredUser != userName) return false
        val registeredPassword = storage.getString("$userName$PASSWORD")
        if (registeredPassword != password) return false
        return true
    }

    fun unregister() {
        storage.setString(REGISTERED_USER, "")
        storage.setString("$userName$PASSWORD", "")
    }

}