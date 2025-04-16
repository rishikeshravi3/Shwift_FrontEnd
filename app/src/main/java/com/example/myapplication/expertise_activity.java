package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class expertise_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertise);
        Button to_signupPage = findViewById(R.id.expertcontinuebtn);
        to_signupPage.setOnClickListener(v -> {
//            Intent intent = getIntent();
//            String dataFromActivity2 = intent.getStringExtra("PasswordKey");
//            String dataFromActivity3 = intent.getStringExtra("JobType");
//             //intent=new Intent(this,Sign_up_screen.class);
//             intent.putExtra("PasswordKey",dataFromActivity2);
//            intent.putExtra("JobType",dataFromActivity3);
//            startActivity(intent);
        });
    }
}