package com.example.fypg;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GasingListAdapter extends RecyclerView.Adapter<GasingListViewHolder> {

    private Context context;
    private List<Gasing> gasingListUser;


    public GasingListAdapter(Context context, List<Gasing> dataList) {
        this.context = context;
        this.gasingListUser = dataList;
    }

    @NonNull
    @Override
    public GasingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gasing_list, parent, false);
        return new GasingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GasingListViewHolder holder, int position) {
       // Glide.with(context).load(gasingListUser.get(position).getVideo()).into(holder.recVideo);
        Gasing gasingVideo = gasingListUser.get(position);
        holder.recType.setText(gasingListUser.get(position).getType());
        holder.recDesc.setText(gasingListUser.get(position).getDescription());

        try{
            String link = gasingVideo.getVideo();
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.recVideo);
            Uri videoUri = Uri.parse(link);
            holder.recVideo.setMediaController(mediaController);
            holder.recVideo.setVideoURI(videoUri);
          //  mediaController.setAnchorView(holder.recVideo);
           // holder.recVideo.requestFocus();
            holder.recVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.recVideo.start();
                }
            });

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // holder.recVideo.start();
                Intent intent = new Intent(context, tutorial.class);
                intent.putExtra("Video", gasingListUser.get(holder.getAdapterPosition()).getVideo());
                intent.putExtra("Type", gasingListUser.get(holder.getAdapterPosition()).getType());
                intent.putExtra("Description", gasingListUser.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Key",gasingListUser.get(holder.getAdapterPosition()).getmKey());
                context.startActivity(intent);
            }
        });
        } catch (Exception e){
            Toast.makeText(context, "Error Connection", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        return gasingListUser.size();
    }
}
class GasingListViewHolder extends RecyclerView.ViewHolder{

    VideoView recVideo;
    TextView recType, recDesc;
    CardView recCard;

    public GasingListViewHolder(@NonNull View itemView) {
        super(itemView);

        recVideo = itemView.findViewById(R.id.recVideo);
        recCard = itemView.findViewById(R.id.recCard);
        recType = itemView.findViewById(R.id.recType);
        recDesc = itemView.findViewById(R.id.recDesc);

    }
}
