package com.example.saisi.agni;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MoreUserDetails extends AppCompatActivity {

    private Button mSaveDetailsButton;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPhoneNumber;

    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_user_details);

        mSaveDetailsButton =(Button) findViewById(R.id.SaveDetailsButton);
        mFirstName = (EditText) findViewById(R.id.FirstName);
        mLastName = (EditText) findViewById(R.id.LastName);
        mPhoneNumber = (EditText) findViewById(R.id.PhoneNumber);


        mSaveDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseClass();
            }
        });

    }
    public void DatabaseClass() {
        final String firstname = mFirstName.getText().toString().trim();
        final String lastname = mLastName.getText().toString().trim();
        final String phonenumber = mPhoneNumber.getText().toString().trim();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://agni-7603c.firebaseio.com/");
        mFirebaseAuth =  FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if (user!= null){
                    mDatabaseReference.child("users").child(user.getUid());
                    mDatabaseReference.child("users").child("FirstName").setValue(firstname);
                    mDatabaseReference.child("users").child("LastName").setValue(lastname);
                    mDatabaseReference.child("users").child("PhoneNumber").setValue(phonenumber);
                }
            }
        };
        startActivity(new Intent(MoreUserDetails.this,UserProfile.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener!=null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

    }
}
