package com.example.gob_fact.ui.activity.singup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.gob_fact.R
import com.example.gob_fact.databinding.ActivitySingInBinding
import com.example.gob_fact.sys.util.UtilsText.isNotBlank
import com.example.gob_fact.ui.activity.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingInBinding
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var viewModel: SingInViewModel

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken!!)
            } catch (e: ApiException) {
                e.printStackTrace()
                Snackbar.make(
                    binding.root,
                    "Sing in failed",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        else {
            Snackbar.make(
                binding.root,
                "Sing in failed",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SingInViewModel::class.java]
        binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInButton.setOnClickListener {
            signIn()
        }

        binding.buttonSingIn.setOnClickListener {
            if (binding.layoutEmail.isNotBlank() && binding.layoutPassword.isNotBlank()) {
                if (binding.checkboxEnableBiometric.isChecked) {
                    setupBiometricAuthentication(
                        viewModel,
                        userName = binding.inputEmail.text.toString().trim(),
                        password = binding.inputPassword.text.toString().trim()
                    )
                } else {
                    viewModel.singIn(
                        userName = binding.inputEmail.text.toString().trim(),
                        password = binding.inputPassword.text.toString().trim()
                    )
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
        setOtherConfigurations()
    }

    fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser!!
                    viewModel.singIn(user.email.toString(), user.displayName.toString())
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Snackbar.make(
                        binding.root,
                        "Sing in failed",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setOtherConfigurations() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun setupBiometricAuthentication(
        viewModel: SingInViewModel,
        userName: String,
        password: String
    ) {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        binding.checkboxEnableBiometric.isChecked = false
                        viewModel.singIn(
                            userName = userName,
                            password = password
                        )
                        startActivity(Intent(this@SingInActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Authentication error: $errString $errorCode",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.enableBiometric()
                    viewModel.singIn(
                        userName = binding.inputEmail.text.toString().trim(),
                        password = binding.inputPassword.text.toString().trim()
                    )
                    startActivity(Intent(this@SingInActivity, MainActivity::class.java))
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Snackbar.make(
                        binding.root,
                        "Authentication failed",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
