package com.example.gob_fact.ui.activity.login

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
import com.example.gob_fact.ui.activity.main.MainActivity
import com.example.gob_fact.ui.activity.singup.SingInActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (viewModel.isBiometricDisabled) {
            binding.imageFingerprint.visibility = View.GONE
            binding.textFingerprint.visibility = View.GONE
            binding.textOr.visibility = View.GONE
        }

        viewModel.userName.observe(this) {
            if (!it || FirebaseAuth.getInstance().currentUser == null) {
                startActivity(Intent(this, SingInActivity::class.java))
                finish()
            }
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.buttonLogin.setOnClickListener {
            authenticateWithUsernameAndPassword(binding, viewModel)
        }
        binding.imageFingerprint.setOnClickListener {
            authenticateWithFingerprint()
        }
        setOtherConfigurations()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setOtherConfigurations() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun authenticateWithUsernameAndPassword(
        binding: ActivityLoginBinding,
        viewModel: LoginViewModel
    ) {
        if (binding.layoutEmail.isNotBlank() && binding.layoutPassword.isNotBlank())
            if (viewModel.login(
                    userName = binding.inputEmail.text.toString().trim(),
                    password = binding.inputPassword.text.toString().trim()
                )
            ) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
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
                super.onAuthenticationError(errorCode, errString)
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Authentication error: $errString", Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Authentication failed", Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        biometricPrompt.authenticate(promptInfo)
    }

}