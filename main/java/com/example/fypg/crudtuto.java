package com.example.fypg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class crudtuto extends AppCompatActivity {

    TextView tv, tv2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudtuto);

        tv = findViewById(R.id.textView28); //log keluar
        tv2 = findViewById(R.id.textView44); //kedai
        btn = findViewById(R.id.button3); //add tuto

        //homepage
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crudtuto.this, homepage.class);
                startActivity(intent);
            }
        });
        //kedai page
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crudtuto.this, crud.class);
                startActivity(intent);
            }
        });
        //addtuto page
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crudtuto.this, addtutorial.class);
                startActivity(intent);
            }
        });
    }
}