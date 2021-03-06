package com.example.surprise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class flowerSub2 extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<flowerrecycle> flowerarraylist;
floweradapter myAdapter;
FirebaseFirestore db;
ProgressDialog  pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_sub2);
        Window window = flowerSub2.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(flowerSub2.this, R.color.black));
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("fetching data");
        pd.show();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Flowers");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E7879A")));
  recyclerView=findViewById(R.id.recyclerview);
  recyclerView.setHasFixedSize(true);
  recyclerView.setLayoutManager(new LinearLayoutManager(this));

  db=FirebaseFirestore.getInstance();
  flowerarraylist=new ArrayList<>();
  myAdapter=new floweradapter(flowerSub2.this,flowerarraylist);
recyclerView.setAdapter(myAdapter);
  EventChangeListener();
    }

    private void EventChangeListener() {
    db.collection("Flowers").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
    flowerarraylist.add(dc.getDocument().toObject(flowerrecycle.class));

}
myAdapter.notifyDataSetChanged();
    if(pd.isShowing()){
        pd.dismiss();
    }
}
        }
    });
    }
}