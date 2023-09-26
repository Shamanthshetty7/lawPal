package com.example.matrix;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso; // Import Picasso for image loading

public class clientProfile extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView profileNameTextView,
            profileEmailTextView, profileGenderTextView, profileDOBTextView,
            profileAadharTextView, profileLocationTextView;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinet_profile);

        // Initialize views
        profileImageView = findViewById(R.id.profileImageView);
        profileNameTextView = findViewById(R.id.profileNameTextView);
        profileEmailTextView = findViewById(R.id.profileEmailTextView);
        profileGenderTextView = findViewById(R.id.profileGenderTextView);
        profileDOBTextView = findViewById(R.id.profileDOBTextView);
        profileAadharTextView = findViewById(R.id.profileAadharTextView);
        profileLocationTextView = findViewById(R.id.profileLocationTextView);

        // Initialize Firebase components
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Clients"); // Change to your table name if needed
        if (currentUser != null) {
            Log.d("UserProfile", "User UID: " + currentUser.getUid());
        } else {
            Log.d("UserProfile", "Current user is null.");
        }
        // Retrieve user profile data from Firebase Realtime Database
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve profile data
                        userprofileconstructor userProfile = dataSnapshot.getValue(userprofileconstructor.class);

                        // Set profile data to the views
                        if (userProfile != null) {
                            // Load and display the user's profile picture using Picasso
                            Picasso.get().load(userProfile.getProfileImageUrl()).into(profileImageView);

                            profileNameTextView.setText(userProfile.getFirstName() + " " + userProfile.getLastName());
                            profileEmailTextView.setText(userProfile.getEmail());
                            profileGenderTextView.setText("Gender: " + userProfile.getGender());
                            profileDOBTextView.setText("DOB: " + userProfile.getDob());
                            profileAadharTextView.setText("Aadhar: " + userProfile.getAadhar());
                            profileLocationTextView.setText("Location: " + userProfile.getLocation());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors here
                }
            });
        }
    }
}
