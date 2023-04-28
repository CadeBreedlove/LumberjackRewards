package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManageClasses extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_groups);
        Button btn = findViewById(R.id.backButton);
        Button btnAddClass = findViewById(R.id.btnAddClass);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Perform item selected listener for settings page (nav bar)
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
                    break;
            }
            return true;
        });
        btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ActivityManage.class));
            finish();
        });
        btnAddClass.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ActivityAddClass.class));
            finish();
        });
    }

}
