package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.EmployerView.Employer_Account_Setup_Company_details;
import com.example.myapplication.Helper.Common;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class AccountSetupHome extends AppCompatActivity {

    private Chip selectedChip;
    private String JobType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup_home);
        Button to_accountsetup = findViewById(R.id.setupHome1);
        to_accountsetup.setOnClickListener(v -> {
            if (selectedChip != null) {
                Intent intent = getIntent();
                String dataFromActivity1 = intent.getStringExtra("PasswordKey");
                if (JobType!=null){
                intent.putExtra("JobType",JobType);
                if(JobType.equals("employee")){
                    intent = new Intent(AccountSetupHome.this, Sign_up_screen.class);
                    intent.putExtra("PasswordKey", dataFromActivity1);
                    startActivity(intent);
                }else{
                    intent = new Intent(AccountSetupHome.this, Employer_Account_Setup_Company_details.class);
                    intent.putExtra("PasswordKey", dataFromActivity1);
                    startActivity(intent);
                }

                }
            } else {
                // Show a message or perform some action to inform the user that a chip needs to be selected
                Common.printShort(this,"Please select a Job type");
            }
        });
//        Chip to_color_findJob_chip = findViewById(R.id.findJob_chip_home);
//        to_color_findJob_chip.setOnClickListener(v -> {
//            TextView heading = findViewById(R.id.find_a_job);
//            TextView subText = findViewById(R.id.i_want_to_f);
//            heading.setTextColor(getResources().getColor(R.color.white));
//            subText.setTextColor(getResources().getColor(R.color.white));
//            to_color_findJob_chip.setChipBackgroundColorResource(R.color.purple);
//        });
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            View child = chipGroup.getChildAt(i);
            if (child instanceof Chip) {
                final Chip chip = (Chip) child;
                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Deselect the previously selected Chip
                        if (selectedChip != null) {
                            Log.i("HAM", "Logging");
                            selectedChip.setChipBackgroundColorResource(R.color.white);
                            selectedChip.setChecked(false);
                        }

                        // Select the clicked Chip
                        selectedChip = chip;
                        int selectedChipId = chip.getId();
                        if (selectedChipId == R.id.findJob_chip_home) {
                            JobType = "employee";
                        } else if (selectedChipId == R.id.employee_chip_home) {
                           JobType = "employer";
                        }
                        selectedChip.setChipBackgroundColorResource(androidx.cardview.R.color.cardview_shadow_start_color);
                        selectedChip.setChecked(true);
                    }
                });
            }
        }
    }
}