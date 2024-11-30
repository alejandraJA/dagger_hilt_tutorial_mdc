package com.example.gob_fact.domain

import com.example.gob_fact.data.datasource.storage.Storage
import com.example.gob_fact.sys.util.Constants.PASSWORD
import com.example.gob_fact.sys.util.Constants.REGISTERED_USER
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
        return registeredPassword == password
    }

    fun unregister() {
        storage.setString(REGISTERED_USER, "")
        storage.setString("$userName$PASSWORD", "")
    }

}