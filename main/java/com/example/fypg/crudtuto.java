package com.example.fypg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class crudtuto extends AppCompatActivity {

    TextView kedai, logout;

    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<Gasing> gasingList;
    TutorialAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudtuto);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(crudtuto.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(crudtuto.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        gasingList = new ArrayList<>();
        adapter = new TutorialAdapter(crudtuto.this, gasingList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Tutorial");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gasingList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Gasing gasing = itemSnapshot.getValue(Gasing.class);

                    gasing.setmKey(itemSnapshot.getKey());

                    gasingList.add(gasing);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { dialog.dismiss();}
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crudtuto.this, addtutorial.class);
                startActivity(intent);
            }
        });

        //homepage
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crudtuto.this, homepage.class);
                startActivity(intent);
            }
        });
        //kedai page
        kedai = findViewById(R.id.kedai);
        kedai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crudtuto.this, crud.class);
                startActivity(intent);
            }
        });
    }
    public void searchList(String text){
        ArrayList<Gasing> searchList = new ArrayList<>();
        for (Gasing g: gasingList){
            if (g.getType().toLowerCase().contains(text.toLowerCase())){
                searchList.add(g);
            }
        }
        adapter.searchDataList(searchList);
    }
}
