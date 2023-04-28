package com.example.lumberjackrewards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BadgeItemModel> lngList;
    private ArrayAdapter<BadgeItemModel> adapter;
    private ArrayList<BadgeItemModel> pinnedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //RecyclerView pinnedBadgesView = findViewById(R.id.rvPinnedBadges);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        //TO be deleted to below --------
        //Log.d("PinnedTest", ""+(pinnedList));
        /*if(pinnedList != null){
            RecyclerView pinnedBadgesView = findViewById(R.id.rvPinnedBadges);

            //layout manager for badge test
            GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

            //set layout manager
            pinnedBadgesView.setLayoutManager(layoutManager);

            //set adapter
            pinnedBadgesView.setAdapter(new PinnedBadgesAdapter(pinnedList)); //BadgeViewAdapter
        }*/
        //delete above here ---------

        //pinned badges
        Intent intent = getIntent();
        ArrayList<BadgeItemModel> pinnedBadges = intent.getSerializableExtra("pinnedBadges", new ArrayList<BadgeItemModel>().getClass());


        //sets variables needed for empty or filled pinnedBadges view
        RecyclerView pinnedBadgesView = findViewById(R.id.rvPinnedBadges);
        LinearLayout emptyPinnedBadgesView = findViewById(R.id.tvNoPinnedBadges);

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int pinnedBadgesViewPixels = (int)(355 * scale + 0.5f);

        if (pinnedBadges != null) {
            pinnedList = pinnedBadges;

            //layout manager for badge test
            GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

            //set layout manager
            pinnedBadgesView.setLayoutManager(layoutManager);

            //set adapter
            pinnedBadgesView.setAdapter(new BadgeViewAdapter(pinnedBadges));

            //sets emptyPinnedBadges layout parameters
            emptyPinnedBadgesView.setLayoutParams(new LinearLayout.LayoutParams(0,0));

            //sets pinnedBadges layout parameters
            pinnedBadgesView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pinnedBadgesViewPixels));

            //changes visibility for placeholder
            emptyPinnedBadgesView.setVisibility(View.INVISIBLE);
            pinnedBadgesView.setVisibility(View.VISIBLE);


        }else{
            //sets homepage with empty pinned badges
            emptyPinnedBadgesView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pinnedBadgesViewPixels));
            pinnedBadgesView.setLayoutParams(new LinearLayout.LayoutParams(0,0));

            //placeholder visibility change
            emptyPinnedBadgesView.setVisibility(View.VISIBLE);
            pinnedBadgesView.setVisibility(View.INVISIBLE);
        }



        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.navigation_badges:
                startActivity(new Intent(getApplicationContext(),BadgesActivity.class));
                //overridePendingTransition(0,0);
                break;
                case R.id.navigation_home:
                break;
                case R.id.navigation_settings:
                startActivity(new Intent(getApplicationContext(),Settings.class));
                //overridePendingTransition(0,0);
                break;
            }
            return true;
        });

    }
    }
