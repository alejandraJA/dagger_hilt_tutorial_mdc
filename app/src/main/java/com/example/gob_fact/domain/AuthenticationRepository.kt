package com.example.gob_fact.domain

import com.example.gob_fact.data.datasource.storage.IStorage
import com.example.gob_fact.sys.util.Constants.BIOMETRIC
import com.example.gob_fact.sys.util.Constants.PASSWORD
import com.example.gob_fact.sys.util.Constants.REGISTERED_USER
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val storage: IStorage) {

    private val auth = FirebaseAuth.getInstance()

    private var userName: String
        get() = storage.getString(REGISTERED_USER)
        set(value) = storage.setString(REGISTERED_USER, value)

    private var password: String
        get() = storage.getString("$userName$PASSWORD")
        set(value) = storage.setString("$userName$PASSWORD", value)

    var isEnableBiometric: Boolean
        get() = storage.getBoolean(BIOMETRIC, true)
        set(value) {
            storage.setBoolean(BIOMETRIC, value)
            storage.getBoolean(BIOMETRIC, value)
        }


    fun registerUser(
        userName: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener {
            if (it.isSuccessful) {
                // Next task: encrypt password and user name
                // and save them in storage
                storage.setString(REGISTERED_USER, userName)
                storage.setString("$userName$PASSWORD", password)
                onSuccess()
            } else {
                onError(it.exception?.message ?: "Authentication failed")
            }
        }
    }

    fun isUserRegistered() = userName.isNotEmpty()

    fun loginUser(
        userName: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.currentUser?.let {
            val registeredUser = this.userName
            if (registeredUser != userName)
                onError("User not registered")
            val registeredPassword = storage.getString("$userName$PASSWORD")
            if (registeredPassword == password)
                onSuccess()
            else
                onError("Incorrect password")
        }
        auth.signInWithEmailAndPassword(userName, password).addOnCompleteListener {
            if (it.isSuccessful) {
                this.userName = userName
                this.password = password
                onSuccess()
            } else {
                onError(it.exception?.message ?: "Authentication failed")
            }
        }
    }

    fun registerUserWithGoogle(
        account: GoogleSignInAccount,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val userName = account.displayName ?: ""
                this.userName = userName
                onSuccess()
            } else {
                onError(it.exception?.message ?: "Authentication failed")
            }
        }
    }


}