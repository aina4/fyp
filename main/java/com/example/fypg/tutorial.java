package com.example.fypg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tutorial extends AppCompatActivity {

    TextView homepage;

    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<Gasing> gasingListUser;
    GasingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        recyclerView = findViewById(R.id.recyclerView_video);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(tutorial.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(tutorial.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        gasingListUser = new ArrayList<>();
        adapter = new GasingListAdapter(tutorial.this, gasingListUser);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorial");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gasingListUser.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Gasing g = itemSnapshot.getValue(Gasing.class);

                    g.setmKey(itemSnapshot.getKey());

                    gasingListUser.add(g);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { dialog.dismiss();}
        });

        //homepage
        homepage = findViewById(R.id.textView16);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tutorial.this, homepage.class);
                startActivity(intent);
            }
        });
    }
}
