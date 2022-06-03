package com.example.wombatworkshop_dreamcatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    public Button newBucket;
    private ListView bucketList;
    private ArrayList<String> bucketNames = new ArrayList<>();
    private ArrayList<Bucket> bucketInfos = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private FirebaseUser user;
    private DatabaseReference dbReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        newBucket = (Button) findViewById(R.id.newBucket);
        newBucket.setOnClickListener(this);

        bucketList = (ListView) findViewById(R.id.bucketListView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bucketNames);
        bucketList.setAdapter(arrayAdapter);

        dbReference = FirebaseDatabase.getInstance().getReference("User");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        dbReference.child(userID).child("bucketID").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Bucket temp = snapshot.getValue(Bucket.class);
                bucketNames.add(temp.getBucket_name());
                bucketInfos.add(temp);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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

        bucketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bucket clickedBucket = (Bucket) bucketInfos.get(i);
                Intent viewPrep = new Intent(HomeScreen.this, BucketHome.class);
                viewPrep.putExtra("bucket", clickedBucket);
                startActivity(viewPrep);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newBucket:
                startActivity(new Intent(this,NewBucket.class));
                break;
        }
    }
}