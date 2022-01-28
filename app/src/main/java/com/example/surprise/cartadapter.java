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

public class cartadapter extends RecyclerView.Adapter<cartadapter.MyViewHolder> implements Serializable{
    Context context;
    ArrayList<cartrecycle> cartarraylist;
FirebaseFirestore db,fdb;
    DocumentSnapshot document;
    boolean operation=false;
int total=0;
String oid,actualquant,pid,totalquant="";
int quant=0;
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
                db.collection("Cart").document(oid).get();
         db.collection("Cart").document(oid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
//                         quant=Integer.parseInt(String.valueOf(document.getLong("requiredquantity")));
operation=true;
                         pid= (String) document.get("pid");
                        Log.d("oidddddddddddddddd", String.valueOf(document));
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
         Log.d("QUANTITY", pid);
//        DocumentReference docRef1 = db.collection("Flowers").document(oid);
//        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document != null) {
//                        actualquant=document.getString("quantity");
//
//                    } else {
//                        Log.d("LOGGER", "No such document");
//                    }
//                } else {
//                    Log.d("LOGGER", "get failed with ", task.getException());
//                }
//            }
//        });
//
//        totalquant=Integer.parseInt(actualquant)+Integer.parseInt(String.valueOf(quant));
//        fdb=FirebaseFirestore.getInstance();
//        fdb.collection("Flowers").document( "product1").update("quantity",totalquant).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(operation=true) {
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
