package com.example.gob_fact.domain

import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import timber.log.Timber

class GoogleAuthenticator(
    private val credentialManager: CredentialManager,
    private val webClientId: String
) {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend fun authenticateWithGoogle(
        context: Context,
        onResult: (String?, String?) -> Unit
    ) {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            when (val credential = result.credential) {
                is PublicKeyCredential -> processPublicKeyCredential(credential, onResult)
                is PasswordCredential -> onResult(credential.id, credential.password)
                is CustomCredential -> processCustomCredential(credential, onResult)
                else -> {
                    Timber.tag("GoogleAuth").e("Unrecognized credential type")
                    onResult(null, null)
                }
            }
        } catch (e: GetCredentialException) {
            when (e.type) {
                GetCredentialException.TYPE_NO_CREDENTIAL -> {
                    Timber.tag("GoogleAuth").w("No saved credentials, triggering manual sign-in")
                    onResult(null, null)
                }
                else -> {
                    Timber.tag("GoogleAuth").e(e, "Credential retrieval failed")
                    onResult(null, null)
                }
            }
        } catch (e: Exception) {
            Timber.tag("GoogleAuth").e(e, "Authentication failed")
            onResult(null, null)
        }
    }

    private fun processPublicKeyCredential(
        credential: PublicKeyCredential,
        onResult: (String?, String?) -> Unit
    ) {
        credential.authenticationResponseJson.let { type ->
            if (type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_SIWG_CREDENTIAL) {
                try {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    onResult(
                        googleIdTokenCredential.idToken,
                        googleIdTokenCredential.displayName
                    )
                } catch (e: Exception) {
                    Timber.tag("GoogleAuth").e(e, "Public Key Credential processing failed")
                    onResult(null, null)
                }
            } else {
                Timber.tag("GoogleAuth").e("Unrecognized Public Key Credential type")
                onResult(null, null)
            }
        }
    }

    private fun processCustomCredential(
        credential: CustomCredential,
        onResult: (String?, String?) -> Unit
    ) {
        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_SIWG_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                onResult(
                    googleIdTokenCredential.idToken,
                    googleIdTokenCredential.displayName
                )
            } catch (e: Exception) {
                Timber.tag("GoogleAuth").e(e, "Custom Credential processing failed")
                onResult(null, null)
            }
        } else {
            Timber.tag("GoogleAuth").e("Unrecognized Custom Credential type")
            onResult(null, null)
        }
    }
}