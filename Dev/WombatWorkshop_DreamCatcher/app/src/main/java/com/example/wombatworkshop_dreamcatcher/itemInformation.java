package com.example.wombatworkshop_dreamcatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class itemInformation extends AppCompatActivity implements View.OnClickListener {
    private TextView itemName;
    private TextView completeBy;
    private TextView description;
    private CheckBox checkBox;
    private TextView priorityText;
    private SeekBar priorityBar;

    private Button delete;
    private Bucket currentBucket;
    private BucketItem currentItem;

    private FirebaseUser user;
    private DatabaseReference dbReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        itemName = findViewById(R.id.itemName);
        completeBy = findViewById(R.id.completeByDate);
        description = findViewById(R.id.descriptionText);
        checkBox = findViewById(R.id.checkBox);
        priorityText = findViewById(R.id.priorityText2);
        priorityBar = findViewById(R.id.seekBar2);

        delete = findViewById(R.id.deleteItem);

        currentItem = getIntent().getParcelableExtra("item");
        currentBucket = getIntent().getParcelableExtra("bucket");
        Log.d("ITEM: ", currentItem.toString());

        itemName.setText(currentItem.getItemName());
        completeBy.setText(currentItem.getDate());
        description.setText(currentItem.getDescription());
        description.setMovementMethod(new ScrollingMovementMethod());
        checkBox.setChecked(currentItem.getIsCompleted());
        priorityBar.setProgress(currentItem.getPriority());
        priorityText.setText("Priority: " + currentItem.getPriority());

        priorityBar.setEnabled(false);
        checkBox.setOnClickListener(this);
        delete.setOnClickListener(this);

        dbReference = FirebaseDatabase.getInstance().getReference("Bucket");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.deleteItem:
                confirmDelete().show();
                break;

            case R.id.checkBox:
                updateCheckbox();
                break;
        }
    }

    private void updateCheckbox() {
        currentItem.setIsCompleted(checkBox.isChecked());
        dbReference.child(currentBucket.getBucket_id()).child("items").child(currentItem.getItem_id()).child("isCompleted")
                .setValue(currentItem.getIsCompleted());
    }

    private Dialog confirmDelete() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(itemInformation.this);
        dialog.setMessage("Are you sure you want to delete this item?")
                .setTitle("Delete Item")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return dialog.create();
    }

    private void deleteItem() {
        dbReference.child(currentBucket.getBucket_id()).child("items").child(currentItem.getItem_id()).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(itemInformation.this, "Deleted Item", Toast.LENGTH_SHORT).show();
                Intent viewPrep = new Intent(itemInformation.this, BucketHome.class);
                viewPrep.putExtra("bucket", currentBucket);
                startActivity(viewPrep);
            }
        });

    }
}