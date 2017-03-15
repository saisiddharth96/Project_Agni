package com.example.saisi.agni;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ButtonBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.authentication.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class MainActivity extends AppCompatActivity {
    private EditText mEmailID;
    private EditText mPassword;
    private Button mLoginButton;
    private Button mSignUpButton;
    private Button mTrainingCenterLogin;
    private ProgressBar mProgressBar;

    //FireBase instance variables

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Firebase mFirebase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginButton =(Button) findViewById(R.id.LoginButton);
        mSignUpButton = (Button) findViewById(R.id.SignUpButton);
        mTrainingCenterLogin = (Button) findViewById(R.id.centerLoginButton);
        mProgressBar = (ProgressBar)findViewById(R.id.progress_circular);
        mEmailID = (EditText)findViewById(R.id.EmailID);
        mPassword = (EditText)findViewById(R.id.password);


        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        mTrainingCenterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TrainingCenterLogin.class);
                startActivity(intent);
            }
        });
        
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }

    public void registerUser(){
        String email = mEmailID.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(MainActivity.this,"User Registeration Successful",Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()){
                      startActivity(new Intent(MainActivity.this,MoreUserDetails.class));
                       finish();
                   }
                   else
                   {
                      Toast.makeText(MainActivity.this, "User registeration failed please try again",Toast.LENGTH_SHORT );
                   }
                    }
                });


    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mFirebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),UserProfile.class));
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener!=null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

    }
}



