package com.example.lumberjackrewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                .inflate(R.layout.item_badge, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set text
        holder.txtView.setText(arrItemBadges.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrItemBadges.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtView = itemView.findViewById(R.id.badge_title);
        }
    }
}
