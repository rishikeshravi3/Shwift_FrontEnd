package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.JobListing.JobModel;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

public class ApplicantViewJobDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_view_job_description);

        Gson gson = new Gson();
        String jobData=getIntent().getStringExtra("jobData");
        JobModel object = gson.fromJson(jobData, JobModel.class);

        RelativeLayout item=findViewById(R.id.applicant_view_job_description_detail_card_relative_layout);
        View child = getLayoutInflater().inflate(R.layout.job_list_item,null);
        child.findViewById(R.id.bookmark).setVisibility(View.GONE);
        MaterialCardView.LayoutParams jobListingLayoutParams=new MaterialCardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        child.setLayoutParams(jobListingLayoutParams);
        item.addView(child);

        TextView role=child.findViewById(R.id.txtRole);
        role.setText(object.job_title);

        TextView company=child.findViewById(R.id.txtCompanyName);
        company.setText(object.recruiter_name);

        TextView payScale=child.findViewById(R.id.txtSalary);
        payScale.setText("$ "+object.pay_scale+" per hour");

        TextView location=child.findViewById(R.id.txtLocation);
        location.setText(object.job_location);

        TextView desc=findViewById(R.id.applicant_view_job_description_job_description_body);
        desc.setText(object.job_desc);

        TextView hours=findViewById(R.id.applicant_view_job_description_NumberOfHours_body);
        hours.setText(object.num_hours);

        ImageView logo = child.findViewById(R.id.companyLogo);
        if (object.employer_dp != null && object.employer_dp.isEmpty() == false) {
            Glide.with(this).load(object.employer_dp).into(logo);
        }

//        TextView recruiter=findViewById(R.id.applicant_view_job_description_recruiter_body);
//        recruiter.setText(object.recruiter_name);

        int job= object.position_type;
        int work= object.position_onsite;

        String jobText = "Full Time";
        String workText = "Remote";

        if(job==1){
            jobText="Full Time";
        }
        else if(job==2){
            jobText="Part Time";
        }
        else if(job==3){
            jobText="Temporary";
        }

        if(work==1){
            workText="Onsite";
        }
        else if(work==0){
            workText="Remote";
        }

        TextView attr1=child.findViewById(R.id.txtJobAttr1);
        attr1.setText(jobText);

        TextView attr2=child.findViewById(R.id.txtJobAttr2);
        attr2.setText(workText);


        FlexboxLayout flexboxLayout=findViewById(R.id.applicant_view_job_description_key_skills_body);
        FlexboxLayout.LayoutParams params=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(25,25,25,25);
        String skills=object.job_req;
        String[] indSkills=skills.split(",",-2);
        TextView[] tv=new TextView[indSkills.length];
        int i=0;
        for(String sub : indSkills){
            tv[i]=new TextView(this);
            tv[i].setText(sub);
            tv[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv[i].setTextAppearance(R.style.key_skills);
            tv[i].setBackground(ContextCompat.getDrawable(this,R.drawable.key_skills_border));
            tv[i].setPadding(25,25,25,25);
            tv[i].setLayoutParams(params);
            flexboxLayout.addView(tv[i]);
            i=i+1;
        }

        TextView startDate=findViewById(R.id.applicant_view_job_description_start_date_body);
        startDate.setText(object.position_start_date);

        TextView applicationdeadline=findViewById(R.id.applicant_view_job_description_application_deadline_body);
        applicationdeadline.setText(object.application_deadline);

        ImageView backArrow=findViewById(R.id.applicant_view_job_description_back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button apply=findViewById(R.id.applicant_view_job_description_apply_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(ApplicantViewJobDescription.this).inflate(R.layout.apply_job_popup, null);

                // Create a PopupWindow
                PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                // Set a focusable flag to make it respond to touch events outside of the popup
                popupWindow.setFocusable(true);
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int anchorX = location[0] + v.getWidth() / 2;
                int anchorY = location[1] + v.getHeight() / 2;

                // Show the popup at the center of the screen, you can customize the position
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

                Button CVapply=popupView.findViewById(R.id.buttonApplyCV);
                Button applyProfile=popupView.findViewById(R.id.buttonApplyProfile);
                CVapply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ApplicantViewJobDescription.this, Apply_Job.class);
                        intent.putExtra("jobData",jobData);
                        startActivity(intent);
                    }
                });
                applyProfile.setOnClickListener(v1 -> {
                    Intent intent=new Intent(ApplicantViewJobDescription.this, ViewApplicantProfile.class);
                    intent.putExtra("jobData",jobData);
                    startActivity(intent);
                });
            }
        });

