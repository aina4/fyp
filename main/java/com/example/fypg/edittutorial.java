package com.example.fypg;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class edittutorial extends AppCompatActivity {

    TextView back, logout;
    VideoView updateVideo;
    Button updateButton, update;
    EditText updateType, updateDesc;
    String type, description;
    String videoUrl;
    String key, oldVideoURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittutorial);

        updateButton = findViewById(R.id.updateButton);
        updateType = findViewById(R.id.updateType);
        updateVideo = findViewById(R.id.updateVideo);
        updateDesc = findViewById(R.id.updateDesc);
        update = findViewById(R.id.update);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            pd.setTitle("Muat naik...");
                            pd.show();
                            updateVideo.setVideoURI(uri);
                            uploadvideo();
                            setVideotoVideoView();
                        } else {
                            Toast.makeText(edittutorial.this, "Video tidak dipilih", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
           // Glide.with(edittutorial.this).load(bundle.getString("Video")).into(updateVideo);
           // uri = Uri.parse(bundle.getString("uri"));
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(updateVideo);
            videoUrl = bundle.getString("Video");
            uri = Uri.parse(bundle.getString("Video"));
            updateVideo.setMediaController(mediaController);
            updateVideo.setVideoURI(uri);
            updateType.setText(bundle.getString("Type"));
            updateDesc.setText(bundle.getString("Description"));
            key = bundle.getString("Key");
            oldVideoURL = bundle.getString("Video");
            updateVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateVideo.start();
                }
            });
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorial").child(key);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(edittutorial.this);
                Intent videoPicker = new Intent(Intent.ACTION_PICK);
                videoPicker.setType("video/*");
                videoPicker.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(videoPicker);
               // startActivityForResult(Intent.createChooser(videoPicker,"Select Video"),100);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(edittutorial.this, crudtuto.class);
                startActivity(intent);
            }
        });
        //crud tuto page
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edittutorial.this, crudtuto.class);
                startActivity(intent);
            }
        });
        //homepage (logout)
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edittutorial.this, homepage.class);
                startActivity(intent);
            }
        });
    }
    private void setVideotoVideoView(){
        MediaController mc = new MediaController(this);
        mc.setAnchorView(updateVideo);

        //set media controller to video view
        updateVideo.setMediaController(mc);
        updateVideo.requestFocus();
        updateVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                updateVideo.pause();
            }
        });
    }
    private String getfiletype(Uri uri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(uri));
    }
    private void uploadvideo(){
        if (uri != null) {
            // save the selected video in Firebase storage
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Gasing Videos/" + System.currentTimeMillis() + "." + getfiletype(uri));
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    // get the link of video
                    //  Uri urlVideo = uriTask.getResult();
                    // videoURL = urlVideo.toString();
                    // uploadData();
                    // String downloadUri = uriTask.getResult().toString();
                    //  DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Tutorial");
                    //   HashMap<String, String> map = new HashMap<>();
                    //  map.put("videolink", downloadUri);
                    //   reference1.child("" + System.currentTimeMillis()).setValue(map);
                    // Video uploaded successfully
                    // Dismiss dialog
                    pd.dismiss();
                    Toast.makeText(edittutorial.this, "Video di muat naik!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    pd.dismiss();
                    Toast.makeText(edittutorial.this, "Tidak berjaya" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pd.setMessage("Di muat naik " + (int) progress + "%");
                }
            });
        }
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference("Gasing Videos/" + System.currentTimeMillis() + "." + getfiletype(uri));

        AlertDialog.Builder builder = new AlertDialog.Builder(edittutorial.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri urlVideo = uriTask.getResult();
                videoUrl = urlVideo.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                dialog.dismiss();
                Toast.makeText(edittutorial.this, "Tidak berjaya" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            // Progress Listener for loading
            // percentage on the dialog box
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                // show the progress bar
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pd.setMessage("Di muat naik " + (int) progress + "%");
            }
        });
    }
    public void updateData(){
        type = updateType.getText().toString().trim();
        description = updateDesc.getText().toString().trim();

        Gasing gasing = new Gasing(type, description, videoUrl);

        databaseReference.setValue(gasing).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldVideoURL);
                    reference.delete();
                    Toast.makeText(edittutorial.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(edittutorial.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
