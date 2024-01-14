package com.n1akai.e_commercetutorial.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.models.Car;
import com.n1akai.e_commercetutorial.models.Make;

public class CarAdapter extends FirebaseRecyclerAdapter<Car, CarAdapter.CarViewHolder> {

    public CarAdapter(@NonNull FirebaseRecyclerOptions<Car> options) {
        super(options);
    }

    class CarViewHolder extends RecyclerView.ViewHolder {

        TextView make, model, year, price;
        ImageView carImage, addToCart;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            make = itemView.findViewById(R.id.make_tv);
            model = itemView.findViewById(R.id.model_tv);
            year = itemView.findViewById(R.id.year_tv);
            price = itemView.findViewById(R.id.price_tv);
            carImage = itemView.findViewById(R.id.car_iv);
            addToCart = itemView.findViewById(R.id.add_to_cart_iv);
        }
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull CarViewHolder holder, int position, @NonNull Car car) {
        FirebaseDatabase.getInstance().getReference("makers").child(car.getMake_id()).get().addOnCompleteListener(task -> {
            Make make = task.getResult().getValue(Make.class);
            holder.make.setText(make.getLabel());
        });
        holder.model.setText(car.getModel());
        holder.year.setText(""+car.getYear());
        holder.price.setText(""+car.getPrice());
        Glide.with(holder.itemView).load(car.getImage()).into(holder.carImage);

    }
}
