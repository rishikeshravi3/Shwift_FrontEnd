package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Helper.Common;
import com.example.myapplication.Profile.UpdateProfileRequest;
import com.example.myapplication.Profile.UpdateProfileService;
import com.example.myapplication.R;

public class OrgDescription_Employer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_description_employer);
        Button save = findViewById(R.id.button_save);
        EditText Orgdesc = findViewById(R.id.editOrgDesc);
        ProfileResponseEmployer obj = Common.getProfileDataEmployer(this);
        if(obj!=null){
            Orgdesc.setText(obj.org_desc);
        }
        save.setOnClickListener(v -> {
            UpdateProfileRequest req = new UpdateProfileRequest();
            req.col_name="org_desc";
            req.value = Orgdesc.getText().toString().trim();
            UpdateEmployerInfo.Service(this, req, new UpdateProfileService.UpdateProfileCallback(){
                @Override
                public void onUpdateSuccess() {
                    // Update successful, start the new activity
                    Intent intent = new Intent(OrgDescription_Employer.this, ProfileActivity_employer.class);
                    startActivity(intent);
                }

                @Override
                public void onUpdateFailure() {
                    // Handle update failure if needed
                    Toast.makeText(OrgDescription_Employer.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}