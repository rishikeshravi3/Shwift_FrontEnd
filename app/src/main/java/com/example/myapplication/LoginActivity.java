package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView to_forgotpassword = findViewById(R.id.forgot_pass);

        to_forgotpassword.setOnClickListener(v -> {
            Intent intent=new Intent(this,passwordReset_Mode_Selection.class);
            startActivity(intent);
        });
    }
}