package com.example.lumberjackrewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PinnedBadgesAdapter extends RecyclerView.Adapter<PinnedBadgesAdapter.ViewHolder> {

    ArrayList<BadgeItemModel> arrItemBadges;
    public PinnedBadgesAdapter(ArrayList<BadgeItemModel> arrBadges) {
        this.arrItemBadges = arrBadges;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_badge, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PinnedBadgesAdapter.ViewHolder holder, int position) {
        holder.nameTextView.setText(arrItemBadges.get(position).getName());
        holder.descriptionTextView.setText(arrItemBadges.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
       return arrItemBadges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.badgeNameTextView);
            descriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);
        }
    }
}