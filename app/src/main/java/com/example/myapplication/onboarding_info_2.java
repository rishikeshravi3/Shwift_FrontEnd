package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class onboarding_info_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_info_2);
        Button to_onboarding_info_3=findViewById(R.id.onboarding_info_2_button);
        to_onboarding_info_3.setOnClickListener(v -> {
            Intent intent=new Intent(onboarding_info_2.this,onboarding_info_3.class);
            startActivity(intent);
        });
    }
}