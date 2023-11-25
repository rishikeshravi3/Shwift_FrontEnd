package com.example.myapplication.Profile;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.example.myapplication.R;

public class ContactInformationActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_EMAIL = "emailKey";
    private  static  final String KEY_PHONE = "phoneKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_information);
        EditText phoneNumber = findViewById(R.id.editPhoneNum);
        EditText Emailid = findViewById(R.id.editEmail);
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedPhone = sharedPreferences.getString(KEY_PHONE,"");
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
        Emailid.setText(savedEmail);
        phoneNumber.setText(savedPhone);
    }
}