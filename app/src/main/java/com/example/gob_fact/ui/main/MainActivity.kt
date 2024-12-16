package com.example.gob_fact.ui.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gob_fact.R
import com.example.gob_fact.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        window.statusBarColor = getColor(R.color.white)
        setContentView(binding.root)
        setOtherConfigurations()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setOtherConfigurations() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}