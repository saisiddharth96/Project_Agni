package com.example.saisi.agni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MoreTrainingCenterDetails extends AppCompatActivity {
    private EditText mAddress;
    private EditText mCenterName;
    private EditText mTimings;
    private Button mEnterDetails;


    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_training_center_details);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        mCenterName = (EditText)findViewById(R.id.CenterName);
        mAddress = (EditText)findViewById(R.id.CenterAddress);
        mTimings = (EditText)findViewById(R.id.CenterTimings);
        mEnterDetails = (Button)findViewById(R.id.EnterDetailsButton);

        mEnterDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDetails();
            }
        });
    }

    private void sendDetails() {
        String centerName = mCenterName.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        String timings = mTimings.getText().toString().trim();


        if(!TextUtils.isEmpty(centerName)){
            String id = mDatabaseReference.push().getKey();
            CourseDetails details = new CourseDetails(centerName,address,timings);
            mDatabaseReference.child("Center Details").child(id).setValue(details);


        }
        startActivity(new Intent(getApplicationContext(),TrainingCenterProfile.class));
        finish();
    }
}
