package com.example.matrix;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdvProfileCreation extends AppCompatActivity {

    private EditText AdvName,mobileEditText, emailEditText, aadharEditText, locationEditText, lawSchoolEditText, yearOfGraduationEditText,
            areaOfPracticeEditText, websiteEditText, officeAddressEditText, introductionEditText, yearOfExperienceEditText, awardsRecognitionEditText;
    private Spinner genderSpinner;
    private Button submitButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    String selectedGender;
    private Button buttonUploadPicture;
    private ImageView imageViewProfile;
    private Uri selectedImageUri;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advprofilecreation);
        Spinner spinnerGender = findViewById(R.id.spinnerGender);

        // Define the list of gender options
        String[] genderOptions = {"Male", "Female", "Other"};

        // Create an ArrayAdapter to populate the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);

        // Set the dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for the Spinner
        spinnerGender.setAdapter(adapter);

        // Set an item click listener for the Spinner
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected gender
                selectedGender = genderOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle no selection if needed
            }
        });

        // Initialize Firebase Authentication and Database references
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");
        mDatabase = FirebaseDatabase.getInstance().getReference("Advocates"); // Change to "Advocates" if needed
        AdvName=findViewById(R.id.editTextAdvName);
        // Initialize views
        mobileEditText = findViewById(R.id.editTextMobile);
        emailEditText = findViewById(R.id.clientEmail);
        genderSpinner = findViewById(R.id.spinnerGender);
        aadharEditText = findViewById(R.id.editTextAadhar);
        locationEditText = findViewById(R.id.editTextLocation);
        lawSchoolEditText = findViewById(R.id.editTextLawSchool);
        yearOfGraduationEditText = findViewById(R.id.editTextYearOfGraduation);
        areaOfPracticeEditText = findViewById(R.id.editTextAreaOfPractice);
        websiteEditText = findViewById(R.id.editTextWebsite);
        officeAddressEditText = findViewById(R.id.editTextOfficeAddress);
        introductionEditText = findViewById(R.id.editTextIntroduction);
        yearOfExperienceEditText = findViewById(R.id.editTextYearOfExperience);
        awardsRecognitionEditText = findViewById(R.id.editTextAwardsRecognition);
        imageViewProfile = findViewById(R.id.clientimageViewProfile);
        buttonUploadPicture = findViewById(R.id.clientbuttonUploadPicture);
        buttonUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a file picker to select an image
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        submitButton = findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values from the views
                String name=AdvName.getText().toString() ;
                String mobile = mobileEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String gender = genderSpinner.getSelectedItem().toString();
                String aadhar = aadharEditText.getText().toString();
                String location = locationEditText.getText().toString();
                String lawSchool = lawSchoolEditText.getText().toString();
                String yearOfGraduation = yearOfGraduationEditText.getText().toString();
                String areaOfPractice = areaOfPracticeEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String officeAddress = officeAddressEditText.getText().toString();
                String introduction = introductionEditText.getText().toString();
                String yearOfExperience = yearOfExperienceEditText.getText().toString();
                String awardsRecognition = awardsRecognitionEditText.getText().toString();

                // Create a UserProfile object with the values
                advprofileConstructer userProfile = new advprofileConstructer();
                userProfile.setUserId(currentUser.getUid()); // Use Firebase Auth UID as the unique identifier
                userProfile.setName(name);
                userProfile.setMobile(mobile);
                userProfile.setEmail(email);
                userProfile.setGender(gender);
                userProfile.setAadhar(aadhar);
                userProfile.setLocation(location);
                userProfile.setLawSchool(lawSchool);
                userProfile.setYearOfGraduation(yearOfGraduation);
                userProfile.setAreaOfPractice(areaOfPractice);
                userProfile.setWebsite(website);
                userProfile.setOfficeAddress(officeAddress);
                userProfile.setIntroduction(introduction);
                userProfile.setYearOfExperience(yearOfExperience);
                userProfile.setAwardsRecognition(awardsRecognition);

                // Upload the selected image to Firebase Storage
                if (selectedImageUri != null) {
                    uploadImage(selectedImageUri, currentUser.getUid(), userProfile);
                } else {
                    // If no image is selected, you can choose to show an error message or proceed without an image.
                }
            }
        });
    }

    // Handle the result of the image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageViewProfile.setImageURI(selectedImageUri);
        }
    }

    // Upload the image to Firebase Storage
    private void uploadImage(Uri imageUri, String userId, final advprofileConstructer userProfile) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        // Create a reference to the user's profile image
        StorageReference profileImageRef = storageReference.child(userId + ".jpg");

        profileImageRef.putFile(imageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Image uploaded successfully, get its URL
                            profileImageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();

                                        // Update the user's profile with the image URL
                                        userProfile.setProfileImageUrl(downloadUri.toString());

                                        // Upload the user profile to Firebase Database
                                        mDatabase.child(userId).setValue(userProfile)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // Profile and image URL created successfully
                                                            Toast.makeText(AdvProfileCreation.this, "Profile created successfully!", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(AdvProfileCreation.this, AdvocatesHomePage.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Toast.makeText(AdvProfileCreation.this, "Failed to create profile.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        // Handle the error
                                        Toast.makeText(AdvProfileCreation.this, "Failed to get image URL.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // Handle the error
                            Toast.makeText(AdvProfileCreation.this, "Failed to upload image.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
