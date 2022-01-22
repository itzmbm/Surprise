package com.example.surprise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PlaceOrder extends AppCompatActivity implements PaymentResultListener {
    TextView totalamount;
    private Button btn;
    int tsum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        btn=findViewById(R.id.order);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });
        totalamount=findViewById(R.id.totalprice);
        Intent i=getIntent();
        tsum=i.getExtras().getInt("totalsum");
        Log.d("Totsum", String.valueOf(tsum));
        totalamount.setText(String.valueOf(tsum));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("mytotalprice"));
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
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","8105831133");
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