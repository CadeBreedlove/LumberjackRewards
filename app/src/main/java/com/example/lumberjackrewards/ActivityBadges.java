package com.example.lumberjackrewards;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class ActivityBadges extends AppCompatActivity {

    private EditText itemEdt;
    private ArrayList<BadgeItemModel> badgesToDelete;
    private BadgeViewAdapter adapter;
    private FirebaseFirestore db;
    private RecyclerView rvBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_badges);

        adapter = new BadgeViewAdapter();
        // accessing Cloud Firestore instance
        db = FirebaseFirestore.getInstance();

        // Initialize and assign variable
        rvBadge = findViewById(R.id.rvBadges);
        ArrayList<BadgeItemModel> arrBadges = new ArrayList<>();
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Fills recycler view with every badge in the database
        displayAllBadges(arrBadges);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_badges);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.navigation_home:

                    //Sends back to main activity what badges are pinned
                        ArrayList<BadgeItemModel> pinnedBadges = new ArrayList<>();

                       for(int i = 0; i < arrBadges.size(); i++){
                          boolean isPinned = arrBadges.get(i).getIsPinned();
                           if(isPinned) {
                               pinnedBadges.add(arrBadges.get(i));
                           }
                       }
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        if(!pinnedBadges.isEmpty()) {
                            intent.putExtra("pinnedBadges", pinnedBadges);
                        }
                        startActivity(intent);

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

        Button addBtn = findViewById(R.id.btnBadgeAdd);
        Button removeBtn = findViewById(R.id.btnBadgeRemove);
        Button btnManage = findViewById(R.id.btnManageUsersAndGroups);

        // adding click listener for our button.
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityBadges.this, ActivityAddBadge.class);
            startActivity(intent);
            finish();
        });

        btnManage.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityBadges.this, ActivityManage.class);
            startActivity(intent);
            finish();
        });

        // NOT WORKING: see BadgeViewAdapter.java itemView.setOnClickListener
        // for more details.
        // uncomment to activate removeBtn
        /*removeBtn.setOnClickListener(v -> {
            if (adapter.badgesToDelete.size() != 0){
                for(int i = 0; i < adapter.badgesToDelete.size(); i++){
                    deleteBadge(adapter.badgesToDelete.get(i).getName());
                }
            }

            displayAllBadges(arrBadges);

        });*/



    }

    // Query for all badges in the db and
    // displays them in the recycler view
    private void displayAllBadges(ArrayList<BadgeItemModel> arrBadges) {
        arrBadges.clear();
        db.collection("badges")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            BadgeItemModel badge = document.toObject(BadgeItemModel.class);
                            arrBadges.add(badge);
                        }
                        Log.d("PRINT_ARRAY", arrBadges.get(0).toString());


                        //layout manager for badge test
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);

                        //set layout manager
                        rvBadge.setLayoutManager(layoutManager);

                        //set adapter
                        rvBadge.setAdapter(new BadgeViewAdapter(arrBadges));

                    }

                });
    }

    private void deleteBadge(String badgeName){
        db.collection("badges").whereEqualTo("name", badgeName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBadges.this);
                            builder.setMessage("You are about to remove this badge.");
                            builder.setTitle("Notice");
                            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                            });
                            builder.setNegativeButton("No",(DialogInterface.OnClickListener)(dialog, which) -> {
                                dialog.cancel();
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("badges").document(document.getId())
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


    public void AssignBadge(String userID, String badgeId) {
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

                    db.collection("users").document(userID).collection("badges").document(documentId)
                            .set(docData);
                }
            }
        });

    }

    public void DeleteBadgeFromUser(String userID, String badgeID) {
        db.collection("users").document(userID).collection("badges").whereEqualTo("badgeID", badgeID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("users").document(userID).collection("badges").document(document.getId())
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