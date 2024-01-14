package com.n1akai.e_commercetutorial.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.databinding.ActivityCreateMakeBinding;
import com.n1akai.e_commercetutorial.models.Make;
import com.n1akai.e_commercetutorial.utils.Utils;

public class CreateMakeActivity extends AppCompatActivity {

    ActivityCreateMakeBinding binding;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateMakeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Add make");

        mRef = FirebaseDatabase.getInstance().getReference("makers");

        binding.buttonAdd.setOnClickListener(v -> {
            addMake();
        });
    }

    private void addMake() {
        String label = binding.etMakeLabel.getText().toString();
        if (label.trim().isEmpty()) {
            Utils.errorToast(this, "This field is required");
            return;
        }
        String id = mRef.push().getKey();
        mRef.child(id).setValue(new Make(id, label)).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Utils.successToast(this, "Added successfully!");
                finish();
            } else {
                Utils.errorToast(this, task.getException().getMessage());
            }
        });
    }
}