package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Calendar;

public class JobDetailsActivity extends AppCompatActivity {

    String[] itemsWork;

    String[] itemsJob;
    String[] itemPriority;
//    String priorityText;
//    String jobText;
//    String workText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Spinner work = findViewById(R.id.job_details_activity_workType_EditText);
        Spinner job = findViewById(R.id.job_details_activity_jobType_EditText);
        Spinner priority = findViewById(R.id.job_details_activity_priority_EditText);
        TextView startDate = findViewById(R.id.job_details_activity_startDate_EditText);
        TextView applicationDeadline = findViewById(R.id.job_details_activity_applicationDeadline_EditText);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(JobDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });

        applicationDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(JobDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        applicationDeadline.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });

//        work.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                Object item = parent.getItemAtPosition(pos);
//                if(item.toString().equals("remote")){
//                    workText="1";
//                }
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//                workText="0";
//            }
//        });

//        work.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                workText = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                workText = parent.getItemAtPosition(0).toString();
//            }
//        });

        itemsWork = new String[]{"Remote","Onsite"};
        itemsJob = new String[]{"Full Time", "Part Time", "Temporary"};
        itemPriority = new String[]{"High", "Medium", "Low"};


        ArrayAdapter<String> adapterWork = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsWork);
        work.setAdapter(adapterWork);
        ArrayAdapter<String> adapterJob = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsJob);
        job.setAdapter(adapterJob);
        ArrayAdapter<String> adapterPriority = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemPriority);
        priority.setAdapter(adapterPriority);

        Button button = findViewById(R.id.job_details_activity_button);
        TextView jobTitle = findViewById(R.id.job_details_activity_jobTitle_EditText);
        TextView jobDesc = findViewById(R.id.job_details_activity_description_EditText);
        TextView jobRequirement = findViewById(R.id.job_details_activity_requirements_EditText);
        TextView pay = findViewById(R.id.job_details_activity_pay_EditText);
        TextView hours = findViewById(R.id.job_details_activity_hours_EditText);
        TextView jobLocation = findViewById(R.id.job_details_activity_location_EditText);
        TextView recruiter = findViewById(R.id.job_details_activity_recruiter_EditText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jobTitle.getText().toString().isEmpty()||jobDesc.getText().toString().isEmpty()||jobRequirement.getText().toString().isEmpty()||
                pay.getText().toString().isEmpty()||hours.getText().toString().isEmpty()||jobLocation.getText().toString().isEmpty()||startDate.getText().toString().isEmpty()
                ||applicationDeadline.getText().toString().isEmpty()||recruiter.getText().toString().isEmpty()){
                    Toast.makeText(JobDetailsActivity.this,"Please Fill All the Fields",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(JobDetailsActivity.this, JobDetailsActivityPreview.class);
                    intent.putExtra("jobTitle", jobTitle.getText().toString());
                    intent.putExtra("jobDesc", jobDesc.getText().toString());
                    intent.putExtra("jobReq", jobRequirement.getText().toString());
                    intent.putExtra("workType", work.getSelectedItem().toString());
                    intent.putExtra("jobPriority", priority.getSelectedItem().toString());
                    intent.putExtra("pay", pay.getText().toString());
                    intent.putExtra("hours", hours.getText().toString());
                    intent.putExtra("jobLocation", jobLocation.getText().toString());
                    intent.putExtra("jobType", job.getSelectedItem().toString());
                    intent.putExtra("startDate", startDate.getText().toString());
                    intent.putExtra("applicationDeadline", applicationDeadline.getText().toString());
                    intent.putExtra("recruiterName", recruiter.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}