package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_EMAIL = "emailKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");

        EditText Email = findViewById(R.id.email_id_login);
        Email.setText(savedEmail);
        TextView to_forgotpassword = findViewById(R.id.forgot_pass);

        to_forgotpassword.setOnClickListener(v -> {
            Intent intent=new Intent(this,passwordReset_Mode_Selection.class);
            startActivity(intent);
        });
        Button loginbtn = findViewById(R.id.button_account_login);
        loginbtn.setOnClickListener(v -> {
            Toast.makeText(this,"Implementation in Progress...",Toast.LENGTH_SHORT).show();
        });
        TextView to_createAccount = findViewById(R.id.go_to_create_account);
        to_createAccount.setOnClickListener(v -> {
          finish();
        });
    }
}