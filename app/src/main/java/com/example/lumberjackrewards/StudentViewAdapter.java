package com.example.lumberjackrewards;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.ViewHolder> {

    ArrayList<UserModel> arrStudents;

    public StudentViewAdapter(ArrayList<UserModel> arrStudents) {
        this.arrStudents = arrStudents;
    }

    public ArrayList<UserModel> getCheckedUsers() {
        ArrayList<UserModel> students = new ArrayList<>();
        for (int i = 0; i < arrStudents.size(); i++) {
            if (arrStudents.get(i).isChecked()) {
                students.add(arrStudents.get(i));
            }
        }
        return students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_view_model, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StudentViewAdapter.ViewHolder holder, int position) {

        holder.studentViewModelName.setText(arrStudents.get(position).getFullName());
        holder.studentViewModelEmail.setText(arrStudents.get(position).geteMail());
    }

    @Override
    public int getItemCount() {
        return arrStudents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentViewModelName;
        TextView studentViewModelEmail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentViewModelName = itemView.findViewById(R.id.studentViewModelName);
            studentViewModelName = itemView.findViewById(R.id.studentViewModelEmail);

            // onclick event listener for the container that holds the
            // the badge image and progress circle
            View containerView = itemView.findViewById(R.id.student_IconContainer);

        }
    }


}
