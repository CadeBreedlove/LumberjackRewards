package com.example.lumberjackrewards;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;


public class Settings_Security_Privacy extends AppCompatActivity {
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_security_privacy);


        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);

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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings_Security_Privacy.this, Settings.class));
            }
        });
    }
}
