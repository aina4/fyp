package com.example.fypg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class homepage extends AppCompatActivity {

    TextView tv;
    ImageView iv, iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        tv = (TextView) findViewById(R.id.textView);
        iv = findViewById(R.id.imageView);
        iv2 = findViewById(R.id.imageView3);

        //admin
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homepage.this, login.class);
                startActivity(intent);
            }
        });
        //tuto
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homepage.this, tutorial.class);
                startActivity(intent);
            }
        });
        //kedai
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homepage.this, stores.class);
                startActivity(intent);
            }
        });
    }
}