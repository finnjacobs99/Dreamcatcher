package com.example.wombatworkshop_dreamcatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BucketHome extends AppCompatActivity implements RecyclerAdapter.OnItemListener, View.OnClickListener {

    private ArrayList<BucketItem> itemList;
    private RecyclerView recyclerView;
    private Button newItemButton;
    private Bucket currentBucket;
    private TextView bucketName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_home);

        recyclerView = findViewById(R.id.itemListView);
        newItemButton = findViewById(R.id.newItemButton);
        newItemButton.setOnClickListener(this);
        bucketName = findViewById(R.id.bucketName);

        currentBucket = getIntent().getParcelableExtra("bucket");

        bucketName.setText(currentBucket.getBucket_name());

        itemList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bucket")
                .child(currentBucket.getBucket_id()).child("items");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BucketItem item = snapshot.getValue(BucketItem.class);
                itemList.add(item);
                setAdapter();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BucketItem item = snapshot.getValue(BucketItem.class);
                for(int i = 0; i < itemList.size(); i++){
                    if(item.sameID(itemList.get(i))){
                        itemList.set(i, item);
                        break;
                    }
                }
                setAdapter();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(itemList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent viewPrep = new Intent(this, itemInformation.class);
        viewPrep.putExtra("item", itemList.get(position));
        viewPrep.putExtra("bucket", currentBucket);
        startActivity(viewPrep);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newItemButton:
                Intent viewPrep = new Intent(this, NewItem.class);
                viewPrep.putExtra("bucket", currentBucket);
                startActivity(viewPrep);
                break;
        }
    }
}