package com.example.lumberjackrewards;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class GroupActivity {
    private FirebaseFirestore db;

    public void AssignBadgeToGroup(String groupID, String badgeId) {
        // Get the collection reference
        db = FirebaseFirestore.getInstance();

        CollectionReference badgesRef = db.collection("badges");

        // Create a query to search for the document with the unique field value
        Query query = badgesRef.whereEqualTo("badgeID", badgeId);

        // Execute the query asynchronously
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String documentId = document.getId();
                    String badgeName = document.getString("name");
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("name", badgeName);
                    docData.put("badgeID", badgeId);

                    db.collection("groups").document(groupID).collection("badges").document(documentId)
                            .set(docData);
                }
            }
        });
    }

    public void DeleteBadgeFromGroup(String groupID, String badgeID) {
        db.collection("groups").document(groupID).collection("badges").whereEqualTo("badgeID", badgeID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("groups").document(groupID).collection("badges").document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("DELETED", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("ERROR", "Error deleting document", e);
                                            }
                                        });

                            }
                        } else {
                            Log.d("ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void AssignUserToGroup(String groupID, String userId) {
        // Get the collection reference
        db = FirebaseFirestore.getInstance();

        CollectionReference UsersRef = db.collection("users");

        // Create a query to search for the document with the unique field value
        Query query = UsersRef.whereEqualTo("userID", userId);

        // Execute the query asynchronously
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String documentId = document.getId();
                    String badgeName = document.getString("name");
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("name", badgeName);
                    docData.put("userID", userId);

                    db.collection("groups").document(groupID).collection("users").document(documentId)
                            .set(docData);
                }
            }
        });
    }

    public void DeleteUserFromGroup(String groupID, String userID) {
        db.collection("groups").document(groupID).collection("users").whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("groups").document(groupID).collection("users").document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("DELETED", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("ERROR", "Error deleting document", e);
                                            }
                                        });

                            }
                        } else {
                            Log.d("ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
