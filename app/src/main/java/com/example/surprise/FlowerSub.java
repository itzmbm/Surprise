package com.example.surprise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FlowerSub extends AppCompatActivity {
private Button bn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_sub);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
 bn=findViewById(R.id.button4);
 bn.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent i=new Intent(FlowerSub.this,cart.class);
         startActivity(i);
         finish();
     }
 });
    }
}