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

public class TutorialAdapter extends RecyclerView.Adapter<TutorialViewHolder> {

    private Context context;
    private List<Gasing> gasingList;


    public TutorialAdapter(Context context, List<Gasing> dataList) {
        this.context = context;
        this.gasingList = dataList;
    }

    @NonNull
    @Override
    public TutorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tuto, parent, false);
        return new TutorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialViewHolder holder, int position) {
        holder.recType.setText(gasingList.get(position).getType());
        holder.recDescription.setText(gasingList.get(position).getDescription());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detailGasing.class);
                intent.putExtra("Type", gasingList.get(holder.getAdapterPosition()).getType());
                intent.putExtra("Description", gasingList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Key",gasingList.get(holder.getAdapterPosition()).getmKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gasingList.size();
    }

    public void searchDataList(ArrayList<Gasing> searchList){
        gasingList = searchList;
        notifyDataSetChanged();
    }
}

class TutorialViewHolder extends RecyclerView.ViewHolder{

    TextView recType, recDescription;
    CardView recCard;

    public TutorialViewHolder(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);
        recType = itemView.findViewById(R.id.recType);
        recDescription = itemView.findViewById(R.id.recDescription);
    }
}
