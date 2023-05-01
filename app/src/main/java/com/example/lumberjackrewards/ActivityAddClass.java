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
        setContentView(R.layout.activity_add_class);

        db = FirebaseFirestore.getInstance();
        badgeName = findViewById(R.id.badgeName);
        badgeClassName = findViewById(R.id.className);
        badgeDescription = findViewById(R.id.badgeDescription);
        createBadge = findViewById(R.id.createBadge);
        returnButton = findViewById(R.id.backButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        // Set badges selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_badges);

        // Perform item selected listener for settings page
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_badges:
                    startActivity(new Intent(getApplicationContext(), ActivityBadges.class));
                    finish();
                    //overridePendingTransition(0,0);
                    break;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    break;
                case R.id.navigation_settings:
                    startActivity(new Intent(getApplicationContext(), ActivitySettings.class));
                    finish();
            }
            return true;
        });

        // Return to activity_badge.xml
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ActivityBadges.class));
                finish();
            }
        });
    }
}
