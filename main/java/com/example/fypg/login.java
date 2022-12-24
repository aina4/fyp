package com.example.fypg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    //create object of database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fypg-2205f-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       final EditText et = findViewById(R.id.editTextTextEmailAddress);
       final EditText et2 = findViewById(R.id.editTextTextPassword);
       final Button btn = findViewById(R.id.button7);
       final TextView notadmin = findViewById(R.id.textView43);


        //check admin uname and pwd
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String uname = et.getText().toString();
                final String pwd = et2.getText().toString();

                if(uname.isEmpty() || pwd.isEmpty()){
                    //correct
                    Toast.makeText(login.this, "Sila isi semua ruangan kosong", Toast.LENGTH_SHORT).show();
                }else if(uname.equals("admin@gmail.com") && pwd.equals("admin123")){
                    Toast.makeText(login.this, "Log Masuk Berjaya!", Toast.LENGTH_SHORT).show();
                    //login button
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(login.this, crud.class);
                            startActivity(intent);
                        }
                    });
                   // finish();
                }else{
                    //incorrect
                    Toast.makeText(login.this, "Log Masuk Tidak Berjaya!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //not admin to homepage
        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, homepage.class);
                startActivity(intent);
            }
        });
    }

    /* public void sendData(View view) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }*/
}