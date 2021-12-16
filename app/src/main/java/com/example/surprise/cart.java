package com.example.surprise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Objects;

public class cart extends AppCompatActivity implements PaymentResultListener {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Checkout.preload(getApplicationContext());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cart");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
btn=findViewById(R.id.button2);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        makePayment();

    }
});


    }

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
            options.put("amount", "50000");//pass amount in currency subunits
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
        Intent i=new Intent(cart.this,PaymentDetails.class);
        startActivity(i);
        finish();
    }


    public void onPaymentError(int code, String response) {

    }
}