package com.example.surprise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class Registrations extends AppCompatActivity {
Button rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Registration");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
        rg=(Button) findViewById(R.id.register);
        rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}