package com.ankitsharma.mymall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ankitsharma.mymall.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText name,email,password;
     private FirebaseAuth auth;
     SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registration);
        name = findViewById (R.id.username);
        email = findViewById (R.id.email);
        password = findViewById (R.id.password);
        auth = FirebaseAuth.getInstance ();
        if (auth.getCurrentUser ()!=null){
            startActivity (new Intent (RegistrationActivity.this, MainActivity.class));
            finish ();
        }

        sharedPreferences = getSharedPreferences ("onBoardScreen",MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean ("firstTime",true);
        if (isFirstTime){
            SharedPreferences.Editor  editor = sharedPreferences.edit ();
            editor.putBoolean ("firstTime",false);
            editor.commit ();

            startActivity (new Intent (RegistrationActivity.this, OnBoardingActivity.class));
        }

    }

    public void signup(View view){

        String userName = name.getText ().toString ();
        String userEmail = email.getText ().toString ();
        String userPassword = password.getText ().toString ();
        if (TextUtils.isEmpty (userName)){
            Toast.makeText (this, "Enter name!", Toast.LENGTH_SHORT).show ();
            return;
        }
        if (TextUtils.isEmpty (userEmail)){
            Toast.makeText (this, "Enter email!", Toast.LENGTH_SHORT).show ();
            return;
        }
        if (TextUtils.isEmpty (userPassword)){
            Toast.makeText (this, "Enter password!", Toast.LENGTH_SHORT).show ();
            return;
        }
        if (userPassword.length ()<8){
            Toast.makeText (this, "Password too short! Enter minimum 8 characters", Toast.LENGTH_SHORT).show ();
            return;
        }
        auth.createUserWithEmailAndPassword (userEmail,userPassword)
                .addOnCompleteListener (RegistrationActivity.this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                      if (task.isSuccessful ()) {
                            Toast.makeText (RegistrationActivity.this, "You are successfully registered", Toast.LENGTH_SHORT).show ();
                            startActivity (new Intent (RegistrationActivity.this, MainActivity.class));
                        }
                      else {
                          Toast.makeText (RegistrationActivity.this, "Registration Failed!"+task.getException (), Toast.LENGTH_SHORT).show ();
                      }
                    }
                });
    }
    public void signin(View view){
        startActivity (new Intent (RegistrationActivity.this, LoginActivity.class));
    }
}