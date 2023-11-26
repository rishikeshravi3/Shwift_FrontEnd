package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.R;
import com.example.myapplication.SignUpModel;
import com.google.android.flexbox.FlexboxLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivityPreview extends AppCompatActivity {
    APIInterface apiInterface;
    int workTypeCode;
    int jobTypeCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_view_job_description);

        RelativeLayout item = (RelativeLayout)findViewById(R.id.applicant_view_job_description_detail_card_relative_layout);
        View child=getLayoutInflater().inflate(R.layout.job_details_preview_card,null);
        item.addView(child);

        Button button=findViewById(R.id.applicant_view_job_description_apply_button);
        button.setText("Post");


        TextView jobTitle=child.findViewById(R.id.recent_applicants_item_txtRole);
        jobTitle.setText(getIntent().getStringExtra("jobTitle"));
        TextView jobLocation=child.findViewById(R.id.recent_applicants_item_txtLocation);
        jobLocation.setText(getIntent().getStringExtra("jobLocation"));
        TextView jobPay=child.findViewById(R.id.recent_applicants_item_txtAvailability);
        jobPay.setText("$ "+getIntent().getStringExtra("pay")+" per hour");
        TextView jobJobType=child.findViewById(R.id.recent_applicants_item_positionType);
        jobJobType.setText(getIntent().getStringExtra("jobType"));
        TextView jobWorkType=child.findViewById(R.id.recent_applicants_item_workType);
        jobWorkType.setText(getIntent().getStringExtra("workType"));

        TextView jobDescription=findViewById(R.id.applicant_view_job_description_job_description_body);
        jobDescription.setText(getIntent().getStringExtra("jobDesc"));
        TextView hours=findViewById(R.id.applicant_view_job_description_NumberOfHours_body);
        hours.setText(getIntent().getStringExtra("hours"));
        TextView recruiter=findViewById(R.id.applicant_view_job_description_recruiter_body);
        recruiter.setText(getIntent().getStringExtra("recruiterName"));
        TextView startDate=findViewById(R.id.applicant_view_job_description_start_date_body);
        startDate.setText(getIntent().getStringExtra("startDate"));
        TextView applicationDeadline=findViewById(R.id.applicant_view_job_description_application_deadline_body);
        applicationDeadline.setText(getIntent().getStringExtra("applicationDeadline"));


        FlexboxLayout flexboxLayout=findViewById(R.id.applicant_view_job_description_key_skills_body);
        FlexboxLayout.LayoutParams params=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(25,25,25,25);
        String skills=getIntent().getStringExtra("jobReq");
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
        if (getIntent().getStringExtra("jobType").equals("Full Time")){
            jobTypeCode=1;
        }
        else if(getIntent().getStringExtra("jobType").equals("Part Time")){
            jobTypeCode=2;
        }
        else if(getIntent().getStringExtra("jobType").equals("Temporary")){
            jobTypeCode=3;
        }

        if(getIntent().getStringExtra("workType").equals("Remote"))
        {
            workTypeCode=0;
        }
        else if(getIntent().getStringExtra("workType").equals("Onsite"))
        {
            workTypeCode=1;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(getIntent().getStringExtra("jobTitle"),getIntent().getStringExtra("jobDesc"),getIntent().getStringExtra("jobReq"),getIntent().getStringExtra("jobPriority"),getIntent().getStringExtra("pay"),
                        getIntent().getStringExtra("hours"),getIntent().getStringExtra("jobLocation"),jobTypeCode,getIntent().getStringExtra("startDate"),getIntent().getStringExtra("applicationDeadline"),
                        getIntent().getStringExtra("recruiterName"),workTypeCode);
            }
        });

//        FlexboxLayout flexboxLayout=findViewById(R.id.applicant_view_job_description_key_skills_body);
//        TextView[] tv = new TextView[10];
//        tv[0]=new TextView(getApplicationContext());
//        tv[1]=new TextView(getApplicationContext());
//        tv[2]=new TextView(getApplicationContext());
//        tv[3]=new TextView(getApplicationContext());
//        tv[0].setText(skills);
//        tv[1].setText("Artificial Intelligence");
//        tv[2].setText("C++");
//        tv[3].setText("Java");
//
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

    }

    private void postData(String jobTitle, String jobDescription, String jobRequirement, String jobPriority,
                          String payScale, String numHours, String jobLocation, int positionType,
                          String startDate, String appDeadline, String recruiterName, int positionOnsite) {

        // below line is for displaying our progress bar.
        apiInterface = APIClient.getClient().create(APIInterface.class);
        // on below line we are creating a retrofit
        // builder and passing our base url
        // below line is to create an instance for our retrofit api class.
        // passing data from our text fields to our modal class.
        JobDetailsModel modal = new JobDetailsModel(jobTitle,jobDescription,jobRequirement,jobPriority,payScale,numHours,jobLocation,positionType,startDate,appDeadline,recruiterName,positionOnsite);

        // calling a method to create a post and passing our modal class.
        Call<JobDetailsResponseModel> call = apiInterface.createListing(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<JobDetailsResponseModel>() {
            @Override
            public void onResponse(Call<JobDetailsResponseModel> call, Response<JobDetailsModel> response) {
                try {
                    JobDetailsModel responseFromAPI = response.body();
                    // on below line we are getting our data from modal class and adding it to our string.
                    String responseString = "Response Code : " + response.code();
                    System.out.println(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // below line we are setting our
                // string to our text view.
            }

            @Override
            public void onFailure(Call<JobDetailsModel> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }
}