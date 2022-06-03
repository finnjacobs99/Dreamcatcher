package com.example.wombatworkshop_dreamcatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewBucket extends AppCompatActivity implements View.OnClickListener {

    private EditText bucketName;
    private RadioGroup privacyOptions;
    private RadioButton radioButton;
    private Button createButton;

    private DatabaseReference dbReference;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bucket);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        bucketName = findViewById(R.id.editBucketName);

        privacyOptions = findViewById(R.id.privacyOptions);

        createButton = (Button) findViewById(R.id.createBucket);
        createButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createBucket:
                // Get privacy option
                radioButton = findViewById(privacyOptions.getCheckedRadioButtonId());

                Bucket bucketForBucket = new Bucket(userID, new ArrayList<String>(), new ArrayList<String>());

                // Make reference to the Bucket path
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bucket/");
                // Get id for new item in Bucket
                String id = reference.push().getKey();

                // Make the bucket with collected data that will be stored in the users information
                Bucket bucketForUser = new Bucket("", bucketName.getText().toString().trim(), radioButton.getText().toString(), id);

                // Set values in Bucket
                reference.child(id).setValue(bucketForBucket)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // Sets values in User
                                FirebaseDatabase.getInstance().getReference("User")
                                        .child((FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                        .child("bucketID").push().setValue(bucketForUser);
                                if(task.isSuccessful()){
                                    startActivity(new Intent(NewBucket.this, HomeScreen.class));
                                    Toast.makeText(NewBucket.this, "Created "+bucketName.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(NewBucket.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                break;
        }
    }
}