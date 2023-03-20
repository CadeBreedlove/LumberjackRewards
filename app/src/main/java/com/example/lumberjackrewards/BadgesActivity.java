package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BadgesActivity extends AppCompatActivity {
    // Badges backend
    private EditText itemEdt;
    private ArrayList<BadgeItemModel> lngList;
    private ArrayAdapter<BadgeItemModel> adapter;
    private FirebaseFirestore db;
    private RecyclerView rvBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);

        // on below line we are accessing Cloud Firestore instance
        db = FirebaseFirestore.getInstance();

        // Initialize and assign variable
        rvBadge = findViewById(R.id.rvBadges);
        ArrayList<BadgeItemModel> arrBadges = new ArrayList<>();
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        //testing badge recycle view layout
        /*for (int i = 0; i < 40; i++){
            //Add values in array List
            arrBadges.add(new BadgeItemModel(i, "test", "Example" + i, "badge_ex1"));
        }*/
        /*db.collection("badges")
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

                });*/
        displayAllBadges(arrBadges);

        // Set Home selected
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
                    startActivity(new Intent(getApplicationContext(),Settings.class));
                    //overridePendingTransition(0,0);
                    break;
            }
            return true;
        });

        /*ListView languageLV = findViewById(R.id.idLVLanguages);*/
        Button addBtn = findViewById(R.id.idBtnAdd);
        Button removeBtn = findViewById(R.id.idBtnRmv);
        itemEdt = findViewById(R.id.idEdtItemName);
        lngList = new ArrayList<>();

        // on below line we are adding click listener for our button.
        addBtn.setOnClickListener(v -> {
            // on below line we are getting text from edit text
            String item = itemEdt.getText().toString();

            // on below line we are checking if item is not empty
            if (!item.isEmpty()) {

                // splitting String item to
                // populate BadgeItemModel
                String[] badgeItemAttributes = item.split(", ");

                // populating BadgeItemModel
                // int badgeID, String description, String name, String icon
                BadgeItemModel newBadge = new BadgeItemModel(Integer.parseInt(badgeItemAttributes[0]), badgeItemAttributes[1], badgeItemAttributes[2], badgeItemAttributes[3]);

                // adding badge to database
                newBadge.addNewBadgeItem(db);

                displayAllBadges(arrBadges);
                // on below line we are adding item to our list.
                //lngList.add(newBadge);

                // on below line we are notifying adapter
                // that data in list is updated to
                // update our list view.
                //adapter.notifyDataSetChanged();
            }

        });

        removeBtn.setOnClickListener(v -> {
            // on below line we are getting text from edit text
            String badgeName = itemEdt.getText().toString();

            // on below line we are checking if item is not empty
            if (!badgeName.isEmpty()) {
                deleteBadge(badgeName);
                displayAllBadges(arrBadges);
            }

        });

        //-----------------uncomment(if languageLV will still be used)---------------
        // the onItemClickListener below makes the remove button obsolete
       /* languageLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BadgeItemModel item = adapter.getItem(position);
                item.deleteBadgeItem(db);
                lngList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });*/
    }
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
                .continueWith()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("badges").document(document.toString())
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