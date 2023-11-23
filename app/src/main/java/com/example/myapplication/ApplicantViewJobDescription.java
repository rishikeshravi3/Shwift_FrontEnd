package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

public class ApplicantViewJobDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_view_job_description);

//dummy code
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