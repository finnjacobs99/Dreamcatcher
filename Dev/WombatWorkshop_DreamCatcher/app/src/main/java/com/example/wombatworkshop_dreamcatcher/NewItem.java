package com.example.wombatworkshop_dreamcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewItem extends AppCompatActivity implements View.OnClickListener {
    private TextView itemName;
    private DatePicker completeDate;
    private TextView description;
    private Button create;
    private Bucket currentBucket;
    private TextView priorityText;
    private int priorityValue = 3;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        currentBucket = getIntent().getParcelableExtra("bucket");

        itemName = findViewById(R.id.editName);
        completeDate = findViewById(R.id.datePicker1);
        description = findViewById(R.id.editDescription);
        priorityText = findViewById(R.id.priorityText);
        seekBar = findViewById(R.id.seekBar);

        create = findViewById(R.id.createItem);
        create.setOnClickListener(this);
        seekBar.setProgress(3);
        priorityText.setText("Priority: 3");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                priorityText.setText("Priority: " + i);
                priorityValue = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.createItem:
                createItem(itemName, completeDate, description);
                break;
        }
    }

    private void createItem(TextView itemName, DatePicker completeDate, TextView _description) {
        String month = String.valueOf(completeDate.getMonth());
        String day = String.valueOf(completeDate.getDayOfMonth());
        String year = String.valueOf(completeDate.getYear());
        String name = itemName.getText().toString().trim();
        String description = _description.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bucket").child(currentBucket.getBucket_id()).child("items");
        String id = reference.push().getKey();
        BucketItem item = new BucketItem(name, month, day, year, description, Boolean.FALSE, id, priorityValue);
        reference.child(id).setValue(item);

        Intent viewPrep = new Intent(this, BucketHome.class);
        viewPrep.putExtra("bucket", currentBucket);
        startActivity(viewPrep);
    }
}