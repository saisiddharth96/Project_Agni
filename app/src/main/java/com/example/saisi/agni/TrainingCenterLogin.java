package com.example.saisi.agni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TrainingCenterLogin extends AppCompatActivity {

    EditText mCenterUserName;
    EditText mCenterPassword;
    Button mCenterLoginButton;
    FirebaseAuth mFirebaseAuthentication;
    //FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_center_login);

       mFirebaseAuthentication = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuthentication.getCurrentUser();

        mCenterUserName = (EditText) findViewById(R.id.CenterUserName);
        mCenterPassword = (EditText) findViewById(R.id.CenterPassword);
        mCenterLoginButton = (Button)findViewById(R.id.CenterLoginButton);

        mCenterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CenterLogin();
                startActivity(new Intent(TrainingCenterLogin.this,TrainingCenterProfile.class));
            }
        });
    }

    public void CenterLogin() {
        String email = mCenterUserName.getText().toString().trim();
        String password = mCenterPassword.getText().toString().trim();

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

        mFirebaseAuthentication.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(TrainingCenterLogin.this,"User Registeration Successful",Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(TrainingCenterLogin.this,TrainingCenterProfile.class));


                        }
                        else
                        {
                            Toast.makeText(TrainingCenterLogin.this, "User registeration failed please try again",Toast.LENGTH_SHORT );
                        }
                    }
                });
    }

}

