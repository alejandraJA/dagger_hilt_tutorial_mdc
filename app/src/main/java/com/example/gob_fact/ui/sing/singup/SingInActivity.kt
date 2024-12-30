package com.example.gob_fact.ui.sing.singup

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModelProvider
import com.example.gob_fact.R
import com.example.gob_fact.databinding.ActivitySingInBinding
import com.example.gob_fact.sys.util.UtilsText.isNotBlank
import com.example.gob_fact.ui.main.MainActivity
import com.example.gob_fact.ui.sing.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SingInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingInBinding
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var viewModel: SingInViewModel
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SingInViewModel::class.java]
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setOtherConfigurations()
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun setupUI() {
        binding.doYouHaveAnAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.signInButton.setOnClickListener {
            signInWithGoogle()
        }
        binding.buttonSingIn.setOnClickListener { handleSignInButtonClick() }
    }

    private fun signInWithGoogle() {
        // Configura Google Sign-In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id)) // Client ID en google-services.json
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Inicia el flujo de inicio de sesión
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Google sign-in failed", e)
                showSnackBar("Google sign-in failed: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    showSnackBar("Authentication successful!!")
                } else {
                    // Fallo en la autenticación
                    showSnackBar("Authentication failed: ${task.exception?.message}")
                }
            }
    }

    private fun handleSignInButtonClick() {
        if (!(binding.layoutEmail.isNotBlank() && binding.layoutPassword.isNotBlank()))
            return
        if (binding.checkboxEnableBiometric.isChecked) setupBiometricAuthentication()
        else singIn()
    }

    private fun singIn() {
        viewModel.singIn(
            userName = binding.inputEmail.text.toString().trim(),
            password = binding.inputPassword.text.toString().trim(),
            onSuccess = {
                navigateToMainActivity()
            },
            onError = {
                showSnackBar(it)
            }
        )
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setOtherConfigurations() {
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun setupBiometricAuthentication() {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    handleBiometricAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    singInWithBiometricUserAndPassword()
                }

                override fun onAuthenticationFailed() {
                    showSnackBar("Authentication failed")
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun handleBiometricAuthenticationError(
        errorCode: Int,
        errString: CharSequence
    ) {
        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
            binding.checkboxEnableBiometric.isChecked = false
            singIn()
        } else {
            showSnackBar("Authentication error: $errString $errorCode")
        }
    }

    private fun singInWithBiometricUserAndPassword() {
        val userName = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()
        viewModel.loginWithEmailPasswordBiometric(userName, password, {
            navigateToMainActivity()
        }, {
            showSnackBar(it)
        })
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}