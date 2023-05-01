package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityGroup extends AppCompatActivity {
    private FirebaseFirestore db;
    private RecyclerView rvStudent;
    private UserViewAdapter adapter;
    private Button addBtn;
    private Button rmvBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        db = FirebaseFirestore.getInstance();

        rvStudent = findViewById(R.id.rvStudents);
        ArrayList<UserModel> arrStudents = new ArrayList<>();
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        displayAllUsers(arrStudents);

        bottomNavigationView.setSelectedItemId(R.id.navigation_badges);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    //overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_badges:
                    break;
                case R.id.navigation_settings:
                    startActivity(new Intent(getApplicationContext(), ActivitySettings.class));
                    //overridePendingTransition(0,0);
                    break;
            }
            return true;
        });

        addBtn = findViewById(R.id.btnAddBtn);
        addBtn.setOnClickListener(v -> {
            ArrayList<UserModel> students;
            students = adapter.getCheckedUsers();
            for(int i = 0; i < students.size(); i++){
                AssignUserToGroup("Senior Design", students.get(i).geteMail());
            }

        });

        // uncomment when we add front end for removing users
        /*rmvBtn = findViewById(R.id.idBtnRmv);
        rmvBtn.setOnClickListener(v -> {
            ArrayList<UserModel> students;
            students = adapter.getCheckedUsers();
            for(int i = 0; i < students.size(); i++){
                DeleteUserFromGroup("Senior Design", students.get(i).geteMail());
            }
        });*/




    }

    private void displayAllUsers(ArrayList<UserModel> arrStudents) {
        arrStudents.clear();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            UserModel student = document.toObject(UserModel.class);
                            arrStudents.add(student);
                        }

                        //layout manager for badge test
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);

                        //set layout manager
                        rvStudent.setLayoutManager(layoutManager);

                        //set adapter
                        rvStudent.setAdapter(new UserViewAdapter(arrStudents));

                    }

                });
    }

    public void AssignUserToGroup(String groupID, String userId) {
        // Get the collection reference
        db = FirebaseFirestore.getInstance();

        CollectionReference UsersRef = db.collection("users");

        // Create a query to search for the document with the unique field value
        Query query = UsersRef.whereEqualTo("userID", userId);

        // Execute the query asynchronously
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String documentId = document.getId();
                    String badgeName = document.getString("name");
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("name", badgeName);
                    docData.put("userID", userId);

                    db.collection("groups").document(groupID).collection("users").document(documentId)
                            .set(docData);
                }
            }
        });
    }

    public void DeleteUserFromGroup(String groupID, String userID) {
        db.collection("groups").document(groupID).collection("users").whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("groups").document(groupID).collection("users").document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("DELETED", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("ERROR", "Error deleting document", e);
                                            }
                                        });

                            }
                        } else {
                            Log.d("ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void AssignBadgeToGroup(String groupID, String badgeId) {
        // Get the collection reference


        CollectionReference badgesRef = db.collection("badges");

        // Create a query to search for the document with the unique field value
        Query query = badgesRef.whereEqualTo("badgeID", badgeId);

        // Execute the query asynchronously
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String documentId = document.getId();
                    String badgeName = document.getString("name");
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("name", badgeName);
                    docData.put("badgeID", badgeId);

                    db.collection("groups").document(groupID).collection("badges").document(documentId)
                            .set(docData);
                }
            }
        });
    }

    public void DeleteBadgeFromGroup(String groupID, String badgeID) {
        db.collection("groups").document(groupID).collection("badges").whereEqualTo("badgeID", badgeID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("groups").document(groupID).collection("badges").document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("DELETED", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("ERROR", "Error deleting document", e);
                                            }
                                        });

                            }
                        } else {
                            Log.d("ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
