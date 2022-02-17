package com.example.surprise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class OrderDetails extends AppCompatActivity {
    TextView did, name, mobileno, address, total, orderDateTime, deliveryByDateTime;
    deliveryrecycle viewHolder = null;
    RecyclerView recyclerView;
    ArrayList<orderrecycle> orderarraylist;
    orderadapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog pd;
    @SuppressLint("SetTextI18n")

    String delid = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Window window = OrderDetails.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(OrderDetails.this, R.color.black));
        Objects.requireNonNull(getSupportActionBar()).setTitle("Order Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
        SharedPreferences b = getSharedPreferences("Logindetails", MODE_PRIVATE);
        final Object object = getIntent().getSerializableExtra("deliverydetails");
        if (object instanceof deliveryrecycle) {
            viewHolder = (deliveryrecycle) object;
        }
        name = findViewById(R.id.sname1);
        did = findViewById(R.id.did1);
        mobileno = findViewById(R.id.mobno1);
        address = findViewById(R.id.addr1);
        total = findViewById(R.id.tprice1);
        orderDateTime = findViewById(R.id.ot1);
        deliveryByDateTime = findViewById(R.id.dt1);
        if (viewHolder != null) {
            name.setText(viewHolder.getName());
            delid = viewHolder.getDid();
            did.setText(viewHolder.getDid());
            mobileno.setText(String.valueOf(viewHolder.getMobileno()));
            address.setText(viewHolder.getAddress() + "," + viewHolder.getPincode());
            total.setText(String.valueOf(viewHolder.getTotal()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            String odateString = sdf.format(viewHolder.orderedDateTime);
            orderDateTime.setText(odateString);
            String ddateString = sdf.format(viewHolder.deliveryByDateTime);
            deliveryByDateTime.setText(ddateString);
        }
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("fetching data");
        pd.show();
        recyclerView = findViewById(R.id.rcview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        orderarraylist = new ArrayList<>();
        myAdapter = new orderadapter(OrderDetails.this, orderarraylist);
        recyclerView.setAdapter(myAdapter);
        try {
            EventChangeListener();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void EventChangeListener() throws InterruptedException {
        SharedPreferences b = getSharedPreferences("Logindetails", MODE_PRIVATE);
        db.collection("Order").whereEqualTo("did", delid).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        orderarraylist.add(dc.getDocument().toObject(orderrecycle.class));

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