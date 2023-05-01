package com.example.lumberjackrewards;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.ViewHolder> {

    ArrayList<UserModel> arrStudents;

    public UserViewAdapter(ArrayList<UserModel> arrStudents) {
        this.arrStudents = arrStudents;
    }

    // get the index of items that have been checked in
    // activity_users.xml
    public ArrayList<UserModel> getCheckedUsers() {
        ArrayList<UserModel> students = new ArrayList<>();
        for (int i = 0; i < arrStudents.size(); i++) {
            if (arrStudents.get(i).isChecked()) {
                students.add(arrStudents.get(i));
            }
        }
        return students;
    }

    // populate the recycler view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_view_model, parent, false);

        return new ViewHolder(view);
    }


    // dynamically set the names and ids (emails) of
    // users in the recycler view
    @Override
    public void onBindViewHolder(@NonNull UserViewAdapter.ViewHolder holder, int position) {

        holder.studentViewModelName.setText(arrStudents.get(position).getFullName());
        holder.studentViewModelEmail.setText(arrStudents.get(position).geteMail());
    }

    // get total number of items in the recycler view
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
