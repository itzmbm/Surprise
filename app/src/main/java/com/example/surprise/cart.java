package com.example.surprise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class cart extends AppCompatActivity  {
    RecyclerView recyclerView;
    ArrayList<cartrecycle> cartarraylist;
    cartadapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog pd;
    int totalsum=0;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Checkout.preload(getApplicationContext());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cart");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
        SharedPreferences b = getSharedPreferences("Logindetails", MODE_PRIVATE);
btn=findViewById(R.id.button2);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getApplicationContext(),PlaceOrder.class);
        i.putExtra("totalsum",totalsum);
        Log.d("totsum", String.valueOf(totalsum));
        startActivity(i);
    }
});
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("fetching data");
        pd.show();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cart Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
        recyclerView=findViewById(R.id.crecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        cartarraylist=new ArrayList<>();
        myAdapter=new cartadapter(cart.this,cartarraylist);
        recyclerView.setAdapter(myAdapter);
        try {
            EventChangeListener();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void EventChangeListener() throws InterruptedException {
        SharedPreferences b = getSharedPreferences("Logindetails", MODE_PRIVATE);
        db.collection("Cart").whereEqualTo("uid",b.getString("uid","")).orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    if(pd.isShowing()){
                        pd.dismiss();
                    }
                    Log.e("Firestore error",error.getMessage());
                    return;
                }
                for(DocumentChange dc:value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        cartarraylist.add(dc.getDocument().toObject(cartrecycle.class));
                        totalsum=totalsum+cartarraylist.get(cartarraylist.size()-1).totalprice;
                    }
                    myAdapter.notifyDataSetChanged();
                    if(pd.isShowing()){
                        pd.dismiss();
                    }
                }
            }
        });
//        Intent i=new Intent(getApplicationContext(),PlaceOrder.class);
//        Bundle args = new Bundle();
//        args.putSerializable("cartitems",cartarraylist);
//        i.putExtras(args);
//        Thread.sleep(1000);
//        startActivity(i);

// SharedPreferences  sh = getSharedPreferences("cartdetails", MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sh.edit();
//        myEdit.putInt("total", totalsum);
//        myEdit.apply();
    }


}