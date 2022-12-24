package com.example.fypg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class stores extends AppCompatActivity {

    TextView tv;
    ImageView iv, iv2, iv3, iv4, iv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        iv = findViewById(R.id.imageView11);
        iv2 = findViewById(R.id.imageView12);
        iv3 = findViewById(R.id.imageView14);
        iv4 = findViewById(R.id.imageView13);
        iv5 = findViewById(R.id.imageView15);
        tv = (TextView) findViewById(R.id.textView16);

        //mytown
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stores.this, lilit.class);
                startActivity(intent);
            }
        });
        //lao mao
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stores.this, lontar.class);
                startActivity(intent);
            }
        });
        //ati creative
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stores.this, lilit.class);
                startActivity(intent);
            }
        });
        //gasing lagenda
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stores.this, lontar.class);
                startActivity(intent);
            }
        });
        //central market
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stores.this, lontar.class);
                startActivity(intent);
            }
        });
        //homepage
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stores.this, homepage.class);
                startActivity(intent);
            }
        });
    }
}