package com.example.fypg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class tutorial extends AppCompatActivity {

    ImageView iv, iv2;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        iv = findViewById(R.id.imageView5);
        iv2 = findViewById(R.id.imageView6);
        tv = (TextView) findViewById(R.id.textView5);


        //homepage
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tutorial.this, homepage.class);
                startActivity(intent);
            }
        });
    }
}
