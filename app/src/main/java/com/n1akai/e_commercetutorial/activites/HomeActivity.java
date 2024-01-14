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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.adapters.CarAdapter;
import com.n1akai.e_commercetutorial.databinding.ActivityHomeBinding;
import com.n1akai.e_commercetutorial.models.Car;
import com.n1akai.e_commercetutorial.models.Make;
import com.n1akai.e_commercetutorial.utils.Utils;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    FirebaseAuth mAuth;
    CarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Home");

        Log.d("Hello", String.valueOf(R.drawable.gt86));

        mAuth = FirebaseAuth.getInstance();

        Query query = FirebaseDatabase.getInstance().getReference("cars");

        FirebaseRecyclerOptions<Car> options = new FirebaseRecyclerOptions.Builder<Car>()
                .setQuery(query, Car.class)
                .build();



        adapter = new CarAdapter(options);
        binding.listCars.setAdapter(adapter);
        binding.listCars.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
}