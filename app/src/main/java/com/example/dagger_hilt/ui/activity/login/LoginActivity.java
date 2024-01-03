package com.example.dagger_hilt.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dagger_hilt.databinding.ActivityLoginBinding;
import com.example.dagger_hilt.sys.util.UtilsText;
import com.example.dagger_hilt.ui.activity.login.viewModel.LoginViewModel;
import com.example.dagger_hilt.ui.activity.main.MainActivity;
import com.example.dagger_hilt.ui.activity.singup.SingInActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        var binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel.isRegistered().observe(this, it -> {
            if (!it) {
                startActivity(new Intent(this, SingInActivity.class));
                finish();
            }
        });

        binding.buttonLogin.setOnClickListener(view -> {
            var layoutEmail = new UtilsText(binding.layoutEmail);
            var layoutPassword = new UtilsText(binding.layoutPassword);
            if (layoutEmail.isNotBlank() && layoutPassword.isNotBlank())
                if (viewModel.login(
                        binding.inputEmail.getText().toString().trim(),
                        binding.inputPassword.getText().toString().trim()
                )
                ) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
        });

    }
}
