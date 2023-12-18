package com.example.dagger_hilt.ui.activity.singup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dagger_hilt.databinding.ActivitySingInBinding
import com.example.dagger_hilt.sys.util.UtilsTextKts.isNotBlank
import com.example.dagger_hilt.ui.activity.splash.SplashActivityKts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingInActivityKts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[SingInViewModelKts::class.java]
        val binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSingIn.setOnClickListener {
            if (binding.layoutEmail.isNotBlank() && binding.layoutPassword.isNotBlank()) {
                viewModel.singIn(
                    userName = binding.inputEmail.text.toString().trim(),
                    password = binding.inputPassword.text.toString().trim()
                )
                startActivity(Intent(this, SplashActivityKts::class.java))
            }
        }
    }
}
