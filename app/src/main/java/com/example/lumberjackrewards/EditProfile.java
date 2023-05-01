package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfile extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_profile);

        Button btnSaveChanges = findViewById(R.id.saveButton);
        ImageButton backBtn = findViewById(R.id.backButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set settings selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_settings);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
           setText(user);
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
                    startActivity(new Intent(getApplicationContext(), ActivitySettings.class));
                    finish();
                    break;
            }
            return true;
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, ActivitySettings.class));
                finish();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges(user);
                startActivity(new Intent(EditProfile.this, ActivitySettings.class));
                finish();
            }
        });
    }

    private void setText(FirebaseUser user){
        if(user != null) {
            String[] temp = user.getDisplayName().split(" ");
            String fName = temp[0].trim();
            String lName = temp[1].trim();
            String eMail = user.getEmail();
            String role = user.getUid();
            EditText f = findViewById(R.id.firstName);
            EditText l = findViewById(R.id.lastName);
            EditText e = findViewById(R.id.email);
            EditText r = findViewById(R.id.role);
            f.setText(fName);
            l.setText(lName);
            e.setText(eMail);
            r.setText(role);
        }
    }

    private void saveChanges(FirebaseUser user) {
        EditText f = findViewById(R.id.firstName);
        EditText l = findViewById(R.id.lastName);
        EditText e = findViewById(R.id.email);
        if(user != null) {
            user.updateEmail(e.getText().toString().trim());
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(f.getText() + " " + l.getText())
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("USER INFO UPDATED", "User profile updated.");
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(getApplicationContext(), ActivitySettings.class));
                                finish();
                            }
                        }
                    });
            // send the newly edited profile changes to settings page
            Intent i = new Intent(getBaseContext(), ActivitySettings.class);
            i.putExtra("fName", f.getText().toString());
            i.putExtra("lName", l.getText().toString());
            i.putExtra("eMail", e.getText().toString());
            startActivity(i);
            finish();
        }

    }
    // the function which triggered when the save changes button is clicked
    // which validates the email address (if altered) entered by the user.
    //public void emailValidator(EditText etMail) {

    // extract the entered data from the email EditText
    //    String emailToText = etMail.getText().toString();

    //    // validate email
    //     if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
    //        Toast.makeText(this, "Email Verified !", Toast.LENGTH_SHORT).show();
    //    } else {
    //        Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
    //    }
    //}



    private boolean validateEmail(String data){
        Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(data);
        return emailMatcher.matches();
    }
}
