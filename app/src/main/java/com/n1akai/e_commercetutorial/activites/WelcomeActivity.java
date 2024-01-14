package com.n1akai.e_commercetutorial.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.n1akai.e_commercetutorial.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Welcome");

        mAuth = FirebaseAuth.getInstance();
        binding.welcomeButtonRegister.setOnClickListener(v -> goToActivity(RegisterActivity.class));
        binding.welcomeButtonLogin.setOnClickListener(v -> goToActivity(LoginActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            goToHomeActivity();
        }
    }

    private void goToActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    private void goToHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

}