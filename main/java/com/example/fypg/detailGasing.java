package com.example.fypg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class detailGasing extends AppCompatActivity {

    TextView back, logout;
    TextView detailDesc, detailType;
    VideoView detailVideo;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String videoUrl = "";
    Uri uri;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gasing);

        detailDesc = findViewById(R.id.detailDesc);
        detailVideo = findViewById(R.id.detailVideo);
        detailType = findViewById(R.id.detailType);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailType.setText(bundle.getString("Type"));
            detailDesc.setText(bundle.getString("Description"));
            key = bundle.getString("Key");
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(detailVideo);
            videoUrl = bundle.getString("Video");
            uri = Uri.parse(bundle.getString("Video"));
            detailVideo.setMediaController(mediaController);
            detailVideo.setVideoURI(uri);
           // binding.detailVideo.setVideoURI(uri);
            detailVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailVideo.start();
                }
            });
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tutorial");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageReference = storage.getReferenceFromUrl(videoUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(detailGasing.this, "Telah Dibuang", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), crudtuto.class));
                        finish();
                    }
                });
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailGasing.this, edittutorial.class)
                        .putExtra("Type", detailType.getText().toString())
                        .putExtra("Description", detailDesc.getText().toString())
                        .putExtra("Video", videoUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
        //go back to crud tuto page
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailGasing.this, crudtuto.class);
                startActivity(intent);
            }
        });
        //homepage (logout)
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailGasing.this, homepage.class);
                startActivity(intent);
            }
        });
    }
}
