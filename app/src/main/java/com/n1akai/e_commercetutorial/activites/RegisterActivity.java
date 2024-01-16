package com.n1akai.e_commercetutorial.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.models.Client;
import com.n1akai.e_commercetutorial.utils.Utils;
import com.n1akai.e_commercetutorial.databinding.ActivityRegisterBinding;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    String firstName, lastName, adresse, phone, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Register");
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        binding.buttonRegister.setOnClickListener(v -> {
            register();
        });
    }

    private void register() {
        firstName = binding.fnEt.getText().toString();
        lastName = binding.lnEt.getText().toString();
        adresse = binding.adresseEt.getText().toString();
        phone = binding.phoneEt.getText().toString();
        email = binding.emailEt.getText().toString();
        password = binding.passwordEt.getText().toString();
        confirmPassword = binding.passwordConfirmEt.getText().toString();
        if(!validation()) return;
        binding.progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            if (task.isSuccessful()) {
                addUserDataToDatabase(task.getResult().getUser().getUid());
            } else {
                Utils.errorToast(this, task.getException().getMessage());
            }
        });
    }

    private void addUserDataToDatabase(String uid) {
        Client client = new Client(uid, firstName, lastName, email, adresse, phone);
        mRef.child("clients").child(uid).setValue(client).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Utils.successToast(this, Utils.getString(this, R.string.registered_successfully));
                goToHomeActivity();
            } else {
                Utils.errorToast(this, task1.getException().getMessage());
            }
        });
    }

    private boolean validation() {
        if (firstName.trim().isEmpty()) {
            binding.fnEtLayout.setError("First name is required");
            return false;
        }
        binding.fnEtLayout.setError(null);

        if (lastName.trim().isEmpty()) {
            binding.lnEtLayout.setError("Last name is required");
            return false;
        }
        binding.lnEtLayout.setError(null);

        if (adresse.trim().isEmpty()) {
            binding.adresseEtLayout.setError("Adresse is required");
            return false;
        }
        binding.adresseEtLayout.setError(null);

        if (phone.trim().isEmpty()) {
            binding.phoneEtLayout.setError("Phone number is required");
            return false;
        }
        binding.phoneEtLayout.setError(null);

        if (email.trim().isEmpty()) {
            binding.emailEtLayout.setError("Email is required");
            return false;
        }
        binding.emailEtLayout.setError(null);

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