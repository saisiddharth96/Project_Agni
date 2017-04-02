package com.example.saisi.agni;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PreviousPosts extends AppCompatActivity  {

    private DatabaseReference mDatabaseReference;
    private ListView mCoursesList;
    private StorageReference mStorageRef;
    List<CourseDetails> Detail;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_posts);

        mCoursesList = (ListView) findViewById(R.id.listViewPreviousPosts);

        Detail = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Course Details");
        mStorageRef = FirebaseStorage.getInstance().getReference(Constants.STORAGE_PATH_UPLOADS);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Detail.clear();

                for(DataSnapshot PostSnapshot : dataSnapshot.getChildren()){

                    CourseDetails newDetails = PostSnapshot.getValue(CourseDetails.class);
                    Detail.add(newDetails);
                }

                CourseList courseListAdapter = new CourseList(PreviousPosts.this,Detail);
                mCoursesList.setAdapter(courseListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PreviousPosts.this, "Error Loading", Toast.LENGTH_SHORT).show();
            }
        });

      /*  mStorageRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImageView courseImage = (ImageView)findViewById(R.id.CourseImage);
                Uri downloadUri =  taskSnapshot.getDownloadUrl();
                Picasso.with(PreviousPosts.this).load(downloadUri).into(courseImage);
            }
        });*/
    }



}
