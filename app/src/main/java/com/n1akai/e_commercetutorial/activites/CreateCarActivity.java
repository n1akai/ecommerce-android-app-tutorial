package com.n1akai.e_commercetutorial.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    MakeSpinnerAdapter adapter;
    Car car;
    final static int CODE_ADD = 100;
    final static int CODE_UPDATE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Car");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        addMakersToSpinner();
        car = (Car) getIntent().getSerializableExtra("car");

        if(car != null) {
            setDataToFields();
            getSupportActionBar().setTitle("Update Car");
            binding.createCarActivityTitle.setText("Update Car");
            binding.buttonAddCar.setText("Update");
            binding.buttonAddCar.setOnClickListener(v -> addOrUpdateCarToDatabase(CODE_UPDATE));
        } else {
            binding.buttonAddCar.setOnClickListener(v -> addOrUpdateCarToDatabase(CODE_ADD));
        }
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
                adapter = new MakeSpinnerAdapter(getBaseContext(), makeList);
                binding.carMake.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.errorToast(getBaseContext(), error.getMessage());
            }
        });
    }

    private void addOrUpdateCarToDatabase(int code) {
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
        String message;
        String id;
        if (code == CODE_UPDATE) {
            message = "Updated successfully!";
            id = car.getId();
        } else {
            message = "Added successfully!";
            id = carsRef.push().getKey();
        }
        Car car = new Car(id, makeId, model, fuel, topSpeed, year, price, imageUrl);
        carsRef.child(id).setValue(car).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Utils.successToast(this, message);
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Utils.errorToast(this, task.getException().getMessage());
            }
        });
    }

    private void setDataToFields() {
        binding.carModelEt.setText(car.getModel());
        binding.carTopSpeedEt.setText(car.getTopSpeed());
        binding.carYearEt.setText(""+car.getYear());
        binding.carPriceEt.setText(""+car.getPrice());
        binding.carImageUrlEt.setText(car.getImage());
        if (car.getFuel().equals("Gasoline")) {
            binding.carFuelType.check(R.id.car_gasoline);
        } else {
            binding.carFuelType.check(R.id.car_diesel);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}