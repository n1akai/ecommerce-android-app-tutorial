package com.n1akai.e_commercetutorial.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.adapters.CarAdapter;
import com.n1akai.e_commercetutorial.databinding.ActivityHomeBinding;
import com.n1akai.e_commercetutorial.models.Car;
import com.n1akai.e_commercetutorial.models.Make;
import com.n1akai.e_commercetutorial.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    FirebaseAuth mAuth;
    CarAdapter adapter;
    List<Car> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Home");

        mAuth = FirebaseAuth.getInstance();

        carsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            mAuth.signOut();
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return true;
        } else if(id == R.id.add_make) {
            startActivity(new Intent(this, CreateMakeActivity.class));
            return true;
        } else if(id == R.id.add_car) {
            startActivity(new Intent(this, CreateCarActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void carsList() {
        cars = new ArrayList<>();
        adapter = new CarAdapter(cars);
        DatabaseReference mDataRef = FirebaseDatabase.getInstance().getReference("cars");
        mDataRef.addValueEventListener(new ValueEventListener() {
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

        binding.listCars.setLayoutManager(new LinearLayoutManager(this));
        binding.listCars.setAdapter(adapter);
        adapter.setOnCarClickListener(car -> {
            Intent i = new Intent(this, CarDetailActivity.class);
            i.putExtra("car", car);
            startActivity(i);
        });
    }
}