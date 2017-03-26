package com.example.saisi.agni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrainingCenterProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText mCourseName;
    private EditText mCourseDetails;
    private Button mSendDetailsButton;
    private TextView mUserNameText;
    private TextView mEmailIDText;


    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "GoogleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_center_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        mCourseName = (EditText)findViewById(R.id.courseName);
        mCourseDetails = (EditText)findViewById(R.id.courseDetails);
        mSendDetailsButton = (Button)findViewById(R.id.sendInfoButton);
        mUserNameText = (TextView) findViewById(R.id.UserNameNav);
        mEmailIDText = (TextView) findViewById(R.id.userEmailId);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mSendDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNewCourseDetails();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    mUserNameText.setText(mFirebaseAuth.getCurrentUser().getEmail());
                    mEmailIDText.setText(mFirebaseAuth.getCurrentUser().getDisplayName());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

    }

    private void sendNewCourseDetails() {
        String cName = mCourseName.getText().toString().trim();
        String cDetails = mCourseDetails.getText().toString().trim();


        if(!TextUtils.isEmpty(cName)&& !TextUtils.isEmpty(cDetails)){
            String id = mDatabaseReference.push().getKey();
            CourseDetails newdetails = new CourseDetails(cName,cDetails);
            mDatabaseReference.child("Course Details").child(id).setValue(newdetails);
            Toast.makeText(TrainingCenterProfile.this,"Course Details Updated",Toast.LENGTH_LONG).show();
            mCourseDetails.setText("");
            mCourseName.setText("");
        }
        else if(TextUtils.isEmpty(cName)){
            Toast.makeText(TrainingCenterProfile.this, "Enter Course Name to Proceed  ", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(cName)){
            Toast.makeText(TrainingCenterProfile.this, "Enter Course Details to Proceed  ", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(TrainingCenterProfile.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Logout) {
            // Handle the camera action
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(TrainingCenterProfile.this,MainActivity.class));
        } else if (id == R.id.PreviousPosts) {
            //Open Previous Posts Activity
            startActivity(new Intent(TrainingCenterProfile.this,PreviousPosts.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
