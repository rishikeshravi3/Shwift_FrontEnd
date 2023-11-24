package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;

public class onboarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // If night mode is enabled, set the theme to Light
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Button to_onboarding_info_1=findViewById(R.id.onboarding_get_shift_button);
        to_onboarding_info_1.setOnClickListener(v -> {
            Intent intent=new Intent(onboarding.this,onboarding_info_1.class);
            startActivity(intent);
        });
    }
}