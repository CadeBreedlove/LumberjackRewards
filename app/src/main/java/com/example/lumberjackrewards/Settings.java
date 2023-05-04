package com.example.lumberjackrewards;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {
    SwitchCompat a;
    private static final String IS_DARK = "IS_DARK";
    private static String MY_PREFS = "switch_prefs";
    private static String SWITCH_STATUS = "switch_status";
    boolean switch_status;


    SharedPreferences prefs;
    SharedPreferences.Editor myEditor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        super.onCreate(savedInstanceState);
        Button btn = (Button)findViewById(R.id.backButton);
        ImageButton btnTextSize = (ImageButton) findViewById(R.id.searchImageButton);
        ImageButton btnSecurity_Privacy = (ImageButton) findViewById(R.id.securityPrivacyButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        // Set settings selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);

        // Perform item selected listener for settings page
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.navigation_badges:
                    startActivity(new Intent(getApplicationContext(),BadgesActivity.class));
                    finish();
                    //overridePendingTransition(0,0);
                    break;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                    break;
                case R.id.navigation_settings:
                    break;
            }
            return true;
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, MainActivity.class));
                finish();
            }
        });

        btnTextSize.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Settings_Text_Size.class));
                finish();
            }
        });

        btnSecurity_Privacy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Settings_Security_Privacy.class));
                finish();
            }
        });
    }


}


