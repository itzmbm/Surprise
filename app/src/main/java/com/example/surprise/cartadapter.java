package com.example.surprise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class cartadapter extends RecyclerView.Adapter<cartadapter.MyViewHolder> implements Serializable{
    Context context;
    ArrayList<cartrecycle> cartarraylist;
FirebaseFirestore db;
int total=0;
    public cartadapter(Context context, ArrayList<cartrecycle> cartarraylist) {
        this.context = context;
        this.cartarraylist = cartarraylist;
//        Intent intent = new Intent(context,ProductDetails.class);
//        context.startActivity(intent);
    }

    @NonNull

    @Override
    public cartadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cartrecycle,parent,false);
        return new cartadapter.MyViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull cartadapter.MyViewHolder holder, int position) {
        cartrecycle fc=cartarraylist.get(position);
        holder.name.setText(fc.name);
        holder.price.setText(String.valueOf(fc.price));
holder.totalprice.setText(String.valueOf(fc.totalprice));
holder.quantity.setText(String.valueOf(fc.requiredquantity));
holder.quan.setText(String.valueOf(fc.requiredquantity));
        Glide.with(context).load(fc.imageurl).into(holder.imageurl);
        total=total+fc.totalprice;
        Log.d("TOTAL", String.valueOf(total));
        Intent intent=new Intent("mytotalprice");
        intent.putExtra("totalAmount",total);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        cartarraylist.remove(holder.getAdapterPosition());
        notifyDataSetChanged();
//        String doc=db.collection("Cart").document().getId(;
//        db.collection("Cart").document(doc).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(context.getApplicationContext(), "Item removed",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(context.getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
});
    }

    @Override
    public int getItemCount() {
        return cartarraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements Serializable {
        TextView name,price,totalprice,quantity,quan;
        ImageView imageurl;
        Button delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cname);
            quantity=itemView.findViewById(R.id.cquantity);
            quan=itemView.findViewById(R.id.quan);
            price=itemView.findViewById(R.id.cprice);
            imageurl=itemView.findViewById(R.id.cimage);
            totalprice=itemView.findViewById(R.id.ctotalprice);
            delete=itemView.findViewById(R.id.delete);

        }
    }
}
