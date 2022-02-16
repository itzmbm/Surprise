package com.example.surprise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Calendar;

public class PlaceOrder extends AppCompatActivity implements PaymentResultListener, Serializable {
    TextView Name,Mobno,Address,Pincode,totalamount,totban;
    private Button btn;
    String name,addr="";
    int pin;
        long mobno=0;
    String did;
    int tsum=0;
    boolean valid=false;
    cartrecycle viewHolder=null;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<cartrecycle> cartarraylist;
    cartadapter myAdapter;

    ProgressDialog pd;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Delivery Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
        btn=findViewById(R.id.order);
        Name=findViewById(R.id.name);
        Mobno=findViewById(R.id.mobno);
        Address=findViewById(R.id.address);
        Pincode=findViewById(R.id.pincode);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()) {
                    makePayment();
                }
            }
        });
        totalamount=findViewById(R.id.totalprice);
        totban=findViewById(R.id.totbanner);
        Intent i=getIntent();
        tsum=i.getExtras().getInt("totalsum");
        Log.d("Totsum", String.valueOf(tsum));
//        SharedPreferences b = getSharedPreferences("cartdetails", MODE_PRIVATE);
//        int tsum=b.getInt("total",0);
        totalamount.setText(String.valueOf(tsum));
        totban.setText("Total Amount :"+tsum +"Rs");

//        Log.d("Mobile No:",mobno);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("mytotalprice"));
    }
    boolean valid(){

        String MobilePattern = "[0-9]{10}";
        String PincodePattern="[0-9]{6}";

        if (Name.length() == 0 || Mobno.length() == 0 || Address.length() == 0 || Pincode.length() == 0 ) {
            Toast.makeText(getApplicationContext(), "please fill the empty fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!Pincode.getText().toString().matches(PincodePattern)) {
            Toast.makeText(getApplicationContext(), "Please enter valid 6 digit Pincode", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!Mobno.getText().toString().matches(MobilePattern)) {
            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Pincode.getText().toString().matches(PincodePattern)) {
            return true;
        }
       if(Mobno.getText().toString().matches(MobilePattern)) {
//                    Toast.makeText(getApplicationContext(), "phone number is valid", Toast.LENGTH_SHORT).show();
return true;
        }
//       else if (Address.length() > 25) {
//            Toast.makeText(getApplicationContext(), "pls enter full address properly", Toast.LENGTH_SHORT).show();
//            return false;
//
//        }
        return false;
    }
//    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int totalBill=intent.getIntExtra("totalAmount",0);
//            totalamount.setText(String.valueOf(totalBill));
//        }
//    };
    private void makePayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_vt6aef5g6GP2Rn");
        checkout.setImage(R.drawable.logo);
        SharedPreferences b = getSharedPreferences("Logindetails", MODE_PRIVATE);

        name = String.valueOf(Name.getText());
        mobno = Long.parseLong(String.valueOf(Mobno.getText()));
         addr = String.valueOf(Address.getText());
         pin = Integer.parseInt(String.valueOf(Pincode.getText()));

        final Activity activity = this;
        try {
            db=FirebaseFirestore.getInstance();
             did = db.collection("Delivery").document().getId();
            JSONObject options = new JSONObject();
            options.put("name", "Surprise");
            options.put("description", "Reference No."+did);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", tsum*100);//pass amount in currency subunits
            options.put("prefill.email", b.getString("email",""));
            options.put("prefill.contact",mobno);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("MSSG : ", "Error in starting Razorpay Checkout", e);
        }

    }


    public void onPaymentSuccess(String razorpayPaymentID) {
        Log.d("placeorderpage", "hello");
        SharedPreferences b = getSharedPreferences("Logindetails", MODE_PRIVATE);
        try {
            db=FirebaseFirestore.getInstance();

            db.collection("Cart").whereEqualTo("uid",b.getString("uid","")).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
//                            cartarraylit.add.
//                            (dc.getDocument().toObject(cartrecycle.class));
                            String oid = db.collection("Order").document().getId();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("cid",dc.getDocument().get("cid"));
                            map.put("imageurl",dc.getDocument().get("imageurl"));
                            map.put("name",dc.getDocument().get("name"));
                            map.put("pid",dc.getDocument().get("pid"));
                            map.put("quantity",dc.getDocument().get("requiredquantity"));
                            map.put("totalprice",dc.getDocument().get("totalprice"));
                            map.put("uid",dc.getDocument().get("uid"));
                            map.put("oid",oid);
                            map.put("did",did);

                            db.collection("Order").document(oid).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            db.collection("Cart").document(String.valueOf(dc.getDocument().get("cid"))).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Item removed",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                }
            });

            HashMap<String, Object> map = new HashMap<>();
            map.put("did",did);
            map.put("uid",b.getString("uid",""));
            map.put("name", name);
            map.put("mobileno", mobno);
            map.put("address", addr);
            map.put("pincode", pin);
            map.put("total",tsum);
            Date currentTime = Calendar.getInstance().getTime();
            map.put("orderedDateTime",currentTime);
            Calendar c = Calendar.getInstance();
            c.setTime(currentTime);
            // manipulate date
            c.add(Calendar.YEAR, 0);
            c.add(Calendar.MONTH, 0);
            c.add(Calendar.DATE, 3);
            Date currentDatePlusOne = c.getTime();
            map.put("deliveryByDateTime",currentDatePlusOne);
            Log.d("datetime", String.valueOf(currentDatePlusOne));

            db.collection("Delivery").document(did).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(), cart.class);
//                        startActivity(i);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed!!", Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception e){
          Log.d("Exception caught", String.valueOf(e));
        }

        Intent i=new Intent(PlaceOrder.this,Orders.class);
        startActivity(i);
            finish();
        }


    public void onPaymentError(int code, String response) {

    }
}