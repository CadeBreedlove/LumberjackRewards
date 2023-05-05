package com.example.lumberjackrewards;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivitySettings extends AppCompatActivity {
    SwitchCompat a;
    private static final String IS_DARK = "IS_DARK";
    private static String MY_PREFS = "switch_prefs";
    private static String SWITCH_STATUS = "switch_status";
    boolean switch_status;


    SharedPreferences prefs;
    SharedPreferences.Editor myEditor;
    @Override
    protected void attachBaseContext(Context baseContext) {
        super.attachBaseContext(baseContext);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        boolean isDark = prefs.getBoolean(IS_DARK, false);

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        a = findViewById(R.id.mode);
        prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        myEditor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        switch_status = prefs.getBoolean(SWITCH_STATUS, false);
        a.setChecked(switch_status);

            a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isChecked()) { // switch gets checked and night mode is off
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // turns on night mode
                        myEditor.putBoolean(SWITCH_STATUS, true);
                        myEditor.putBoolean(IS_DARK, true);
                        myEditor.apply();
                        a.setChecked(true);
                    } else { // switch is unchecked, then switch off night mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        myEditor.putBoolean(SWITCH_STATUS, false);
                        myEditor.putBoolean(IS_DARK, false);
                        myEditor.apply();
                        a.setChecked(false);
                    }
                }
            });
        ImageView logoutBtn = findViewById(R.id.logoutButton);
        ImageButton backBtn = findViewById(R.id.backButton);
        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        ImageButton btnTextSize = findViewById(R.id.textSizeButton);
        ImageButton btnSecurity_Privacy = findViewById(R.id.securityPrivacyButton);
        ImageButton btnContactUs = findViewById(R.id.contactUsButton);
        ImageButton btnFAQ = findViewById(R.id.faqButton);

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
                        startActivity(new Intent(getApplicationContext(), ActivityBadges.class));
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
                    startActivity(new Intent(ActivitySettings.this, EditProfile.class));
                    finish();
                }
            });
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Set settings selected
                    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    startActivity(new Intent(ActivitySettings.this, MainActivity.class));
                    finish();
                }
            });

            // logging out gives you a notice before confirming
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySettings.this);
                    builder.setMessage("You are about to sign out.");
                    builder.setTitle("Notice");
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ActivitySettings.this, ActivityLogin.class);
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
                    startActivity(new Intent(ActivitySettings.this, ActivityFAQ.class));
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