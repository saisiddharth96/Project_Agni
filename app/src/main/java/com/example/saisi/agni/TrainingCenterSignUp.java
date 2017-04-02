package com.example.saisi.agni;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrainingCenterSignUp extends AppCompatActivity {
    private EditText mEmailId;
    private EditText mpassword;
    private Button mSignUpButton;



    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Error";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_center_sign_up);

        mEmailId = (EditText)findViewById(R.id.CenterEmailId);
        mpassword = (EditText)findViewById(R.id.CenterPasswordSignUp);
        mSignUpButton = (Button)findViewById(R.id.CenterSignUpButton);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainingCenterSignUp.this,MoreTrainingCenterDetails.class));
                //registerCenter();
            }
        });
    }
    public void registerCenter(){
        String email = mEmailId.getText().toString().trim();
        String password = mpassword.getText().toString().trim();

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
                        Toast.makeText(TrainingCenterSignUp.this,"User Registeration Successful",Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(TrainingCenterSignUp.this,MoreTrainingCenterDetails.class));

                        }
                        else
                        {
                            Toast.makeText(TrainingCenterSignUp.this, "User registeration failed please try again",Toast.LENGTH_SHORT );
                        }
                    }
                });


    }
   /* public void addCenter() {
        String name = mCenterName.getText().toString().trim();
        String email = mEmailId.getText().toString().trim();
        String password = mpassword.getText().toString().trim();
        String Address =  mAddress.getText().toString().trim();
        String Timings = mTimings.getText().toString().trim();

        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(TrainingCenterSignUp.this,"User Registeration Successful",Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()){
                            startActivity(new Intent(TrainingCenterSignUp.this,TrainingCenterProfile.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(TrainingCenterSignUp.this, "User registeration failed please try again",Toast.LENGTH_SHORT );
                        }
                    }
                });

        String id = mDatabaseReference.push().getKey();
        CenterDetails centerDetails = new CenterDetails(name,email,password,Address,Timings);
        mDatabaseReference.child("CenterInfo").child(id).setValue(centerDetails);


    }

    public void registerTrainingCenter() {
        String email = mEmailId.getText().toString().trim();
        String password = mpassword.getText().toString().trim();
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



    }
*/
}
