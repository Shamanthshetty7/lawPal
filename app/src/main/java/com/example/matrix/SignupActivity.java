package com.example.matrix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userType = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ImageView selectedImageView; // Variable to keep track of the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        EditText emailEdittext = findViewById(R.id.loginemailid);
        EditText passwordEditText = findViewById(R.id.loginpassword);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        TextView logintxt = findViewById(R.id.logintxt);
        Button signupbtn = findViewById(R.id.loginButton);

        // Initialize the selectedImageView to the Advocate image by default
        selectedImageView = findViewById(R.id.advocateimage);

        ImageView advocateImageView = findViewById(R.id.advocateimage);
        advocateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userType = "Advocate"; // Set the user type to Advocate
                setSelectedImage(advocateImageView);
            }
        });

        ImageView clientImageView = findViewById(R.id.clientimage);
        clientImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userType = "Client"; // Set the user type to Client
                setSelectedImage(clientImageView);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEdittext.getText().toString();
                String passwrd = passwordEditText.getText().toString();
                String repasswrd = confirmPasswordEditText.getText().toString();
                if (!email.matches(emailPattern)) {
                    emailEdittext.setError("Enter proper Email");
                } else if (passwrd.isEmpty() || passwrd.length() < 6) {
                    passwordEditText.setError("Enter proper Password");
                } else if (!passwrd.equals(repasswrd)) {
                    confirmPasswordEditText.setError("Password doesn't match");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference usersRef = database.getReference("users");
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                    if (currentUser != null) {
                        String userEmail = currentUser.getEmail();
                         // Set this based on the user's choice (Advocate or Client)

                        // Use the email as the UID (make sure to sanitize it to remove invalid characters)
                        String sanitizedEmail = sanitizeEmail(userEmail);
                        // Create a new user profile node or update an existing one

                        DatabaseReference userProfileRef = usersRef.child(sanitizedEmail);

                        userProfileRef.child("email").setValue(userEmail);
                        userProfileRef.child("userType").setValue(userType);
                    }

                    mAuth.createUserWithEmailAndPassword(email, passwrd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Intent intent = null;
                                    if (task.isSuccessful()) {
                                        if (userType.equals("Advocate")) {

                                            intent = new Intent(SignupActivity.this, AdvProfileCreation.class);
                                            intent.putExtra("usertype","Advocate");
                                        } else if (userType.equals("Client")) {

                                            intent = new Intent(SignupActivity.this, UserProfileCreation.class);
                                            intent.putExtra("usertype","Client");
                                        } else {
                                            Toast.makeText(SignupActivity.this, "Please select User type", Toast.LENGTH_SHORT).show();
                                        }

                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignupActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private String sanitizeEmail(String email) {
        // Remove invalid characters from the email and replace them with underscores
        return email.replaceAll("[^a-zA-Z0-9]", "_");
    }
    // Function to set the selected image and fade the unselected one
    private void setSelectedImage(ImageView selectedImage) {
        if (selectedImageView != null) {
            selectedImageView.setAlpha(0.5f); // Reduce alpha of the unselected image
        }
        selectedImageView = selectedImage;
        selectedImageView.setAlpha(1.0f); // Set alpha of the selected image to fully visible
    }
}
