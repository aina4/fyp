package com.example.fypg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class addtutorial extends AppCompatActivity {

    TextView tv, tv2;
    EditText type,desc;
    Button upload, add;
    ProgressDialog progressDialog;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtutorial);

        ref = FirebaseDatabase.getInstance().getReference("Tutorial").push();

        tv = findViewById(R.id.textView31); //kembali text
        tv2 = findViewById(R.id.textView32); //log keluar text
        type = findViewById(R.id.textView48); //type of gasing edit text
        desc = findViewById(R.id.textView50); //description of gasing edit text
        add = findViewById(R.id.button4); //add new tuto button
        upload = findViewById(R.id.button9); //upload new tuto button
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showing progressdialog while uploading
                progressDialog = new ProgressDialog(addtutorial.this);
                choosevideo();
            }
        });

        //to crud tuto page
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addtutorial.this, crudtuto.class);
                startActivity(intent);
            }
        });
        //to homepage (logout) page
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addtutorial.this, homepage.class);
                startActivity(intent);
            }
        });
        //to crud tuto after updating tuto page
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals(null) || desc.equals(null) ||  type.equals("") || desc.equals("")){
                    Toast.makeText(getApplicationContext(), "Data cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    SendVideo(type.getText().toString(), desc.getText().toString());
                    Intent intent = new Intent(addtutorial.this, crudtuto.class);
                    startActivity(intent);
                }
            }
        });
    }
    //method function for sending data to db
    public void SendVideo(final String typeGasing, final String description){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("Type of Gasing", typeGasing);
                map.put("Description", description);
                ref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Data has been sent", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // choose a video from phone storage
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }
    Uri videouri;

    // startActivityForResult is used to receive the result, which is the selected video.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouri = data.getData();
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            uploadvideo();
        }
    }

    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    private void uploadvideo() {
        if (videouri != null) {
            // save the selected video in Firebase storage
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Files/" + System.currentTimeMillis() + "." + getfiletype(videouri));
            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", downloadUri);
                    reference1.child("" + System.currentTimeMillis()).setValue(map);
                    // Video uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(addtutorial.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(addtutorial.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }
}