//dummy code

//        TextView requirementAndSkills=findViewById(R.id.applicant_view_job_description_key_skills_body);
//        requirementAndSkills.setText("1. We are looking for an experienced and talented UI designer to design and shape unique, user-centric products and experiences.\n2. The ideal candidate will have experience working in agile teams, with developers, UX designers, and copywriters.\n3. You should have an eye for clean and artful design, possess superior UI skills, and be able to translate high-level requirements into interaction flows and artifacts and transform them into beautiful, intuitive, and functional user interfaces.\n");

//        TextView education=findViewById(R.id.);
//        education.setText("UG :BFA in Visual Communication, Diploma in Visual Arts");

//        TextView jobSummary=findViewById(R.id.applicant_view_job_description_job_summary_body);
//        jobSummary.setText("Role :                        UI / UX Designer\n" +
//                "Industry Type :        IT Services & Consulting\n" +
//                "Functional Area :     UX, Design & Architecture\n" +
//                "Employment Type : Full Time, Permanent\n" +
//                "Role Category :       UI / UX\n" +
//                "Vacancy :                 2 openings\n" +
//                "Website :                 www.paypal.com");
//
//        TextView aboutCompany=findViewById(R.id.applicant_view_job_description_about_company_body);
       // aboutCompany.setText("PayPal Holdings, Inc. is one of the largest online payments companies that allow parties to make payments through online funds transfers.\n");

//        TextView[] tv = new TextView[10];
//        tv[0]=new TextView(getApplicationContext());
//        tv[1]=new TextView(getApplicationContext());
//        tv[2]=new TextView(getApplicationContext());
//        tv[3]=new TextView(getApplicationContext());
//        tv[0].setText("Machine Learning");
//        tv[1].setText("Artificial Intelligence");
//        tv[2].setText("C++");
//        tv[3].setText("Java");

//        for(int i=0;i<=3;i++) {
//            FlexboxLayout.LayoutParams params=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(25,25,25,25);
//            tv[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            tv[i].setTextAppearance(R.style.key_skills);
//            tv[i].setBackground(ContextCompat.getDrawable(this,R.drawable.key_skills_border));
//            tv[i].setPadding(25,25,25,25);
//            tv[i].setLayoutParams(params);
//            flexboxLayout.addView(tv[i]);
//        }

//Dummy Code ends
    }
    private void showPopup(View anchorView) {
        // Inflate the popup_layout.xml
        View popupView = LayoutInflater.from(this).inflate(R.layout.apply_job_popup, null);

        // Create a PopupWindow
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        // Set a focusable flag to make it respond to touch events outside of the popup
        popupWindow.setFocusable(true);
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int anchorX = location[0] + anchorView.getWidth() / 2;
        int anchorY = location[1] + anchorView.getHeight() / 2;

        // Show the popup at the center of the screen, you can customize the position
        popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);

//        // Set up the ImageView and TextView in the popup layout
//        ImageView iconImageView = popupView.findViewById(R.id.iconImageView);
//        TextView textView = popupView.findViewById(R.id.textView);

        // You can customize the icon and text here
        // Example: iconImageView.setImageResource(R.drawable.your_custom_icon);
        // Example: textView.setText("Your custom text");
    }
}


