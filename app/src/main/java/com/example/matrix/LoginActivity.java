package com.example.matrix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText email,password;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         email=findViewById(R.id.loginemailid);
         password=findViewById(R.id.loginpassword);
        Button login=findViewById(R.id.loginButton);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString();
                String Pass=password.getText().toString();
                if(!Email.matches(emailPattern)){
                    email.setError("Enter proper Email");
                }else if(Pass.isEmpty()||Pass.length()<6){
                    password.setError("Enter proper Password");
                }else{
              mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Intent intent=null;
                          Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                          // Inside LoginActivity, after a successful login
                          // Assuming you have already initialized Firebase
                          FirebaseDatabase database = FirebaseDatabase.getInstance();
                          DatabaseReference usersRef = database.getReference("users");
                          String sanitizedEmail = sanitizeEmail(Email);
                          Query query = usersRef.child(sanitizedEmail);


                          query.addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                      if (dataSnapshot.exists()) {
                                          String userType = dataSnapshot.child("userType").getValue(String.class);

                                          if ("Advocate".equals(userType)) {
                                              // Navigate to Advocate's home page
                                              Intent intent = new Intent(LoginActivity.this, clientAdvocateInteraction.class);
                                              startActivity(intent);
                                          } else if ("Client".equals(userType)) {
                                              // Navigate to Client's home page
                                              Intent intent = new Intent(LoginActivity.this, clientAdvocateInteraction.class);
                                              startActivity(intent);
                                          } else {
                                              Toast.makeText(LoginActivity.this, "Something went wrong"+userType, Toast.LENGTH_SHORT).show();
                                          }
                                      }else{
                                          Toast.makeText(LoginActivity.this, "doesnt exist ", Toast.LENGTH_SHORT).show();
                                      }
                                  }

                                  @Override
                                  public void onCancelled(@NonNull DatabaseError databaseError) {
                                      // Handle errors
                                  }
                              });




                      }else{
                          Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                      }
                  }
              });
                }
            }
        });

    }
    private String sanitizeEmail(String email) {
        // Remove invalid characters from the email and replace them with underscores
        return email.replaceAll("[^a-zA-Z0-9]", "_");
    }
}


