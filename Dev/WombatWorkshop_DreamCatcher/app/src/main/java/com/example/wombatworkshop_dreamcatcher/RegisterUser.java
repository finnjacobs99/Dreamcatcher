package com.example.wombatworkshop_dreamcatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Locale;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    public TextView banner;
    public EditText editFName, editLName, editEmail, editPassword;
    public TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.registerBanner);
        banner.setOnClickListener(this);

        registerButton = (TextView) findViewById(R.id.registerUser);
        registerButton.setOnClickListener(this);

        editFName = (EditText) findViewById(R.id.registerFName);
        editLName = (EditText) findViewById(R.id.registerLName);
        editEmail = (EditText) findViewById(R.id.registerEmail);
        editPassword = (EditText) findViewById(R.id.registerPassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerBanner:
                //startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String fName = editFName.getText().toString().trim();
        String lName = editLName.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(fName.isEmpty()){
            editFName.setError("First name is Required!");
            editFName.requestFocus();
            return;
        }
        if(lName.isEmpty()){
            editLName.setError("Last name is Required!");
            editLName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editEmail.setError("Email is required!");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("A valid email is required!");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password is required!");
            editPassword.requestFocus();
            return;
        }
        if(password.length() < 8){
            editPassword.setError("Password length must contain at least 8 characters!");
            editPassword.requestFocus();
            return;
        }


        if (password.toLowerCase().contains(fName.toLowerCase()) || password.toLowerCase().contains(lName.toLowerCase())) {
            editPassword.setError("Password can not contain User Name!");
            editPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fName, lName, email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                                Toast.makeText(RegisterUser.this, "User has been registered sucessfully!", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}