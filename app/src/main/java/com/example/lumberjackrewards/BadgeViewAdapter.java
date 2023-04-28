package com.example.lumberjackrewards;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorInflater;

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

    }

    @Override
    public int getItemCount() {
        return arrItemBadges.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;


        //badge icon flip animation variable
        private boolean isFrontIcon = true;
        ProgressBar progressBar;
        TextView progressTxt;

        public boolean isPinned = false;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.badgeNameTextView);
            descriptionTextView = itemView.findViewById(R.id.itemDescriptionTextView);

            progressBar = itemView.findViewById(R.id.progress_bar);
            progressTxt = itemView.findViewById(R.id.progress_txt);


            //modifies the camera scale for flip animation
            float scale = itemView.getContext().getResources().getDisplayMetrics().density;

            ImageView frontIconDisplay = itemView.findViewById(R.id.imgBadgeIcon);
            ConstraintLayout backIconDisplay = itemView.findViewById(R.id.progress_layout);

            frontIconDisplay.setCameraDistance( 8000 * scale);
            backIconDisplay.setCameraDistance( 8000 * scale);

            // set the front/back animation
            Animator front_animation = AnimatorInflater.loadAnimator(itemView.getContext(), R.animator.front_animator);
            Animator back_animation = AnimatorInflater.loadAnimator(itemView.getContext(), R.animator.back_animator);

            // onclick event listener for the container that holds the
            // the badge image and progress circle
            View containerView = itemView.findViewById(R.id.badge_IconContainer);
            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                    //JUST A TEST (DELETE OR REPLACE DOWN)
                    if(getAdapterPosition() % 2 == 0){
                        progressBar.setProgress(30);
                        progressTxt.setText("30%");
                    }
                    else{
                        progressBar.setProgress(65);
                        progressTxt.setText("65%");
                    }
                    //DELETE UP FROM HERE


                    if (isFrontIcon) {
                        front_animation.setTarget(frontIconDisplay);
                        back_animation.setTarget(backIconDisplay);
                        front_animation.start();
                        back_animation.start();
                        isFrontIcon = false;

                    } else {
                        front_animation.setTarget(backIconDisplay);
                        back_animation.setTarget(frontIconDisplay);
                        back_animation.start();
                        front_animation.start();
                        isFrontIcon = true;
                    }
                }
            });

            //pinned badge view button
            View pinBadgeBtn = itemView.findViewById(R.id.pinBadgeButton);
            pinBadgeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   isPinned = (!isPinned) ? true : false;
                    Log.d("Pinned Value" , "Pinned is: " + isPinned);
                    arrItemBadges.get(getAdapterPosition()).setIsPinned(isPinned);
                }
            });


        }

        public boolean getPinned(){
            return isPinned;
        };

        public void setPinned(boolean isPinned){
            this.isPinned = isPinned;
        }
    }
}
