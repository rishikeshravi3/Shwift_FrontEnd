package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class expertise_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertise);
        Button to_signupPage = findViewById(R.id.expertcontinuebtn);
        to_signupPage.setOnClickListener(v -> {
            Intent intent=new Intent(this,Sign_up_screen.class);
            startActivity(intent);
        });
    }
}