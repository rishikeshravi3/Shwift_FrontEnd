package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.card.MaterialCardView;

public class ApplicantViewJobDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_view_job_description);

        RelativeLayout item=findViewById(R.id.applicant_view_job_description_detail_card_relative_layout);
        View child = getLayoutInflater().inflate(R.layout.job_list_item,null);
        child.findViewById(R.id.bookmark).setVisibility(View.GONE);
        MaterialCardView.LayoutParams jobListingLayoutParams=new MaterialCardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        child.setLayoutParams(jobListingLayoutParams);
        item.addView(child);

//dummy code
        TextView jobDescription=findViewById(R.id.applicant_view_job_description_job_description_body);
        jobDescription.setText("We are looking for an experienced and talented UI designer to design and shape unique, user-centric products and experiences. The ideal candidate will have experience working in agile teams, with developers, UX designers, and copywriters. You should have an eye for clean and artful design, possess superior UI skills, and be able to translate high-level requirements into interaction flows and artifacts and transform them into beautiful, intuitive, and functional user interfaces.\n");

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
        FlexboxLayout flexboxLayout=findViewById(R.id.applicant_view_job_description_key_skills_body);
        TextView[] tv = new TextView[10];
        tv[0]=new TextView(getApplicationContext());
        tv[1]=new TextView(getApplicationContext());
        tv[2]=new TextView(getApplicationContext());
        tv[3]=new TextView(getApplicationContext());
        tv[0].setText("Machine Learning");
        tv[1].setText("Artificial Intelligence");
        tv[2].setText("C++");
        tv[3].setText("Java");

        for(int i=0;i<=3;i++) {
            FlexboxLayout.LayoutParams params=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(25,25,25,25);
            tv[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv[i].setTextAppearance(R.style.key_skills);
            tv[i].setBackground(ContextCompat.getDrawable(this,R.drawable.key_skills_border));
            tv[i].setPadding(25,25,25,25);
            tv[i].setLayoutParams(params);
            flexboxLayout.addView(tv[i]);
        }

//Dummy Code ends
    }
}