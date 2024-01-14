package com.n1akai.e_commercetutorial.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.utils.Utils;
import com.n1akai.e_commercetutorial.databinding.ActivityRegisterBinding;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth mAuth;
    String email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Register");

        mAuth = FirebaseAuth.getInstance();
        binding.buttonRegister.setOnClickListener(v -> {
            register();
        });
    }

    private void register() {
        email = binding.emailEt.getText().toString();
        password = binding.passwordEt.getText().toString();
        confirmPassword = binding.passwordConfirmEt.getText().toString();
        if(!validation()) return;
        binding.progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            if (task.isSuccessful()) {
                goToHomeActivity();
                Utils.successToast(this, Utils.getString(this, R.string.registered_successfully));
            } else {
                Utils.errorToast(this, Utils.getString(this, R.string.error));
            }
        });
    }

    private boolean validation() {
        if (email.trim().isEmpty()) {
            binding.emailEtLayout.setError("Email is required");
            return false;
        }
        binding.emailEtLayout.setError(null);

        if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email.trim())) {
            binding.emailEtLayout.setError("Email is invalid");
            return false;
        }
        binding.emailEtLayout.setError(null);

        if (password.trim().isEmpty()) {
            binding.passwordEtLayout.setError("Password is required");
            return false;
        }
        binding.passwordEtLayout.setError(null);

        if(password.trim().length() < 8) {
            binding.passwordEtLayout.setError("Password has to be equal or greater than 8 characters");
            return false;
        }
        binding.passwordEtLayout.setError(null);

        if (confirmPassword.trim().isEmpty()) {
            binding.confirmPasswordEtLayout.setError("Confirm password is required");
            return false;
        }
        binding.confirmPasswordEtLayout.setError(null);

        if(!password.trim().equals(confirmPassword.trim())) {
            binding.confirmPasswordEtLayout.setError("Passwords don't match");
            return false;
        }
        binding.confirmPasswordEtLayout.setError(null);

        return true;
    }

    private void goToHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}