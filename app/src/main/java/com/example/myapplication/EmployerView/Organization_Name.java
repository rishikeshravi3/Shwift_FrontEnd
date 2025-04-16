package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Helper.Common;
import com.example.myapplication.Profile.ProfileActivity;
import com.example.myapplication.Profile.ProfileResponseModel;
import com.example.myapplication.Profile.Profile_Details_Activity;
import com.example.myapplication.Profile.UpdateProfileRequest;
import com.example.myapplication.Profile.UpdateProfileService;
import com.example.myapplication.R;

public class Organization_Name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_name);
        Button save = findViewById(R.id.button_save);
        EditText Orgname = findViewById(R.id.editOrgName);
        ProfileResponseEmployer obj = Common.getProfileDataEmployer(this);
        if(obj!=null){
            Orgname.setText(obj.org_name);
        }
        save.setOnClickListener(v -> {
            UpdateProfileRequest req = new UpdateProfileRequest();
            req.col_name="org_name";
            req.value = Orgname.getText().toString().trim();
            UpdateEmployerInfo.Service(this, req, new UpdateProfileService.UpdateProfileCallback(){
                @Override
                public void onUpdateSuccess() {
                    // Update successful, start the new activity
                    Intent intent = new Intent(Organization_Name.this, ProfileActivity_employer.class);
                    startActivity(intent);
                }

                @Override
                public void onUpdateFailure() {
                    // Handle update failure if needed
                    Toast.makeText(Organization_Name.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}