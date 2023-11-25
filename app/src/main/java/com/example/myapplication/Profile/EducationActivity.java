package com.example.myapplication.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Helper.Common;
import com.example.myapplication.R;

public class EducationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        EditText Education = findViewById(R.id.editEducation);
        Button save = findViewById(R.id.button_save_education);
        ProfileResponseModel obj = Common.getProfileData(this);
        if(obj!=null){
        Education.setText(obj.emp_education);
        }
        save.setOnClickListener(v -> {
            UpdateProfileRequest req = new UpdateProfileRequest();
            req.col_name="emp_education";
            req.value = Education.getText().toString().trim();
            UpdateProfileService.Service(this, req, new UpdateProfileService.UpdateProfileCallback() {
                @Override
                public void onUpdateSuccess() {
                    // Update successful, start the new activity
                    Intent intent = new Intent(EducationActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onUpdateFailure() {
                    // Handle update failure if needed
                    Toast.makeText(EducationActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });

        });

    }
}