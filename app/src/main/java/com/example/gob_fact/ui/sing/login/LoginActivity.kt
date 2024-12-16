package com.example.gob_fact.ui.sing.login

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.gob_fact.databinding.ActivityLoginBinding
import com.example.gob_fact.sys.util.UtilsText.isNotBlank
import com.example.gob_fact.ui.main.MainActivity
import com.example.gob_fact.ui.sing.singup.SingInActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
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
        observeViewModel()
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

    private fun observeViewModel() {
        viewModel.userName.observe(this) {
            if (!it) {
                navigateToActivity(SingInActivity::class.java)
            } else if (FirebaseAuth.getInstance().currentUser != null) {
                navigateToActivity(MainActivity::class.java)
            }
        }
    }

    private fun authenticateWithUsernameAndPassword() {
        if (binding.layoutEmail.isNotBlank() && binding.layoutPassword.isNotBlank()) {
            if (viewModel.login(
                    userName = binding.inputEmail.text.toString().trim(),
                    password = binding.inputPassword.text.toString().trim()
                )
            ) {
                navigateToActivity(MainActivity::class.java)
            }
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}