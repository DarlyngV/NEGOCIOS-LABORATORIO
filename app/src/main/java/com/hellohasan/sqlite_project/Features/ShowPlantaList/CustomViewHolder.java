package com.hellohasan.sqlite_project.Features.ShowPlantaList;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellohasan.sqlite_project.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView stateTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.plantaNameTextView);
        stateTextView = itemView.findViewById(R.id.stateTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
