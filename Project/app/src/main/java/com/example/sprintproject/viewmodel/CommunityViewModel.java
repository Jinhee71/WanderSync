package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.TravelCommunity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CommunityViewModel extends ViewModel {

    private final MutableLiveData<List<TravelCommunity>> travelPosts = new MutableLiveData<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CommunityViewModel() {
        // Listen for real-time updates from Firestore
        db.collection("CommunityPost")
                .orderBy("timestamp")  // Optional: Order by timestamp for recent posts
                .addSnapshotListener((QuerySnapshot snapshots, FirebaseFirestoreException e) -> {
                    if (e != null) {
                        return;
                    }
                    // Update live data when Firestore data changes
                    List<TravelCommunity> posts = snapshots.toObjects(TravelCommunity.class);
                    travelPosts.setValue(posts);  // Set the new posts to the live data
                });
    }

    // Get live data of travel posts
    public LiveData<List<TravelCommunity>> getTravelPosts() {
        return travelPosts;
    }

    // Add a new post to Firestore
    public void addNewPost(TravelCommunity post) {
        db.collection("CommunityPost").add(post)
                .addOnSuccessListener(documentReference -> {
                    // Post successfully added
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }
}
