package com.example.myapplication.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Helper.Common;
import com.example.myapplication.R;

public class ProfileSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_summary);
        EditText Summary = findViewById(R.id.editSummary);
        Button save = findViewById(R.id.button_save_summary);
        ProfileResponseModel obj = Common.getProfileData(this);
        if(obj!=null){
        Summary.setText(obj.emp_summary);
        }
        save.setOnClickListener(v -> {
            UpdateProfileRequest req = new UpdateProfileRequest();
            req.col_name="emp_summary";
            req.value = Summary.getText().toString().trim();
            UpdateProfileService.Service(this, req, new UpdateProfileService.UpdateProfileCallback() {
                @Override
                public void onUpdateSuccess() {
                    finish();
                }

                @Override
                public void onUpdateFailure() {
                    // Handle update failure if needed
                    Toast.makeText(ProfileSummaryActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}