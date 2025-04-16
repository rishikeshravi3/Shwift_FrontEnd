package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class onboarding_info_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_info_3);
        Button to_createaccount = findViewById(R.id.onboarding_info_3_button);

        to_createaccount.setOnClickListener(v -> {
            Intent intent=new Intent(this,CreateAccountActivity.class);
            startActivity(intent);
        });

    }
}