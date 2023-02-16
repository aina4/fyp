package com.example.fypg;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addtutorial extends AppCompatActivity {

    VideoView uploadVideo;
    Button saveButton, upload;
    EditText uploadType, uploadDesc;
    String videoURL;

    Uri uri;
    ProgressDialog pd;
    TextView back, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtutorial);

        uploadVideo = findViewById(R.id.uploadVideo);
        uploadType = findViewById(R.id.uploadType);
        uploadDesc = findViewById(R.id.uploadDesc);
        saveButton = findViewById(R.id.saveButton);
        upload = findViewById(R.id.upload);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadVideo.setVideoURI(uri);

                            pd.setTitle("Muat naik...");
                            pd.show();
                           /* pd.setMessage("Tunggu Sebentar");
                            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            pd.setIndeterminate(true);
                            pd.setProgress(0);
                            pd.setMax(100);


                            final Handler handler = new Handler(new Handler.Callback(){
                                @Override
                                public boolean handleMessage(Message msg){
                                    pd.setProgress(pd.getProgress()+1);
                                    return true;
                                }
                            });

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (pd.getProgress()<pd.getMax()){
                                        try{
                                            Thread.sleep(10);
                                            handler.sendMessage(handler.obtainMessage());
                                        } catch (InterruptedException e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }).start();
                            }*/
                            uploadvideo();
                            setVideotoVideoView();
                        } else {
                            Toast.makeText(addtutorial.this, "Tiada video dipilih", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showing progress while uploading
                pd = new ProgressDialog(addtutorial.this);
                Intent videoPicker = new Intent();
                videoPicker.setType("video/*");
                videoPicker.setAction(Intent.ACTION_GET_CONTENT);
               // startActivityForResult(videoPicker, 5);
                activityResultLauncher.launch(videoPicker);
            }
        });
        //to homepage after adding store page
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        //to crud page
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addtutorial.this, crudtuto.class);
                startActivity(intent);
            }
        });
        //to homepage (logout) page
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addtutorial.this, homepage.class);
                startActivity(intent);
            }
        });
    }
    private void setVideotoVideoView(){
        MediaController mc = new MediaController(this);
        mc.setAnchorView(uploadVideo);

        //set media controller to video view
        uploadVideo.setMediaController(mc);
        uploadVideo.requestFocus();
        uploadVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                uploadVideo.pause();
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
                    Toast.makeText(addtutorial.this, "Video di muat naik!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    pd.dismiss();
                    Toast.makeText(addtutorial.this, "Tidak berjaya" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
    //press the add button
    public void saveData(){
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("Gasing Videos/" + System.currentTimeMillis() + "." + getfiletype(uri));

        AlertDialog.Builder builder = new AlertDialog.Builder(addtutorial.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                //get the link of video
                Uri urlVideo = uriTask.getResult();
                videoURL = urlVideo.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error, Video not uploaded
                pd.dismiss();
                Toast.makeText(addtutorial.this, "Tidak berjaya" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void uploadData(){

        String type = uploadType.getText().toString();
        String description = uploadDesc.getText().toString();

        Gasing g = new Gasing(type, description, videoURL);

        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Tutorial").child(currentDate)
                .setValue(g).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(addtutorial.this, "Data disimpan", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addtutorial.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
