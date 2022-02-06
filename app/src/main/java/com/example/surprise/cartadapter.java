package com.example.surprise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class cartadapter extends RecyclerView.Adapter<cartadapter.MyViewHolder> implements Serializable{
    Context context;
    ArrayList<cartrecycle> cartarraylist;
FirebaseFirestore db,fdb;
    DocumentSnapshot document;
    boolean operation=false;
int total;
String oid="";
String pid="nothing";
int quant,actualquant,totalquant;
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
//        Log.d("TOTAL", String.valueOf(total));
//        Intent intent=new Intent("mytotalprice");
//        intent.putExtra("totalAmount",total);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
oid=fc.oid;

holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d("oid",oid);
        db=FirebaseFirestore.getInstance();
        //update quantity back if item deleted
        //1. get required quantity from cart item
                db.collection("Cart").document(oid).get();
         db.collection("Cart").document(oid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                         quant= (int) Long.parseLong(String.valueOf(document.getLong("requiredquantity")));
                         pid= document.getString("pid");

                        //2.get existing quantity from the product list
        db.collection("Flowers").document(pid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Log.d("oidddddddddddddddd", String.valueOf(pid));
                        actualquant=(int)Long.parseLong(String.valueOf(document.getLong("quantity")));

                        totalquant=actualquant+quant;
                        Log.d("QUANTITY", String.valueOf(totalquant));
                        //3. total the existing quantity add with deleted quantity and update back
                        db.collection("Flowers").document(pid).update("quantity",totalquant).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
                    } else {
                        Log.d("LOGGER", "No such document");
                    }

                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
// delete item from cart collection
                db.collection("Cart").document(oid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context.getApplicationContext(), "Item removed",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context.getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cartarraylist.remove(holder.getAdapterPosition());
                notifyDataSetChanged();

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
