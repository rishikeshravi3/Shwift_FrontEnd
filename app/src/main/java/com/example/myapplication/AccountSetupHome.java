package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AccountSetupHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup_home);
        Button to_accountsetup = findViewById(R.id.setupHome1);
        to_accountsetup.setOnClickListener(v -> {
            Intent intent=new Intent(this,expertise_activity.class);
            startActivity(intent);
        });
    }
}