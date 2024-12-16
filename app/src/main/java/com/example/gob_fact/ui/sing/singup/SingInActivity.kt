package com.example.gob_fact.ui.sing.singup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.gob_fact.R
import com.example.gob_fact.databinding.ActivitySingInBinding
import com.example.gob_fact.domain.GoogleAuthenticator
import com.example.gob_fact.sys.util.UtilsText.isNotBlank
import com.example.gob_fact.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class SingInActivity : AppCompatActivity() { // add firebase

    lateinit var binding: ActivitySingInBinding
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var viewModel: SingInViewModel

    @Inject
    lateinit var credentialManager: CredentialManager

    private val googleAuthenticator by lazy {
        GoogleAuthenticator(
            credentialManager = credentialManager,
            webClientId = getString(R.string.default_web_client_id)
        )
    }

    private val nonce by lazy { UUID.randomUUID().toString() }

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
        binding.signInButton.setOnClickListener { signInWithGoogle() }
        binding.buttonSingIn.setOnClickListener { handleSignInButtonClick() }
    }

    private fun handleSignInButtonClick() {
        if (binding.layoutEmail.isNotBlank() && binding.layoutPassword.isNotBlank()) {
            if (binding.checkboxEnableBiometric.isChecked) {
                setupBiometricAuthentication(
                    userName = binding.inputEmail.text.toString().trim(),
                    password = binding.inputPassword.text.toString().trim()
                )
            } else {
                viewModel.singIn(
                    userName = binding.inputEmail.text.toString().trim(),
                    password = binding.inputPassword.text.toString().trim()
                )
                navigateToMainActivity()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun signInWithGoogle() {
        lifecycleScope.launch {
            try {
                googleAuthenticator.authenticateWithGoogle(
                    context = applicationContext,
                    onResult = { idToken, displayName ->
                        if (idToken != null && displayName != null) {
                            viewModel.singInWithGoogle(idToken, displayName)
                            navigateToMainActivity()
                        } else {
                            startGoogleSignInIntent()
                        }
                    }
                )
            } catch (e: Exception) {
                showSnackBar("Google Sign-In failed: ${e.localizedMessage}")
            }
        }
    }

    private fun startGoogleSignInIntent() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { idToken ->
                    viewModel.signInWithGoogle(idToken, account.displayName)
                    navigateToMainActivity()
                }
            } catch (e: ApiException) {
                Timber.tag("GoogleSignIn").e(e, "Google sign in failed")
                showSnackBar("Google Sign-In failed")
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setOtherConfigurations() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun setupBiometricAuthentication(
        userName: String,
        password: String
    ) {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    handleBiometricAuthenticationError(errorCode, errString, userName, password)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    handleBiometricAuthenticationSuccess()
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
        errString: CharSequence,
        userName: String,
        password: String
    ) {
        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
            binding.checkboxEnableBiometric.isChecked = false
            viewModel.singIn(userName, password)
            navigateToMainActivity()
        } else {
            showSnackBar("Authentication error: $errString $errorCode")
        }
    }

    private fun handleBiometricAuthenticationSuccess() {
        viewModel.enableBiometric()
        viewModel.singIn(
            userName = binding.inputEmail.text.toString().trim(),
            password = binding.inputPassword.text.toString().trim()
        )
        navigateToMainActivity()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}