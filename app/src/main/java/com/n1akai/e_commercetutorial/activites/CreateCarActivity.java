package com.n1akai.e_commercetutorial.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.adapters.MakeSpinnerAdapter;
import com.n1akai.e_commercetutorial.databinding.ActivityCreateCarBinding;
import com.n1akai.e_commercetutorial.models.Car;
import com.n1akai.e_commercetutorial.models.Make;
import com.n1akai.e_commercetutorial.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CreateCarActivity extends AppCompatActivity {

    ActivityCreateCarBinding binding;
    DatabaseReference mRef;
    List<Make> makeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add car");

        init();
        addMakersToSpinner();
        binding.buttonAddCar.setOnClickListener(v -> {
            addCarToDatabase();
        });

    }

    private void init() {
        mRef = FirebaseDatabase.getInstance().getReference();
        makeList = new ArrayList<>();
    }

    private void addMakersToSpinner() {
        mRef.child("makers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    makeList.add(dataSnapshot.getValue(Make.class));
                }
                MakeSpinnerAdapter adapter = new MakeSpinnerAdapter(getBaseContext(), makeList);
                binding.carMake.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addCarToDatabase() {
        String makeId = ((Make) binding.carMake.getSelectedItem()).getId();
        String model = binding.carModelEt.getText().toString();
        String topSpeed = binding.carTopSpeedEt.getText().toString();
        int year = Integer.parseInt(binding.carYearEt.getText().toString());
        Double price = Double.parseDouble(binding.carPriceEt.getText().toString());
        String imageUrl = binding.carImageUrlEt.getText().toString();
        String fuel = "Gasoline";
        if (binding.carFuelType.getCheckedRadioButtonId() == R.id.car_diesel) {
            fuel = "Diesel";
        }
        DatabaseReference carsRef = mRef.child("cars");
        String id = carsRef.push().getKey();
        Car car = new Car(id, makeId, model, fuel, topSpeed, year, price, imageUrl);
        carsRef.child(id).setValue(car).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Utils.successToast(this, "Added successfully!");
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Utils.errorToast(this, task.getException().getMessage());
            }
        });
    }
}