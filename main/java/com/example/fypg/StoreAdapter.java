package com.example.fypg;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    Context context;
    ArrayList<Store> storeArrayList;
    DatabaseReference databaseReference;

    public StoreAdapter(Context context, ArrayList<Store> storeArrayList) {
        this.context = context;
        this.storeArrayList = storeArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_crud, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store stores = storeArrayList.get(position);

        holder.textName.setText("Name: " + stores.getStoreName());
        holder.textAddress.setText("Address: " + stores.getStoreAddress());

        holder.updateicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, stores.getStoreId(), stores.getStoreName(), stores.getStoreAddress());
            }
        });

        holder.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogDelete viewDialogDelete = new ViewDialogDelete();
                viewDialogDelete.showDialog(context, stores.getStoreId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textName;
        TextView textAddress;

        ImageView updateicon, deleteicon;

        public ViewHolder(@NonNull View storeView){
            super(storeView);
            textName = storeView.findViewById(R.id.textView29);
            textAddress = storeView.findViewById(R.id.textView33);

            deleteicon = storeView.findViewById(R.id.imageView21);
            updateicon = storeView.findViewById(R.id.imageView19);
        }
    }

    public class ViewDialogUpdate{
        public void showDialog(Context context, String id, String name, String address){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_editstore);

            ImageView pic = dialog.findViewById(R.id.imgView);
            EditText textName = dialog.findViewById(R.id.textView34);
            EditText textAddress = dialog.findViewById(R.id.textView34);

            textName.setText(name);
            textAddress.setText(address);

            Button update = dialog.findViewById(R.id.button3);

            update.setText("UPDATE");
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newName = textName.getText().toString();
                    String newAddress= textAddress.getText().toString();

                    if(name.isEmpty() || address.isEmpty()){
                        Toast.makeText(context, "Sila isi semua data", Toast.LENGTH_SHORT).show();
                    } else {
                        if(newName.equals(name) && newAddress.equals(address)){
                            Toast.makeText(context, "Anda tidak membuat perubahan", Toast.LENGTH_SHORT).show();
                        } else{
                            databaseReference.child("Kedai").child(id).setValue(new Store(id, name, address));
                            Toast.makeText(context, "Berjaya dikemas kini!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
            });
        }
    }

    public class ViewDialogDelete{
        public void showDialog(Context context, String id){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.deletepg);

            Button delete = dialog.findViewById(R.id.yes);
            Button cancel = dialog.findViewById(R.id.no);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                            databaseReference.child("Kedai").child(id).removeValue();
                            Toast.makeText(context, "Berjaya dipadam!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
        }
    }
}
