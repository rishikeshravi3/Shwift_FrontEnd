package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class onboarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        Button to_onboarding_info_1=findViewById(R.id.onboarding_get_shift_button);
        to_onboarding_info_1.setOnClickListener(v -> {
            Intent intent=new Intent(onboarding.this,onboarding_info_1.class);
            startActivity(intent);
        });
    }
}