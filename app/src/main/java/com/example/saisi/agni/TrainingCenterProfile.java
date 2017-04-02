package com.example.saisi.agni;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;

public class TrainingCenterProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 234;

    private EditText mCourseName;
    private EditText mCourseDetails;
    private Button mSendDetailsButton;
    private TextView mUserNameText;
    private TextView mEmailIDText;
    private Spinner mCourseNameSpinner;
    private Uri filePath;
    private Button mSelectImageButton;
    private ImageView mImageView;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    int mPos;
    String mSelection;

    private static final String TAG = "GoogleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_center_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference(Constants.STORAGE_PATH_UPLOADS);

        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        mCourseName = (EditText)findViewById(R.id.courseName);
        mCourseDetails = (EditText)findViewById(R.id.courseDetails);
        mSendDetailsButton = (Button)findViewById(R.id.sendInfoButton);
        mUserNameText = (TextView) findViewById(R.id.UserNameNav);
        mEmailIDText = (TextView) findViewById(R.id.userEmailId);
        mCourseNameSpinner = (Spinner) findViewById(R.id.CourseNameSpinner);
        mSelectImageButton = (Button)findViewById(R.id.selectImagebutton);
        mImageView = (ImageView) findViewById(R.id.imageUpload);

        mSelectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showFileChooser();
            }
        });



        mCourseNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //parent.getItemAtPosition(position);
                TrainingCenterProfile.this.mPos = position;
                TrainingCenterProfile.this.mSelection = parent.getItemAtPosition(position).toString();
                mCourseName.setText(TrainingCenterProfile.this.mSelection);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



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
                    //mUserNameText.setText(mFirebaseAuth.getCurrentUser().getEmail());
                    //mEmailIDText.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
                    //mUserNameText.setText();

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
        //String cUrl = FirebaseStorage.getInstance().toString();




        if(!TextUtils.isEmpty(cName)&& !TextUtils.isEmpty(cDetails) && mImageView.getDrawable() != null){
            String id = mDatabaseReference.push().getKey();
            CourseDetails newdetails = new CourseDetails(cName,cDetails,null);
            mDatabaseReference.child("Course Details").child(id).setValue(newdetails);
            Toast.makeText(TrainingCenterProfile.this,"Course Details Updated",Toast.LENGTH_LONG).show();
            uploadFile();
            mCourseDetails.setText("");
            mCourseName.setText("");
            mImageView.setImageResource(0);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile() {

        if (filePath != null) {

            StorageReference photoRef = mStorageRef.child(filePath.getLastPathSegment());

            photoRef.putFile(filePath).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    CourseDetails newdetails = new CourseDetails(null,null,taskSnapshot.getDownloadUrl().toString());
                    String uploadId = mDatabaseReference.push().getKey();
                    mDatabaseReference.child("Center Images").child(uploadId).setValue(newdetails);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            //mStorageRef.child(Constants.STORAGE_PATH_UPLOADS /*+ System.currentTimeMillis() + "." + getFileExtension(filePath)*/);

           /* mStorageRef.child("uploads");
                    mStorageRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());

                            String uploadId = mDatabaseReference.push().getKey();
                            mDatabaseReference.child("Center Images").child(uploadId).setValue(upload);


                            //String id = mDatabaseReference.push().getKey();
                           // mDatabaseReference.child(id).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * (taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");

                        }
                    });
        } else {
            //display an error if no file is selected
        }*/

        }
    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    PICK_IMAGE_REQUEST);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
