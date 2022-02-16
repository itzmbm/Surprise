package com.example.surprise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class deliveryadapter extends RecyclerView.Adapter<deliveryadapter.MyViewHolder> implements Serializable {


    Context context;
    ArrayList<deliveryrecycle> deliveryarraylist;

    public deliveryadapter(Context context, ArrayList<deliveryrecycle> deliveryarraylist) {
        this.context = context;
        this.deliveryarraylist = deliveryarraylist;
//        Intent intent = new Intent(context,ProductDetails.class);
//        context.startActivity(intent);
    }

    @NonNull

    @Override
    public deliveryadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.deliveryrecycle,parent,false);
        return new deliveryadapter.MyViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull deliveryadapter.MyViewHolder holder, int position) {
        deliveryrecycle fc=deliveryarraylist.get(position);
        holder.name.setText(fc.name);
        holder.did.setText(String.valueOf(fc.did));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String odateString = sdf.format(fc.orderedDateTime);
        holder.orderDateTime.setText(odateString);
        String ddateString = sdf.format(fc.deliveryByDateTime);
        holder.deliveryByDateTime.setText(ddateString);
        holder.total.setText(String.valueOf(fc.total));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.itemView.getContext(),OrderDetails.class);
                Bundle args = new Bundle();
                args.putSerializable("deliverydetails",fc);
                i.putExtras(args);
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryarraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements Serializable   {
        TextView name,total,did,orderDateTime,deliveryByDateTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            did=itemView.findViewById(R.id.did);
            orderDateTime=itemView.findViewById(R.id.ot);
            deliveryByDateTime=itemView.findViewById(R.id.dt);
            name=itemView.findViewById(R.id.sname);
            total=itemView.findViewById(R.id.tprice);

        }
    }

}
