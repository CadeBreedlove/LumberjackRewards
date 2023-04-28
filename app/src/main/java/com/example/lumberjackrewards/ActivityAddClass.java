package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityAddClass extends AppCompatActivity {
    private EditText badgeName;
    private EditText badgeClassName;
    private EditText badgeDescription;
    private FirebaseFirestore db;
    private Button createBadge;
    private ImageButton returnButton;
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        db = FirebaseFirestore.getInstance();
        badgeName = findViewById(R.id.etAddBadgeName);
        badgeClassName = findViewById(R.id.etAddGroupName);
        badgeDescription = findViewById(R.id.etAddBadgeDescription);
        createBadge = findViewById(R.id.btnAddBadgeSubmit);
        returnButton = findViewById(R.id.backButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        // Set badges selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_badges);

        // Perform item selected listener for settings page
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_badges:
                    startActivity(new Intent(getApplicationContext(), BadgesActivity.class));
                    finish();
                    //overridePendingTransition(0,0);
                    break;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    break;
                case R.id.navigation_settings:
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    finish();
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
                startActivity(new Intent(getApplicationContext(), BadgesActivity.class));
                finish();
            }
        });
    }
}
