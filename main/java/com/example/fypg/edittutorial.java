package com.example.fypg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class edittutorial extends AppCompatActivity {

    TextView tv, tv2;
    Button btn, btn2;
   // ImageView iv, img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittutorial);

        tv = findViewById(R.id.textView31); //kembali text
        tv2 = findViewById(R.id.textView32); //log keluar text
        btn = findViewById(R.id.button5); //update tuto button
        btn2 = findViewById(R.id.button6); //delete tuto button

        //to crud tuto page
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edittutorial.this, crudtuto.class);
                startActivity(intent);
            }
        });
        //to homepage (logout) page
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edittutorial.this, homepage.class);
                startActivity(intent);
            }
        });
        //to crud tuto after updating tuto page
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edittutorial.this, crudtuto.class);
                startActivity(intent);
            }
        });
        //to crud tuto after deleting tuto page
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(edittutorial.this, crudtuto.class);
                startActivity(intent);
            }
        });
    }
}