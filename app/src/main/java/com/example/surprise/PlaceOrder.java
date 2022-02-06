package com.example.surprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Objects;

public class PlaceOrder extends AppCompatActivity implements PaymentResultListener {
    TextView Name,Mobno,Address,Pincode,totalamount,totban;
    private Button btn;
    String name,address,mobno="";
  String pincode="";
    int tsum=0;
    boolean valid=false;
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
        totalamount.setText(String.valueOf(tsum));
        totban.setText("Total Amount :"+ (String.valueOf(tsum)) +"Rs");

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
        mobno=String.valueOf(Mobno.getText());
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Surprise");
            options.put("description", "Reference No. #123456");
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
        Intent i=new Intent(PlaceOrder.this,PaymentSuccess.class);
        startActivity(i);
        finish();
    }


    public void onPaymentError(int code, String response) {

    }
}