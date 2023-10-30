package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class passwordReset_Mode_Selection extends AppCompatActivity {

    Button sendOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_mode_selection);
        sendOTP=findViewById(R.id.sendOTP);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(passwordReset_Mode_Selection.this, passwordReset_OTP_Validation.class);
                startActivity(intent);
            }
        });
    }
}