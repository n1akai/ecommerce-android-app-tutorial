package com.n1akai.e_commercetutorial.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.adapters.SimilarCarsAdapter;
import com.n1akai.e_commercetutorial.databinding.ActivityCarDetailBinding;
import com.n1akai.e_commercetutorial.models.Car;
import com.n1akai.e_commercetutorial.models.Make;
import com.n1akai.e_commercetutorial.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CarDetailActivity extends AppCompatActivity {

    ActivityCarDetailBinding binding;
    List<Car> cars;
    Car car;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        car = (Car) getIntent().getSerializableExtra("car");
        mRef = FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setTitle(car.getModel());

        setDataToView();
        similarCars();
    }

    private void setDataToView() {
        Glide.with(this).load(car.getImage()).into(binding.carDetailImage);
        FirebaseDatabase.getInstance().getReference("makers").child(car.getMake_id()).get().addOnCompleteListener(task -> {
            Make make = task.getResult().getValue(Make.class);
            binding.carDetailMake.setText(make.getLabel());
        });
        binding.carDetailModel.setText(car.getModel());
        binding.carDetailFuel.setText(car.getFuel());
        binding.carDetailPrice.setText("$"+car.getPrice());
        binding.carDetailTopSpeed.setText(car.getTopSpeed()+" Km/h");
        binding.carDetailYear.setText(""+car.getYear());
    }

    private void similarCars() {
        cars = new ArrayList<>();
        SimilarCarsAdapter adapter = new SimilarCarsAdapter(cars);
        mRef.child("cars").orderByChild("make_id").equalTo(car.getMake_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cars.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    cars.add(ds.getValue(Car.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.errorToast(getBaseContext(), error.getMessage());
            }
        });

        binding.carDetialListCars.setAdapter(adapter);

        adapter.setOnSimilarCarClickListener(theCar -> {
            Intent i = new Intent(this, CarDetailActivity.class);
            i.putExtra("car", theCar);
            startActivity(i);
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cars_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.car_edit) {
            Intent i = new Intent(this, CreateCarActivity.class);
            i.putExtra("car", car);
            startActivity(i);
        } else if (id == R.id.car_delete) {
            deleteDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete Car")
                .setMessage("Do you want to delete this car?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteCar();
                })
                .setNegativeButton("No", (dialog, which) -> {

                })
                .show();
    }

    private void deleteCar() {
        mRef.child("cars").child(car.getId()).removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Utils.successToast(this, "Deleted successfully!");
                finish();
            } else {
                Utils.errorToast(this, task.getException().getMessage());
            }
        });
    };
}