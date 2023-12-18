package com.example.dagger_hilt.ui.activity.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dagger_hilt.databinding.ActivitySplashBinding;
import com.example.dagger_hilt.sys.util.Constants;
import com.example.dagger_hilt.ui.activity.main.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = ActivitySplashBinding.inflate(getLayoutInflater());
        var viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        setContentView(binding.getRoot());
        viewModel.loadMovies().observe(this, result -> {
            if (result == Constants.StatusResponse.SUCCESS || result == Constants.StatusResponse.ERROR) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }
}
