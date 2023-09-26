package com.example.matrix;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class clientAdvocateInteraction extends AppCompatActivity {

    private ListView advocateListView;
    private AdvocateAdapter advocateAdapter;
    private DatabaseReference advocatesRef;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advocate_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        advocateListView = findViewById(R.id.advocate_list_view);
        advocateAdapter = new AdvocateAdapter(this, new ArrayList<Advocate>());
        advocateListView.setAdapter(advocateAdapter);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        advocatesRef = rootRef.child("Advocates");

        fetchAdvocateDetails();
    }

    private void fetchAdvocateDetails() {
        advocatesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Advocate> advocateList = new ArrayList<>();

                for (DataSnapshot advocateSnapshot : dataSnapshot.getChildren()) {
                    String name = advocateSnapshot.child("name").getValue(String.class);
                    String location = advocateSnapshot.child("location").getValue(String.class);
                    String experience = advocateSnapshot.child("yearOfExperience").getValue(String.class);
                    String language = advocateSnapshot.child("language").getValue(String.class);
                    String practiceAreas = advocateSnapshot.child("areaOfPractice").getValue(String.class);
                    String amount = "30/min";
                    String imageurl=advocateSnapshot.child("profileImageUrl").getValue(String.class);
                    if (name != null && location != null && amount != null) {
                        // Proceed with creating Advocate object and adding to the list
                        Advocate advocate = new Advocate(name, location, amount,experience,language,practiceAreas,imageurl);
                        advocateList.add(advocate);
                    }


                }

                advocateAdapter.clear();
                advocateAdapter.addAll(advocateList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}