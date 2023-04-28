package com.example.lumberjackrewards;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_settings);

        ImageView logoutBtn = (ImageView) findViewById(R.id.logoutButton);
        ImageButton backBtn = (ImageButton) findViewById(R.id.backButton);
        Button btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        ImageButton btnTextSize = (ImageButton) findViewById(R.id.textSizeButton);
        ImageButton btnSecurity_Privacy = (ImageButton) findViewById(R.id.securityPrivacyButton);
        ImageButton btnContactUs = (ImageButton) findViewById(R.id.contactUsButton);
        ImageButton btnFAQ = (ImageButton) findViewById(R.id.faqButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        // Set settings selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Bundle parameters = getIntent().getExtras();
            if (parameters != null) {
                updateNameText(parameters.getString("fName").trim() + " " + parameters.getString("lName").trim());
                updateEmailText(parameters.getString("eMail").trim());
            } else {
                updateEmailText(user.getEmail());
                updateNameText(user.getDisplayName());
            }


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
                        break;
                }
                return true;
            });

            btnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.this, EditProfile.class));
                    finish();
                }
            });
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Set settings selected
                    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    startActivity(new Intent(Settings.this, MainActivity.class));
                    finish();
                }
            });

            // logging out gives you a notice before confirming
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setMessage("You are about to sign out.");
                    builder.setTitle("Notice");
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(Settings.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            btnTextSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Settings_Text_Size.class));
                    finish();
                }
            });

            btnSecurity_Privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Settings_Security_Privacy.class));
                    finish();
                }
            });

            btnContactUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), ContactUs.class));
                    finish();
                }
            });

            btnFAQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.this, FAQ.class));
                    finish();
                }
            });
        }
    }
    private void updateNameText(String name) {
        TextView settingsNameplate = (TextView) findViewById(R.id.settings_nameplate);
        settingsNameplate.setText(name);
    }

    private void updateEmailText(String emailText) {
        TextView email = (TextView) findViewById(R.id.settingsEmailDisplay);
        email.setText(emailText);
    }
}
