package com.example.lumberjackrewards;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        //ImageView logoutBtn = (ImageView)findViewById(R.id.logoutButton);
        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);
        Button btnEditProfile = (Button)findViewById(R.id.btnEditProfile);
        ImageButton btnTextSize = (ImageButton) findViewById(R.id.textSizeButton);
        ImageButton btnSecurity_Privacy = (ImageButton) findViewById(R.id.securityPrivacyButton);
        ImageButton btnContactUs = (ImageButton) findViewById(R.id.contactUsButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set settings selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            updateEmailText(user.getEmail());
            updateNameText(user.getDisplayName());
        }



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
                    break;
            }
            return true;
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, EditProfile.class));
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MainActivity.class));
            }
        });

        //logoutBtn.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        FirebaseAuth.getInstance().signOut();
        //        Intent intent = new Intent(Settings.this, LoginActivity.class);
        //        startActivity(intent);
        //    }
        //});

        btnTextSize.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Settings_Text_Size.class));
            }
        });

        btnSecurity_Privacy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Settings_Security_Privacy.class));
            }
        });

        btnContactUs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ContactUs.class));
            }
        });
    }

    private void updateNameText(String name) {
        TextView settingsNameplate = (TextView) findViewById(R.id.settings_nameplate);
        settingsNameplate.setText(name);
    }

    private void updateEmailText(String phone) {
        TextView email = (TextView) findViewById(R.id.settingsEmailDisplay);
        email.setText(phone);
    }
}

