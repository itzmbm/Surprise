package com.example.surprise;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.Objects;

public class ProductDetails extends AppCompatActivity implements Serializable {
    TextView name,rating,pric,breif,description,quantity;
    int totalQuantity=1;
    ImageView img;
    ImageButton increase,decrease;
    Button aoc;
    Toolbar tlbar;
  flowerrecycle viewHolder=null;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Product Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));

        final Object object=getIntent().getSerializableExtra("details" );
if(object instanceof flowerrecycle){
viewHolder=(flowerrecycle) object;
}

   name=findViewById(R.id.pdname);
   rating=findViewById(R.id.pdrating);
   pric=findViewById(R.id.pdprice);
   quantity=findViewById(R.id.pdquantity);
   description=findViewById(R.id.pddescription);
   increase=findViewById(R.id.increase);
   decrease=findViewById(R.id.decrease);
   aoc=findViewById(R.id.aoc);
   img=findViewById(R.id.pdimg);

if(viewHolder!=null){
    Glide.with(getApplicationContext()).load(viewHolder.imageurl).into(img);
name.setText(viewHolder.getName());
rating.setText(Double.toString(viewHolder.rating));
pric.setText(Integer.toString(viewHolder.price));
description.setText(viewHolder.description);
}

increase.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
if(totalQuantity<10){
    totalQuantity++;
    quantity.setText(String.valueOf(totalQuantity));
}
    }
});
decrease.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(totalQuantity>1){

            totalQuantity--;
            quantity.setText(String.valueOf(totalQuantity));
        }
    }
});

//Intent i=getIntent();
//String pname=i.getStringExtra("name");
//String imgurl=i.getStringExtra("img");
//   name.setText(pname);
//   Glide.with(this).load(imgurl).into(img);

    }
}