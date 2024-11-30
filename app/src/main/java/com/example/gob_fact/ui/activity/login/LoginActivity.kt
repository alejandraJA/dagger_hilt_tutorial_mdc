package com.example.gob_fact.ui.activity.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gob_fact.databinding.ActivityLoginBinding
import com.example.gob_fact.sys.util.UtilsText.isNotBlank
import com.example.gob_fact.ui.activity.login.viewModel.LoginViewModel
import com.example.gob_fact.ui.activity.main.MainActivity
import com.example.gob_fact.ui.activity.singup.SingInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userName.observe(this) {
            if (!it) {
                startActivity(Intent(this, SingInActivity::class.java))
                finish()
            }
        }

        binding.buttonLogin.setOnClickListener {
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
    }
}