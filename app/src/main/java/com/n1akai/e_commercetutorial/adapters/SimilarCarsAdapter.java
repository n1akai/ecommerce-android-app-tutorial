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
import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.models.Car;

import java.util.List;

public class SimilarCarsAdapter extends RecyclerView.Adapter<SimilarCarsAdapter.SimilarCarsViewHolder> {

    OnSimilarCarClickListener listener;
    List<Car> cars;

    public SimilarCarsAdapter(List<Car> cars) {
        this.cars = cars;
    }

    class SimilarCarsViewHolder extends RecyclerView.ViewHolder {

        TextView model;
        ImageView image;

        public SimilarCarsViewHolder(@NonNull View v) {
            super(v);
            model = v.findViewById(R.id.s_car_model);
            image = v.findViewById(R.id.s_car_image);
        }
    }

    @NonNull
    @Override
    public SimilarCarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarCarsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_car_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarCarsViewHolder holder, int position) {
        Car car = cars.get(position);
        if (car != null) {
            holder.model.setText(car.getModel());
            Glide.with(holder.itemView).load(car.getImage()).into(holder.image);
            holder.itemView.setOnClickListener(v -> {
                listener.onCarClick(car);
            });
        }
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }


    public void setOnSimilarCarClickListener(OnSimilarCarClickListener listener) {
        this.listener = listener;
    }

    public interface OnSimilarCarClickListener {
        void onCarClick(Car car);
    }
}
