package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Calendar;

public class JobDetailsActivity extends AppCompatActivity {
    String[] itemsIndustry;
    String[] itemsEducation;
    String[] itemsWork;

    String[] itemsJob;
    String[] itemsExperience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Spinner industryType = findViewById(R.id.job_details_activity_industryType_EditText);
        Spinner education = findViewById(R.id.job_details_activity_education_EditText);
        Spinner work=findViewById(R.id.job_details_activity_workType_EditText);
        Spinner job=findViewById(R.id.job_details_activity_jobType_EditText);
        Spinner experience=findViewById(R.id.job_details_activity_experience_EditText);
        TextView date=findViewById(R.id.job_details_activity_hiringDeadline_EditText);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr =Calendar.getInstance();
                int day=cldr.get(Calendar.DAY_OF_MONTH);
                int month=cldr.get(Calendar.MONTH);
                int year=cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(JobDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                picker.show();
            }
        });

        itemsIndustry = new String[]{"Accounting and Finance",
                "Administration",
                "Architecture and Engineering",
                "Arts and Sports",
                "Customer Service",
                "Education and Training",
                "General Services",
                "Health and Medical",
                "Hospitality and Tourism",
                "Human Resources",
                "IT and Software",
                "Legal",
                "Management and Consultancy",
                "Manufacturing and Production",
                "Media and Creatives",
                "Public Services and NGOs",
                "Safety and Security",
                "Sales and Marketing",
                "Sciences",
                "Supply Chain",
                "Writing and Content"
        };

        itemsEducation=new String[]{"Less Than High School",
        "High School","Diploma","Bachelor's Degree","Master's Degree","Doctoral or Professional Degree"};

        itemsWork=new String[]{"Onsite","Remote"};
        itemsJob=new String[]{"Full Time","Part Time","Freelance","Contractual"};
        itemsExperience=new String[]{"No Experience","1 - 5 Years","6 - 10 Years","More than 10 Years"};

        ArrayAdapter<String> adapterIndustry=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,itemsIndustry);
        industryType.setAdapter(adapterIndustry);
        ArrayAdapter<String> adapterEducation=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,itemsEducation);
        education.setAdapter(adapterEducation);
        ArrayAdapter<String> adapterWork=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,itemsWork);
        work.setAdapter(adapterWork);
        ArrayAdapter<String> adapterJob=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,itemsJob);
        job.setAdapter(adapterJob);
        ArrayAdapter<String> adapterExperience=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,itemsExperience);
        experience.setAdapter(adapterExperience);
    }

}