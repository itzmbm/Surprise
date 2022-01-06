package com.example.surprise;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class floweradapter extends RecyclerView.Adapter<floweradapter.MyViewHolder> implements Serializable {

    Context context;
ArrayList<flowerrecycle> flowerarraylist;

    public floweradapter(Context context, ArrayList<flowerrecycle> flowerarraylist) {
        this.context = context;
        this.flowerarraylist = flowerarraylist;
//        Intent intent = new Intent(context,ProductDetails.class);
//        context.startActivity(intent);
    }

    @NonNull

    @Override
    public floweradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull floweradapter.MyViewHolder holder, int position) {
flowerrecycle fc=flowerarraylist.get(position);
holder.name.setText(fc.name);
holder.price.setText(String.valueOf(fc.price));
holder.rating.setText(String.valueOf(fc.rating));
holder.brief.setText(fc.brief);

      Glide.with(context).load(fc.imageurl).into(holder.imageurl);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(holder.itemView.getContext(),ProductDetails.class);
                Bundle args = new Bundle();
                args.putSerializable("details",fc);
                i.putExtras(args);
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flowerarraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements Serializable   {
TextView name,price,rating,brief,description;
ImageView imageurl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.pname);
            price=itemView.findViewById(R.id.pprice);
            imageurl=itemView.findViewById(R.id.imgview);
rating=itemView.findViewById(R.id.prating);
brief=itemView.findViewById(R.id.pbreif);

        }
    }
}
