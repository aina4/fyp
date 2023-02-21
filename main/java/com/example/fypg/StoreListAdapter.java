package com.example.fypg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListViewHolder> {

    private Context context;
    private List<Store> storeListUser;


    public StoreListAdapter(Context context, List<Store> dataList) {
        this.context = context;
        this.storeListUser = dataList;
    }

    @NonNull
    @Override
    public StoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list, parent, false);
        return new StoreListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreListViewHolder holder, int position) {
        Glide.with(context).load(storeListUser.get(position).getImgUrl()).into(holder.recImage);
        holder.recName.setText(storeListUser.get(position).getName());
        holder.recAddress.setText(storeListUser.get(position).getAddress());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, stores.class); //will navigate to stores page
                intent.putExtra("Image", storeListUser.get(holder.getAdapterPosition()).getImgUrl());
                intent.putExtra("Name", storeListUser.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Address", storeListUser.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("Key",storeListUser.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
        holder.recImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.recName.getText().equals("Central Market")){
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q=3.1455098818649008, 101.69550160594483"));
                    intent.setPackage("com.google.android.apps.maps");
                    context.startActivity(intent);
                }
                if(holder.recName.getText().equals("Kedai Lao Mao")){
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q=2.824713325612721, 101.50058468205727"));
                    intent.setPackage("com.google.android.apps.maps");
                    context.startActivity(intent);
                }
                if(holder.recName.getText().equals("Gasing Lagenda")){
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q=2.2453329, 102.1900797"));
                    intent.setPackage("com.google.android.apps.maps");
                    context.startActivity(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return storeListUser.size();
    }
}
class StoreListViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recName, recAddress;
    CardView recCard;

    public StoreListViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recName = itemView.findViewById(R.id.recName);
        recAddress = itemView.findViewById(R.id.recAddress);

    }
}
