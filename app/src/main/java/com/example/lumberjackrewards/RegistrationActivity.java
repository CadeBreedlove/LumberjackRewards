package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText RegEmail, RegPwd;
    private Button RegBtn;
    private TextView RegQn;
    private TextView RegFName;
    private TextView RegLName;
    private FirebaseFirestore db;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        RegEmail = findViewById(R.id.RegistrationEmail);
        RegPwd = findViewById(R.id.RegistrationPassword);
        RegBtn = findViewById(R.id.RegistrationButton);
        RegQn = findViewById(R.id.RegistrationPageQuestion);
        RegFName = findViewById(R.id.registrationFirstName);
        RegLName = findViewById(R.id.registrationLastName);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Redirect user to login page if
        // account has already been created
        RegQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Register user in the system
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // removes whitespace from both ends of the strings
                String email = RegEmail.getText().toString().trim();
                String password = RegPwd.getText().toString().trim();
                String fName = RegFName.getText().toString().trim();
                String lName = RegLName.getText().toString().trim();

                // ensure fields are filled out
                if (TextUtils.isEmpty(email)){
                    RegEmail.setError("A valid email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    RegPwd.setError("A valid password is required");
                    return;
                }
                if (TextUtils.isEmpty(fName)){
                    RegPwd.setError("A valid first name is required");
                    return;
                }
                if (TextUtils.isEmpty(lName)){
                    RegPwd.setError("A valid last name is required");
                    return;
                } else{
                    /*loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();*/
                    Query query = db.collection("users");


                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                updateUserProfile(fName, lName);
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else{
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this, "Registration failed: " + error, Toast.LENGTH_SHORT).show();
                                Log.d("REGISTER", "Registration failed: " + error);
                            }
                            //loader.dismiss();
                        }
                    });
                }
            }
        });
    }

    // Add Display Name to newly-registered user
    private void updateUserProfile(String fName, String lName){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fName + " " + lName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("USER INFO UPDATED", "User profile updated.");
                        }
                    }
                });
    }
}

