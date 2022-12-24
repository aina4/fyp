package com.example.fypg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class editstore extends AppCompatActivity {

    TextView tv, tv2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstore);

        tv = findViewById(R.id.textView37);
        tv2 = findViewById(R.id.textView38);
        btn = findViewById(R.id.button5);

        //crud page
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editstore.this, crud.class);
                startActivity(intent);
            }
        });
        //homepage (logout)
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editstore.this, homepage.class);
                startActivity(intent);
            }
        });
        //homepage after updating store
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editstore.this, crud.class);
                startActivity(intent);
            }
        });
    }
}