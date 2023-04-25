package com.example.lumberjackrewards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class
MainActivity extends AppCompatActivity {

    private ArrayList<BadgeItemModel> lngList;
    private ArrayAdapter<BadgeItemModel> adapter;
    private ArrayList<BadgeItemModel> pinnedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Dynamically update welcome msg for current, logged-in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            updateTextView(user.getDisplayName());
        }
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //RecyclerView pinnedBadgesView = findViewById(R.id.rvPinnedBadges);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        Log.d("PinnedTest", ""+(pinnedList));
        if(pinnedList != null){
            RecyclerView pinnedBadgesView = findViewById(R.id.rvPinnedBadges);

            //layout manager for badge test
            GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

            //set layout manager
            pinnedBadgesView.setLayoutManager(layoutManager);

            //set adapter
            pinnedBadgesView.setAdapter(new PinnedBadgesAdapter(pinnedList)); //BadgeViewAdapter
        }

        Intent intent = getIntent();
        ArrayList<BadgeItemModel> pinnedBadges = intent.getSerializableExtra("pinnedBadges", new ArrayList<BadgeItemModel>().getClass());
        //String mystring = intent.getStringExtra("myString");
        //Log.d("PinnedTest", "This is my test"  + mystring);
        Log.d("PinnedBadges" ,String.valueOf( pinnedBadges));
        if (pinnedBadges != null) {
            pinnedList = pinnedBadges;
            RecyclerView pinnedBadgesView = findViewById(R.id.rvPinnedBadges);
            Log.d("SomeTest", "hello jj");
            pinnedBadges.forEach((i) -> Log.d("Hello12", i.getName()));
            //layout manager for badge test
            GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

            //set layout manager
            pinnedBadgesView.setLayoutManager(layoutManager);

            //set adapter
            pinnedBadgesView.setAdapter(new BadgeViewAdapter(pinnedBadges));
        }



        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.navigation_badges:
                    startActivity(new Intent(getApplicationContext(),BadgesActivity.class));
                    finish();
                    //overridePendingTransition(0,0);
                    break;
                case R.id.navigation_home:
                    break;
                case R.id.navigation_settings:
                    startActivity(new Intent(getApplicationContext(),Settings.class));
                    finish();
                    //overridePendingTransition(0,0);
                    break;
            }
            return true;
        });
    }
    private void updateTextView(String newText) {
        String welcomeMessage = "Welcome " + newText;
        TextView textView = (TextView) findViewById(R.id.welcomeNameTextView);
        textView.setText(newText);
    }
}