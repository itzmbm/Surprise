package com.example.surprise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class Orders extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<deliveryrecycle> deliveryarraylist;
    deliveryadapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("fetching data");
        pd.show();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Orders");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
        recyclerView=findViewById(R.id.rcview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        deliveryarraylist=new ArrayList<>();
        myAdapter=new deliveryadapter(Orders.this,deliveryarraylist);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        SharedPreferences b = getSharedPreferences("Logindetails", MODE_PRIVATE);
        db.collection("Delivery").whereEqualTo("uid", b.getString("uid", "")).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        deliveryarraylist.add(dc.getDocument().toObject(deliveryrecycle.class));

                    }
                    myAdapter.notifyDataSetChanged();
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            }
        });
    }
}