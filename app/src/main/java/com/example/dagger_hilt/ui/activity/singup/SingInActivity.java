package com.example.dagger_hilt.ui.activity.singup;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dagger_hilt.databinding.ActivitySingInBinding;
import com.example.dagger_hilt.sys.util.UtilsText;
import com.example.dagger_hilt.ui.activity.main.MainActivity;
import com.example.dagger_hilt.ui.activity.singup.viewModel.SingInViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SingInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var viewModel = new ViewModelProvider(this).get(SingInViewModel.class);
        var binding = ActivitySingInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonSingIn.setOnClickListener(view -> {
            var layoutEmail = new UtilsText(binding.layoutEmail);
            var layoutPassword = new UtilsText(binding.layoutPassword);
            if (layoutEmail.isNotBlank() && layoutPassword.isNotBlank()) {
                viewModel.singIn(
                        binding.inputEmail.getText().toString().trim(),
                        binding.inputPassword.getText().toString().trim()
                );
                startActivity(new Intent(this, MainActivity.class));
            }
        });
    }
}
