package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Button loginbtn = findViewById(R.id.button_create_account);
        loginbtn.setOnClickListener(v -> {
            Toast.makeText(this,"Implementation in Progress...",Toast.LENGTH_SHORT).show();
        });
        TextView to_createAccount = findViewById(R.id.go_to_create_account);
        to_createAccount.setOnClickListener(v -> {
          finish();
        });
    }
}