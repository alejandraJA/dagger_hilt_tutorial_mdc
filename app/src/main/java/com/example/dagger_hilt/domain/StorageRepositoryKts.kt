package com.example.dagger_hilt.domain

import com.example.dagger_hilt.data.datasource.storage.StorageKts
import com.example.dagger_hilt.sys.util.ConstantsKts.PASSWORD
import com.example.dagger_hilt.sys.util.ConstantsKts.REGISTERED_USER
import javax.inject.Inject

class StorageRepositoryKts @Inject constructor(private val storageKts: StorageKts) {

    private val userName: String
        get() = storageKts.getString(REGISTERED_USER)

    fun registerUser(userName: String, password: String) {
        storageKts.setString(REGISTERED_USER, userName)
        storageKts.setString("$userName$PASSWORD", password)
    }

    fun isUserRegistered() = userName.isNotEmpty()

    fun loginUser(userName: String, password: String): Boolean {
        val registeredUser = this.userName
        if (registeredUser != userName) return false
        val registeredPassword = storageKts.getString("$userName$PASSWORD")
        return registeredPassword == password
    }

    fun unregister() {
        storageKts.setString(REGISTERED_USER, "")
        storageKts.setString("$userName$PASSWORD", "")
    }

}