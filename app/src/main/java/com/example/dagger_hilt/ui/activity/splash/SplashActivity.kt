package com.example.dagger_hilt.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dagger_hilt.databinding.ActivitySplashBinding
import com.example.dagger_hilt.sys.util.Constants.StatusResponse
import com.example.dagger_hilt.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(
            layoutInflater
        )
        val viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        setContentView(binding.root)
        viewModel.loadMovies().observe(this) { result: StatusResponse ->
            if (result === StatusResponse.SUCCESS || result === StatusResponse.ERROR) {
                val main = Intent(this, MainActivity::class.java)
                startActivity(main)
                finish()
            }
        }
    }
}