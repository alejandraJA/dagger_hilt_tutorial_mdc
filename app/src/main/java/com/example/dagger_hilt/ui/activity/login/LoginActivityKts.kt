package com.example.dagger_hilt.ui.activity.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dagger_hilt.databinding.ActivityLoginBinding
import com.example.dagger_hilt.sys.util.UtilsTextKts.isNotBlank
import com.example.dagger_hilt.ui.activity.main.MainActivityKts
import com.example.dagger_hilt.ui.activity.singup.SingInActivityKts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivityKts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[LoginViewModelKts::class.java]
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userName.observe(this) {
            if (!it) {
                startActivity(Intent(this, SingInActivityKts::class.java))
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
                    startActivity(Intent(this, MainActivityKts::class.java))
                    finish()
                }
        }
    }
}