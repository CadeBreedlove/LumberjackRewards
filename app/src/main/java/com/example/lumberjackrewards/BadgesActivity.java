package com.example.lumberjackrewards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BadgesActivity extends AppCompatActivity {
    // Badges backend
    private EditText itemEdt;
    private ArrayList<BadgeItemModel> lngList;
    private ArrayAdapter<BadgeItemModel> adapter;
    private FirebaseFirestore db;
    private RecyclerView rvBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);

        // Initialize and assign variable
        rvBadge = findViewById(R.id.rvBadges);
        ArrayList<BadgeItemModel> arrBadges = new ArrayList<>();
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        //testing badge recycle view layout
        for (int i = 1; i <= 40; i++){
            //Add values in array List
            arrBadges.add(new BadgeItemModel(i, "Test description for badge requirements.", "Badge Example " + i, "badge_ex2"));
        }

        //layout manager for badge test
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

        //set layout manager
        rvBadge.setLayoutManager(layoutManager);

        //set adapter
        BadgeViewAdapter badgeViewAdap= new BadgeViewAdapter(arrBadges);
        rvBadge.setAdapter(badgeViewAdap);
        //rvBadge.setAdapter(new BadgeViewAdapter(arrBadges));


        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_badges);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.navigation_home:

                    //Sends back to main activity what badges are pinned
                        ArrayList<BadgeItemModel> pinnedBadges = new ArrayList<>();

                       for(int i = 0; i < badgeViewAdap.getItemCount(); i++){
                          boolean isPinned = badgeViewAdap.arrItemBadges.get(i).getIsPinned();
                           if(isPinned) {
                               pinnedBadges.add(badgeViewAdap.arrItemBadges.get(i));
                           }
                       }
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        if(!pinnedBadges.isEmpty()) {
                            intent.putExtra("pinnedBadges", pinnedBadges);
                        }
                        startActivity(intent);

                        //previous startActivity for testing for different branches to revert back
                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));


                    //overridePendingTransition(0,0);
                    return true;
                case R.id.navigation_badges:
                    break;
                case R.id.navigation_settings:
                    startActivity(new Intent(getApplicationContext(),Settings.class));
                    //overridePendingTransition(0,0);
                    break;
            }
            return true;
        });

        // on below line we are accessing Cloud Firestore instance
        db = FirebaseFirestore.getInstance();

        // on below line we are populating list view
        // with current badges in the database
        displayAllBadges();

        // on below line we are initializing our variables.
        // on below line we are creating variables.

        /*ListView languageLV = findViewById(R.id.idLVLanguages);*/
        Button addBtn = findViewById(R.id.idBtnAdd);
        Button removeBtn = findViewById(R.id.idBtnRmv);
        itemEdt = findViewById(R.id.idEdtItemName);
        lngList = new ArrayList<>();

        // on below line we are adding items to our list
        //lngList.add("C++");
        //lngList.add("Python");

        // on the below line we are initializing the adapter for our list view.
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lngList);

        // on below line we are setting adapter for our list view.
       /* languageLV.setAdapter(adapter);*/

        // on below line we are adding click listener for our button.
        addBtn.setOnClickListener(v -> {
            // on below line we are getting text from edit text
            String item = itemEdt.getText().toString();

            // on below line we are checking if item is not empty
            if (!item.isEmpty()) {

                // on below line we are splitting String item to
                // populate BadgeItemModel
                String[] badgeItemAttributes = item.split(", ");

                // on below line we are populating BadgeItemModel
                // int badgeID, String description, String name, String icon
                BadgeItemModel newBadge = new BadgeItemModel(Integer.parseInt(badgeItemAttributes[0]), badgeItemAttributes[1], badgeItemAttributes[2], badgeItemAttributes[3]);

                // on below line we are adding badge to database
                newBadge.addNewBadgeItem(db);

                // on below line we are adding item to our list.
                lngList.add(newBadge);

                // on below line we are notifying adapter
                // that data in list is updated to
                // update our list view.
                adapter.notifyDataSetChanged();
            }

        });

        removeBtn.setOnClickListener(v -> {
            // on below line we are getting text from edit text
            String item = itemEdt.getText().toString();

            // on below line we are checking if item is not empty
            if (!item.isEmpty()) {

                // on below line we are adding item to our list.
                lngList.remove(item);

                // on below line we are notifying adapter
                // that data in list is updated to
                // update our list view.
                adapter.notifyDataSetChanged();
            }

        });

        //-----------------uncomment(if languageLV will still be used)---------------
        // the onItemClickListener below makes the remove button obsolete
       /* languageLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BadgeItemModel item = adapter.getItem(position);
                item.deleteBadgeItem(db);
                lngList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });*/
    }

    private void displayAllBadges() {
        db.collection("badges")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<BadgeItemModel> badges = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            BadgeItemModel badge = document.toObject((BadgeItemModel.class));
                            badges.add(badge);
                            Log.d("UPDATE_LIST_VIEW", "Successfully refreshed list view");
                        }
                        adapter.addAll(badges);
                    }
                });
    }

}