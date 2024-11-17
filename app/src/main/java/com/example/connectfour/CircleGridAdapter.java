package com.example.connectfour;// CircleGridAdapter.java

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleGridAdapter extends RecyclerView.Adapter<CircleGridAdapter.CircleViewHolder> {
    private static final int TOTAL_ITEMS = 7 * 6; // 7 rows x 6 columns
    private final Context context;

    public CircleGridAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CircleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_circle, parent, false);
        return new CircleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CircleViewHolder holder, int position) {
        holder.button.setText("");
        // Handle button click to change color
        holder.button.setOnClickListener(v -> {
            ColorDrawable background = (ColorDrawable) holder.button.getBackground();
            int currentColor = background.getColor();
            holder.button.setBackgroundColor(currentColor == Color.WHITE ? Color.RED : Color.WHITE);
        });
    }

    @Override
    public int getItemCount() {
        return TOTAL_ITEMS;
    }

    public static class CircleViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public CircleViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.gridButton);
        }
    }
}
