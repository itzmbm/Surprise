package com.example.surprise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.bumptech.glide.Glide;

import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.DoubleConsumer;

public class ProductDetails extends AppCompatActivity implements Serializable {
    TextView name,rating,pric,breif,description,quantity,rquan;
    int totalQuantity=1;
    ImageView img;
    ImageButton increase,decrease;
    Button aoc;
    Toolbar tlbar;
  flowerrecycle viewHolder=null;
  private FirebaseFirestore fdb;

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
aoc.setOnClickListener(new View.OnClickListener() {
    int actualquan;
    int price;
    int totalprice,count=0;
String nam,imagurl,pid;

    public void onClick(View v) {

        fdb=FirebaseFirestore.getInstance();

            if (viewHolder != null) {
                actualquan = viewHolder.getQuantity();
                nam = viewHolder.getName();
                price = viewHolder.getPrice();
                imagurl = viewHolder.getImageurl();
                pid=viewHolder.getPid();
            }

        rquan=findViewById(R.id.pdquantity);
        int requiredquan=Integer.parseInt((String) rquan.getText());
        Log.e("required",String.valueOf(requiredquan));
        if(requiredquan<=actualquan){
            HashMap<String,Object> map=new HashMap<>();
            map.put("uid","101");
            map.put("name",nam);
            map.put("price",price);
            map.put("requiredquantity",requiredquan);
            map.put("imageurl",imagurl);
            totalprice=requiredquan*price;
            map.put("totalprice",totalprice);
            map.put("pid",pid);
            actualquan=actualquan-requiredquan;

            String doc=pid;
            Log.d("LOGGG", doc);

            fdb.collection("Flowers").document(doc).update("quantity",actualquan).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    actualquan=viewHolder.getQuantity();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                }
            });
            fdb.collection("Cart").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Item added to cart successfully", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),cart.class);
                        startActivity(i);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed!!", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            fdb.collection("Flowers").document(viewHolder.getPid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    String TAG="MESSAGE";


                    if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                        Log.d(TAG, "DocumentSnapshot data: " +document.get("quantity"));

                            Log.d(TAG, "DocumentSnapshot: "+actualquan);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                    }else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }

            });

            Toast.makeText(getApplicationContext(), "Only "+actualquan+" Stocks left", Toast.LENGTH_SHORT).show();
        }
    }
});


    }
}