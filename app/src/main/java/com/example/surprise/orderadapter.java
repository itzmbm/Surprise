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
import java.util.ArrayList;

public class orderadapter extends RecyclerView.Adapter<orderadapter.MyViewHolder> implements Serializable {

    Context context;
    ArrayList<orderrecycle> orderarraylist;

    public orderadapter(Context context, ArrayList<orderrecycle> orderarraylist) {
        this.context = context;
        this.orderarraylist = orderarraylist;
    }

    @NonNull

    @Override
    public  orderadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.orderrecycle,parent,false);
        return new orderadapter.MyViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull orderadapter.MyViewHolder holder, int position) {
        orderrecycle fc=orderarraylist.get(position);
        holder.name.setText(fc.name);
        holder.totalprice.setText(String.valueOf(fc.totalprice));
        holder.quantity.setText(String.valueOf(fc.quantity));
        Glide.with(context).load(fc.imageurl).into(holder.imageurl);
    }

    @Override
    public int getItemCount() {
        return orderarraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements Serializable   {
        TextView name,quantity,totalprice;
        ImageView imageurl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.pname1);
            totalprice=itemView.findViewById(R.id.price1);
            imageurl=itemView.findViewById(R.id.img1);
            quantity=itemView.findViewById(R.id.quan1);

        }
    }
}
