package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.Constants;
import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.ProfileRequestModel;
import com.example.myapplication.Profile.ProfileResponseModel;
import com.example.myapplication.R;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employee_Detailspage extends AppCompatActivity {
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detailspage);

        TextView name = findViewById(R.id.textViewEmployeeName);
        TextView currpositon = findViewById(R.id.textViewPosition);
        TextView Education = findViewById(R.id.textViewEmployeeEducation);
        TextView Skills = findViewById(R.id.textViewEmployeeSkills);
        TextView Summary = findViewById(R.id.bodySummary);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        LoginModel obj = Common.getUserData(this);
        name.setText(obj.first_name + " " + obj.last_name);

        Intent intent = getIntent();
        String res =intent.getStringExtra("EmployeeEmail");
        getProfileDetails(res);

        ProfileResponseModel object = Common.getProfileData(Employee_Detailspage.this);
        if(object!=null){
            currpositon.setText(object.curr_employment_status);
            Education.setText(object.emp_education);
            Summary.setText(object.emp_summary);
            Skills.setText(object.emp_skills);

        }


    }

    private void getProfileDetails(String Email) {
        Dialog dialog = Common.progressDialog(this);
        dialog.show();
        ProfileRequestModel request = new ProfileRequestModel();
        request.emailId =Email;
        Call<ProfileResponseModel> call = apiInterface.getProfileInfo(request);
        call.enqueue(new Callback<ProfileResponseModel>() {
            @Override
            public void onResponse(Call<ProfileResponseModel> call, Response<ProfileResponseModel> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    try {
                        ProfileResponseModel json = response.body();
                        Gson gson = new Gson();
                        String jsonStr = gson.toJson(json);
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.KEY_PROFILE_DATA, jsonStr);
                        editor.apply();

//                        recentJobs = g.fromJson(json, new TypeToken<ArrayList<JobModel>>(){}.getType());
//                        JobsAdapter jobsAdapter = new JobsAdapter(JobListingActivity.this, recentJobs);
//                        recentJobsView.setAdapter(jobsAdapter);
//                        recommendedJobsView.setAdapter(jobsAdapter);
                    } catch (Exception e) {

                    }
                } else {
                    //Common.print(ProfileActivity.this, "Failed to get job list");
                }
            }

            @Override
            public void onFailure(Call<ProfileResponseModel> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
}