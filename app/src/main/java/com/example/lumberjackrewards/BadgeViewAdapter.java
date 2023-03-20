package com.example.lumberjackrewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BadgeViewAdapter extends RecyclerView.Adapter<BadgeViewAdapter.ViewHolder> {
    ArrayList<BadgeItemModel> arrItemBadges;

    public BadgeViewAdapter(ArrayList<BadgeItemModel> arrBadges) {
        this.arrItemBadges = arrBadges;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_badge2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set text
        holder.nameTextView.setText(arrItemBadges.get(position).getName());
        holder.descriptionTextView.setText(arrItemBadges.get(position).getDescription());
        //holder.iconImageView.setImageDrawable(arrItemBadges.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return arrItemBadges.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        //ImageView iconImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.badgeNameTextView);
            descriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);
            //iconImageView = itemView.findViewById(R.id.imgBadgeIcon);
        }
    }
}
