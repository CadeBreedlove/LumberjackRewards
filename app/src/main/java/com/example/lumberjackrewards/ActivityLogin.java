package com.example.lumberjackrewards;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ActivityLogin extends AppCompatActivity {
    private EditText LoginEmail, LoginPwd;
    private Button LoginBtn;
    private TextView LoginQn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


        // If user is already logged in already go to homepage
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        LoginEmail = findViewById(R.id.loginEmail);
        LoginPwd = findViewById(R.id.loginPassword);
        LoginBtn = findViewById(R.id.loginButton);
        LoginQn = findViewById(R.id.loginPageQuestion);

        // Redirect user to registration page if
        // account has not already been created
        LoginQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegistration.class);
                startActivity(intent);
            }
        });

        // Authenticate user and login to home page
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = LoginEmail.getText().toString().trim();
                String password = LoginPwd.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    LoginEmail.setError("A valid email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    LoginPwd.setError("A valid password is required");
                    return;
                } else{

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else{
                                String error = task.getException().toString();
                                Toast.makeText(ActivityLogin.this, "Login failed: " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}
