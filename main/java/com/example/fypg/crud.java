package com.example.fypg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class crud extends AppCompatActivity {

    TextView tv, tv2;
    Button btn;
    ImageView iv;

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Store> storeArrayList;
    StoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        Objects.requireNonNull(getSupportActionBar()).hide();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeArrayList = new ArrayList<>();

        tv = findViewById(R.id.textView28); //log keluar
        tv2 = findViewById(R.id.textView44); //tutorial
        btn = findViewById(R.id.button3); //add button
        iv = findViewById(R.id.imageView19);

        //homepage
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crud.this, homepage.class);
                startActivity(intent);
            }
        });
        
        readData();
        //tutorial page
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crud.this, crudtuto.class);
                startActivity(intent);
            }
        });
        //to add new store
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crud.this, addstore.class);
                startActivity(intent);
            }
        });
        //to add store
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crud.this, editstore.class);
                startActivity(intent);
            }
        });
    }

    private void readData() {
        databaseReference.child("Kedai").orderByChild("storeName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Store stores = dataSnapshot.getValue(Store.class);
                    storeArrayList.add(stores);
                }
                adapter = new StoreAdapter(crud.this, storeArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}