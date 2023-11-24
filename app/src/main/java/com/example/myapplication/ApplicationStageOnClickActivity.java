package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Objects;

public class ApplicationStageOnClickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_stage_on_click);
        String company=getIntent().getStringExtra("company");
        String role=getIntent().getStringExtra("role");
        String status=getIntent().getStringExtra("status");
        String location=getIntent().getStringExtra("location");
        String salary=getIntent().getStringExtra("salary");
        ArrayList<String> jobType=getIntent().getStringArrayListExtra("jobType");
        int logoID=getIntent().getIntExtra("logoId",0);

        ImageView logo=findViewById(R.id.application_stage_on_click_card_image);
        TextView companyName=findViewById(R.id.application_stage_on_click_card_company);
        TextView roleName=findViewById(R.id.application_stage_on_click_card_role);
        TextView statusName=findViewById(R.id.application_stage_on_click_your_status);
        TextView locationName=findViewById(R.id.application_stage_on_click_card_location);
        TextView salaryName=findViewById(R.id.application_stage_on_click_card_salary);
        FlexboxLayout flexboxLayout=findViewById(R.id.application_stage_on_click_card_job_type_flexbox);

        logo.setImageResource(logoID);
        companyName.setText(company);
        roleName.setText(role);
        statusName.setText(status);
        locationName.setText(location);
        salaryName.setText(salary);

        TextView[] tv=new TextView[10];
        for(int i = 0; i< Objects.requireNonNull(jobType).size(); i++)
        {
            tv[i]=new TextView(getApplicationContext());
            tv[i].setText(jobType.get(i));
            FlexboxLayout.LayoutParams params=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(25,25,25,25);
            tv[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv[i].setTextAppearance(R.style.key_skills);
            tv[i].setBackground(ContextCompat.getDrawable(this,R.drawable.key_skills_border));
            tv[i].setPadding(25,25,25,25);
            tv[i].setLayoutParams(params);
            flexboxLayout.addView(tv[i]);
        }


        assert status != null;
        switch (status) {
            case "Application Sent":
                statusName.setTextAppearance(R.style.application_stage_item_status_style_sent);
                statusName.setBackground(ContextCompat.getDrawable(this, R.drawable.application_stage_on_click_status_sent_background));
                break;
            case "Application Accepted":
                statusName.setTextAppearance(R.style.application_stage_item_status_style_accepted);
                statusName.setBackground(ContextCompat.getDrawable(this, R.drawable.application_stage_on_click_status_accepted_background));
                break;
            case "Application Rejected":
                statusName.setTextAppearance(R.style.application_stage_item_status_style_rejected);
                statusName.setBackground(ContextCompat.getDrawable(this, R.drawable.application_stage_on_click_status_rejected_background));
                break;
        }

        ImageView backArrow=findViewById(R.id.application_stage_on_click_back_arrow);
        backArrow.setOnClickListener(v -> finish());

    }
}