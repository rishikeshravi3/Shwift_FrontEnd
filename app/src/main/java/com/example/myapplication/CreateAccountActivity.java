package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ImageView showPassword = findViewById(R.id.show_password);
        EditText password = findViewById(R.id.password);
        showPassword.setOnClickListener(v -> {
            if (password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                showPassword.setBackgroundResource(R.drawable.eye_slash_regular);
            } else {
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showPassword.setBackgroundResource(R.drawable.eye_regular);
            }
        });

        ImageView showConfirmPassword = findViewById(R.id.show_confirm_password);
        EditText confirm_password = findViewById(R.id.confirm_password);
        showConfirmPassword.setOnClickListener(v -> {
            if (confirm_password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                confirm_password.setInputType(InputType.TYPE_CLASS_TEXT);
                showConfirmPassword.setBackgroundResource(R.drawable.eye_slash_regular);
            } else {
                confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showConfirmPassword.setBackgroundResource(R.drawable.eye_regular);
            }
        });

        TextView login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        Button to_accountsetup = findViewById(R.id.button_create_account);
        to_accountsetup.setOnClickListener(v -> {
            Intent intent=new Intent(this, AccountSetupHome.class);
            startActivity(intent);
        });

    }
}