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
    private EditText et, et2;
    private Button btn;
    private TextView notAdmin;

    //create object of database
   // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("https://fypg-2205f-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       et = findViewById(R.id.editTextTextEmailAddress);
       et2 = findViewById(R.id.editTextTextPassword);
       btn = findViewById(R.id.button7);
       notAdmin = findViewById(R.id.textView43);


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
        notAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, homepage.class);
                startActivity(intent);
            }
        });
    }
}
