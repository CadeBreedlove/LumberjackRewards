package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityManage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ImageButton btn = findViewById(R.id.backButton);
        Button btnManageClasses = findViewById(R.id.btnManageClasses);
        Button btnManageStudents = findViewById(R.id.btnManageStudents);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Perform item selected listener for settings page (nav bar)
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_badges:
                    startActivity(new Intent(getApplicationContext(), ActivityBadges.class));
                    //overridePendingTransition(0,0);
                    break;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
                case R.id.navigation_settings:
                    startActivity(new Intent(getApplicationContext(), ActivitySettings.class));
                    break;
            }
            return true;
        });
        btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ActivityBadges.class));
            finish();
        });
        btnManageClasses.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ManageClasses.class));
            finish();
        });
        btnManageStudents.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ManageStudents.class));
            finish();
        });
    }
}
