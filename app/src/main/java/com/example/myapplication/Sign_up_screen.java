package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class Sign_up_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        Button Toasttemp = findViewById(R.id.signup_screen__continue_btn);

        Toasttemp.setOnClickListener(v->{
            Toast.makeText(this, "Implementation in Progress...", Toast.LENGTH_LONG).show();
        });

    }
}