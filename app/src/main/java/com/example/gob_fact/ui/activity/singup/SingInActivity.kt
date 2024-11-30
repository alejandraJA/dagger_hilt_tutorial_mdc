package com.example.gob_fact.ui.activity.singup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gob_fact.databinding.ActivitySingInBinding
import com.example.gob_fact.sys.util.UtilsText.isNotBlank
import com.example.gob_fact.ui.activity.main.MainActivity
import com.example.gob_fact.ui.activity.singup.viewModel.SingInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[SingInViewModel::class.java]
        val binding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSingIn.setOnClickListener {
            if (binding.layoutEmail.isNotBlank() && binding.layoutPassword.isNotBlank()) {
                viewModel.singIn(
                    userName = binding.inputEmail.text.toString().trim(),
                    password = binding.inputPassword.text.toString().trim()
                )
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}
