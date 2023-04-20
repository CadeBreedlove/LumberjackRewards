package com.example.lumberjackrewards;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity {
    private FirebaseFirestore db;

    public void GiveAdmin(String userID) {
        // Get the collection reference
        db = FirebaseFirestore.getInstance();

        CollectionReference UsersRef = db.collection("users");

        // Create a query to search for the document with the unique field value
        Query query = UsersRef.whereEqualTo("userID", userID);

        // Execute the query asynchronously
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String documentId = document.getId();
                    String badgeName = document.getString("name");
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("admin", true);
                    UsersRef.document(documentId).update(docData);
                }
            }
        });
    }
}
