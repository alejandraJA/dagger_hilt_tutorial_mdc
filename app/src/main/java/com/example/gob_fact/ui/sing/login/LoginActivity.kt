package com.example.gob_fact.ui.sing.login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.gob_fact.databinding.ActivityLoginBinding
import com.example.gob_fact.sys.util.UtilsText.isNotBlank
import com.example.gob_fact.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setOtherConfigurations()
    }

    private fun setupUI() {
        if (viewModel.isBiometricDisabled) {
            hideBiometricUI()
        }

        binding.buttonLogin.setOnClickListener {
            authenticateWithUsernameAndPassword()
        }
        binding.imageFingerprint.setOnClickListener {
            authenticateWithFingerprint()
        }
    }

    private fun hideBiometricUI() {
        binding.imageFingerprint.visibility = View.GONE
        binding.textFingerprint.visibility = View.GONE
        binding.textOr.visibility = View.GONE
    }

    private fun authenticateWithUsernameAndPassword() {
        val widgetsIsNotBlank =
            binding.layoutEmail.isNotBlank() && binding.layoutPassword.isNotBlank()
        if (widgetsIsNotBlank) {
            val userName = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()
            viewModel.login(
                userName = userName,
                password = password,
                onSuccess = {
                    navigateToActivity(MainActivity::class.java)
                },
                onError = {
                    showSnackBar(it)
                }
            )
        }
    }

    private fun authenticateWithFingerprint() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Authentication")
            .setSubtitle("Log in using your fingerprint")
            .setNegativeButtonText("Cancel")
            .build()
        val executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt = BiometricPrompt(this, executor, object :
            BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                showSnackBar("Authentication error: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                navigateToActivity(MainActivity::class.java)
            }

            override fun onAuthenticationFailed() {
                showSnackBar("Authentication failed")
            }
        })

        biometricPrompt.authenticate(promptInfo)
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
        finish()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setOtherConfigurations() {
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}