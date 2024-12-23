package com.example.gob_fact.ui.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gob_fact.R
import com.example.gob_fact.databinding.ActivityMainBinding
import com.example.gob_fact.sys.util.NetworkCallbackImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var networkCallback: NetworkCallbackImpl

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        window.statusBarColor = getColor(R.color.white)
        setContentView(binding.root)
        enableEdgeToEdge()
        setOtherConfigurations()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkCallback.unregister()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setOtherConfigurations() {
        networkCallback = NetworkCallbackImpl(this, {
            lifecycleScope.launch(Dispatchers.Main) {
                binding.layoutInternetDisabled.visibility = View.GONE
            }
        }, {
            lifecycleScope.launch(Dispatchers.Main) {
                binding.layoutInternetDisabled.visibility = View.VISIBLE
            }
        })
        networkCallback.register()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}