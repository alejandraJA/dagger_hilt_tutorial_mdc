package com.example.dagger_hilt.domain

import com.example.dagger_hilt.data.datasource.storage.SharedPreferenceStorage
import com.example.dagger_hilt.sys.util.Constants.PASSWORD
import com.example.dagger_hilt.sys.util.Constants.REGISTERED_USER
import javax.inject.Inject

class UserRepository @Inject constructor(private val sharedPreferenceStorage: SharedPreferenceStorage) {

    val userName: String
        get() = sharedPreferenceStorage.getString(REGISTERED_USER)

    fun registerUser(userName: String, password: String) {
        sharedPreferenceStorage.setString(REGISTERED_USER, userName)
        sharedPreferenceStorage.setString("$userName$PASSWORD", password)
    }

    fun isUserRegistered() = userName.isNotEmpty()

    fun loginUser(userName: String, password: String): Boolean {
        val registeredUser = this.userName
        if (registeredUser != userName) return false
        val registeredPassword = sharedPreferenceStorage.getString("$userName$PASSWORD")
        if (registeredPassword != password) return false
        return true
    }

    fun unregister() {
        sharedPreferenceStorage.setString(REGISTERED_USER, "")
        sharedPreferenceStorage.setString("$userName$PASSWORD", "")
    }

}