package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Objects;

public class ApplicationStageOnClickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_stage_on_click);
        String company = getIntent().getStringExtra("company");
        String role = getIntent().getStringExtra("role");
        String status = getIntent().getStringExtra("status");
        String location = getIntent().getStringExtra("location");
        String salary = getIntent().getStringExtra("salary");
        int postionType = getIntent().getIntExtra("positionType", 0);
        int onSite = getIntent().getIntExtra("onSite", 0);
        int logoID = getIntent().getIntExtra("logoId", 0);
        String logo_co = getIntent().getStringExtra("logo");

        ImageView logo = findViewById(R.id.application_stage_on_click_card_image);
        TextView companyName = findViewById(R.id.application_stage_on_click_card_company);
        TextView roleName = findViewById(R.id.application_stage_on_click_card_role);
        TextView statusName = findViewById(R.id.application_stage_on_click_your_status);
        TextView locationName = findViewById(R.id.application_stage_on_click_card_location);
        TextView salaryName = findViewById(R.id.application_stage_on_click_card_salary);
        TextView txtPositionType = findViewById(R.id.txtPositionType);
        TextView txtOnSite = findViewById(R.id.txtOnSite);

        if (logo_co != null && logo_co.isEmpty() == false) {
            Glide.with(this).load(logo_co).into(logo);
        } else {
            logo.setImageResource(R.drawable.company_icon);
        }
        companyName.setText(company);
        roleName.setText(role);
        statusName.setText(status);
        locationName.setText(location);
        salaryName.setText(salary);

        if (postionType == 1) {
            String text = getResources().getText(R.string.full_time).toString();
            txtPositionType.setText(text);
        } else if (postionType == 2) {
            String text = getResources().getText(R.string.part_time).toString();
            txtPositionType.setText(text);
        } else {
            String text = getResources().getText(R.string.temp).toString();
            txtPositionType.setText(text);
        }

        if (onSite == 1) {
            String text = getResources().getText(R.string.on_site).toString();
            txtOnSite.setText(text);
        } else {
            String text = getResources().getText(R.string.remote).toString();
            txtOnSite.setText(text);
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

        ImageView backArrow = findViewById(R.id.application_stage_on_click_back_arrow);
        backArrow.setOnClickListener(v -> finish());

    }
}