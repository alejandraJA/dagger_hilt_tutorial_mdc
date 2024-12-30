package com.example.gob_fact.ui.sing.singup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingInBinding
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var viewModel: SingInViewModel
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                handleGoogleSignInResult(data)
            } else {
                showSnackBar("Google sign-in canceled")
            }
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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            viewModel.firebaseAuthWithGoogle(account, {
                navigateToMainActivity()
            }, { error ->
                showSnackBar(error)
            })
        } catch (e: ApiException) {
            showSnackBar("Google sign-in failed: ${e.message}")
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