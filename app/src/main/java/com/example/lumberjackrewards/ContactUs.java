package com.example.lumberjackrewards;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ContactUs extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);


        ImageButton btn = findViewById(R.id.backButton);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Set settings selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);
        if(user != null) {
            String[] temp = user.getDisplayName().split(" ");
            EditText firstName = findViewById(R.id.personName);
            firstName.setText(temp[0] + " " + temp[1]);
            EditText contactEmail = findViewById(R.id.contactEmail);
            contactEmail.setText(user.getEmail());
            submitContactForm(user);
        }
        // Perform item selected listener for settings page (nav bar)
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
                    startActivity(new Intent(getApplicationContext(),Settings.class));
                    finish();
                    break;
            }
            return true;
        });

        btn.setOnClickListener(v -> {
            startActivity(new Intent(ContactUs.this, Settings.class));
            finish();
        });

        btnSubmit.setOnClickListener(v -> {
            // calls method to submit the contact form
            submitContactForm(user);
            startActivity((new Intent(getApplicationContext(), Settings.class)));
            finish();
        });
    }

    // Method called once the btnSubmit is pressed.
    // Sends user a pop-up message and redirects to
    // settings page for now.
    private void submitContactForm(FirebaseUser user) {
        //String s = "Submitted!";
        //Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.save_changes,
        //                Snackbar.LENGTH_SHORT)
        //        .show();


    }
}

