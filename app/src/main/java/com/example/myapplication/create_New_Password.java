package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class create_New_Password extends AppCompatActivity {
    Button continueBtnOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);
        continueBtnOTP=findViewById(R.id.continueBtnOTP);
        continueBtnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(create_New_Password.this,getResources().getString(R.string.passwordResetMsg),Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}