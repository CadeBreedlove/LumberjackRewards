package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ActivityAddBadge extends AppCompatActivity {
    private EditText badgeName;
    private EditText badgeClassName;
    private EditText badgeDescription;
    private FirebaseFirestore db;
    private Button createBadge;
    private ImageButton returnButton;
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_badge);

        db = FirebaseFirestore.getInstance();
        badgeName = findViewById(R.id.badgeName);
        badgeClassName = findViewById(R.id.className);
        badgeDescription = findViewById(R.id.badgeDescription);
        createBadge = findViewById(R.id.createBadge);
        returnButton = findViewById(R.id.backButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        // Set settings selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);

        // Perform item selected listener for settings page
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.navigation_badges:
                    startActivity(new Intent(getApplicationContext(),BadgesActivity.class));
                    //overridePendingTransition(0,0);
                    break;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    break;
                case R.id.navigation_settings:
                    startActivity(new Intent(getApplicationContext(),Settings.class));
            }
            return true;
        });

       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityAddBadge.this, Settings.class));
            }
        });*/

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BadgesActivity.class));
            }
        });

        createBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = db.collection("badges");
                AggregateQuery countQuery = query.count();
                countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Count fetched successfully
                            AggregateQuerySnapshot snapshot = task.getResult();
                            long numberOfBadgesInDB = snapshot.getCount();
                            String name = badgeName.getText().toString().trim();
                            String description = badgeDescription.getText().toString().trim();
                            String className = badgeClassName.getText().toString().trim();
                            String imagePath = "path";

                            if (TextUtils.isEmpty(name)) {
                                badgeName.setError("A valid badge name is required");
                                return;
                            }
                            if (TextUtils.isEmpty(description)) {
                                badgeDescription.setError("A valid description is required");
                                return;
                            }
                            if (TextUtils.isEmpty(className)) {
                                badgeClassName.setError("A valid class is required");
                                return;
                            }

                            // populating BadgeItemModel
                            // int badgeID, String description, String name, String icon
                            BadgeItemModel newBadge = new BadgeItemModel(numberOfBadgesInDB, description, name, imagePath);

                            // adding badge to database
                            db.collection("badges").document(name.replaceAll("\\s", "")).set(newBadge);

                            Intent intent = new Intent(ActivityAddBadge.this, BadgesActivity.class);
                            startActivity(intent);


                        } else {
                            Log.d("CREATE BADGE", "Count failed: ", task.getException());
                        }
                    }
                });

            }
        });
    }
}

