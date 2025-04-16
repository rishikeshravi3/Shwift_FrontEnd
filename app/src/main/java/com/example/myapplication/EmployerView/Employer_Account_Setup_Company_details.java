package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;

public class Employer_Account_Setup_Company_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_account_setup_company_details);

        Button continueButton = findViewById(R.id.employer_account_setup_company_details_button);
        EditText orgNameEditText = findViewById(R.id.employer_account_setup_company_details_organization_name);
        EditText orgStrengthEditText = findViewById(R.id.employer_account_setup_company_details_organization_strength);
        EditText descriptionEditText = findViewById(R.id.employer_account_setup_company_details_company_description);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform validation here

                String orgName = orgNameEditText.getText().toString().trim();
                String orgStrength = orgStrengthEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                if (orgName.isEmpty()) {
                    // Organization Name is empty
                    orgNameEditText.setError("Organization Name is required");
                    return;
                }

                if (orgStrength.isEmpty()) {
                    // Organization Strength is empty
                    orgStrengthEditText.setError("Organization Strength is required");
                    return;
                }

                if (description.isEmpty()) {
                    // Company Description is empty
                    descriptionEditText.setError("Company Description is required");
                    return;
                }

                // If all fields are filled, proceed to the next activity
                // You can start the next activity here
                Intent intent = getIntent();
                String pswd = intent.getStringExtra("PasswordKey");
                intent = new Intent(Employer_Account_Setup_Company_details.this, EmployerAccountSetupRecruiterDetails.class);
                intent.putExtra("OrgName",orgName);
                intent.putExtra("orgDesc",description);
                 intent.putExtra("PasswordKey",pswd);
                // Add any necessary data to the intent
                startActivity(intent);
            }
        });




    }
}