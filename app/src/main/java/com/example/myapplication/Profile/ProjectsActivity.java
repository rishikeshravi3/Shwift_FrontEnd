package com.example.myapplication.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Helper.Common;
import com.example.myapplication.R;

public class ProjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        EditText Project = findViewById(R.id.editProjects);
        Button save = findViewById(R.id.button_save_projects);
        ProfileResponseModel obj = Common.getProfileData(this);
        if(obj!=null){
            Project.setText(obj.emp_projects);
        }
        save.setOnClickListener(v -> {
            UpdateProfileRequest req = new UpdateProfileRequest();
            req.col_name="emp_projects";
            req.value = Project.getText().toString().trim();
            UpdateProfileService.Service(this, req, new UpdateProfileService.UpdateProfileCallback() {
                @Override
                public void onUpdateSuccess() {
                    // Update successful, start the new activity
                    Intent intent = new Intent(ProjectsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onUpdateFailure() {
                    // Handle update failure if needed
                    Toast.makeText(ProjectsActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}