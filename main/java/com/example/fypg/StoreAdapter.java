package com.example.fypg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.fypg.Store;


import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class StoreAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<Store> storeList;


    public StoreAdapter(Context context, List<Store> dataList) {
        this.context = context;
        this.storeList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_store, parent, false);
            return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(storeList.get(position).getImgUrl()).into(holder.recImage);
        holder.recName.setText(storeList.get(position).getName());
        holder.recAddress.setText(storeList.get(position).getAddress());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detailStore.class);
                intent.putExtra("Image", storeList.get(holder.getAdapterPosition()).getImgUrl());
                intent.putExtra("Name", storeList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Address", storeList.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("Key",storeList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
    }
  /*  @Override
    public int getItemViewType(int position) {
        return position % 3;
    }*/

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public void searchDataList(ArrayList<Store> searchList){
        storeList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recName, recAddress;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recName = itemView.findViewById(R.id.recName);
        recAddress = itemView.findViewById(R.id.recAddress);
    }
}
/*class MyViewHolderAdmin extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recName, recAddress;
    CardView recCard;

    public MyViewHolderAdmin(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recName = itemView.findViewById(R.id.recName);
        recAddress = itemView.findViewById(R.id.recAddress);
    }
}*/
