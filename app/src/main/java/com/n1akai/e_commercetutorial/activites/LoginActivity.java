package com.n1akai.e_commercetutorial.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.n1akai.e_commercetutorial.utils.Utils;
import com.n1akai.e_commercetutorial.databinding.ActivityLoginBinding;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        binding.buttonLogin.setOnClickListener(v -> login());
        getSupportActionBar().setTitle("Login");

    }


    private void login() {
        email = binding.loginEmailEt.getText().toString();
        password = binding.loginPasswordEt.getText().toString();
        if(!validation()) return;
        binding.loginProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            binding.loginProgressBar.setVisibility(View.INVISIBLE);
            if(task.isSuccessful()) {
                goToHomeActivity();
                Utils.successToast(this, "Logged in successfully");
            } else {
                Utils.errorToast(this, task.getException().getMessage());
            }
        });
    }

    private boolean validation() {
        if (email.trim().isEmpty()) {
            binding.loginEmailEtLayout.setError("Email is required");
            return false;
        }
        binding.loginEmailEtLayout.setError(null);

        if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email.trim())) {
            binding.loginEmailEtLayout.setError("Email is invalid");
            return false;
        }
        binding.loginEmailEtLayout.setError(null);

        if (password.trim().isEmpty()) {
            binding.loginPasswordEtLayout.setError("Password is required");
            return false;
        }
        binding.loginPasswordEtLayout.setError(null);

        return true;
    }

    private void goToHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}