package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.Constants;
import com.example.myapplication.JobListing.JobListingActivity;
import com.example.myapplication.JobListing.JobModel;
import com.example.myapplication.Profile.ContactInformationActivity;
import com.example.myapplication.Profile.EducationActivity;
import com.example.myapplication.Profile.ProfileRequestModel;
import com.example.myapplication.Profile.ProfileResponseModel;
import com.example.myapplication.Profile.ProfileSummaryActivity;
import com.example.myapplication.Profile.Profile_Details_Activity;
import com.example.myapplication.Profile.ProjectsActivity;
import com.example.myapplication.Profile.SkillActivity;
import com.example.myapplication.Profile.WorkExperienceActivity;
import com.example.myapplication.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewApplicantProfile extends AppCompatActivity {
    APIInterface apiInterface;
    TextView txtName, txtDesignation, txtPhoneNum, txtEmail, txtSummary, txtWorkExp, txtEducation, txtProjects;
    FlexboxLayout chipContainer;
    ImageView userDp, editProfile, editContact, editSummary, editWorkExp, editEducation, editProjects, editSkill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicant_profile);

        chipContainer = findViewById(R.id.chipContainer);
        txtName = findViewById(R.id.txtName);
        txtDesignation = findViewById(R.id.txtDesignation);
        txtPhoneNum = findViewById(R.id.txtPhoneNum);
        txtEmail = findViewById(R.id.txtEmail);
        txtSummary = findViewById(R.id.txtSummary);
        txtWorkExp = findViewById(R.id.txtWorkExp);
        txtEducation = findViewById(R.id.txtEducation);
        txtProjects = findViewById(R.id.txtProjects);
        userDp = findViewById(R.id.userDp);
        editProfile = findViewById(R.id.editProfile);
        editContact = findViewById(R.id.editContact);
        editSummary = findViewById(R.id.editSummary);
        editWorkExp = findViewById(R.id.editWorkExp);
        editEducation = findViewById(R.id.editEducation);
        editProjects = findViewById(R.id.editProjects);
        editSkill = findViewById(R.id.editSkill);
        Button applyButton = findViewById(R.id.applyButton);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        applyButton.setOnClickListener(v -> {
            postData();
        });
        editProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, Profile_Details_Activity.class));
        });
        editContact.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactInformationActivity.class));
        });
        editSummary.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileSummaryActivity.class));
        });
        editWorkExp.setOnClickListener(v -> {
            startActivity(new Intent(this, WorkExperienceActivity.class));
        });
        editEducation.setOnClickListener(v -> {
            startActivity(new Intent(this, EducationActivity.class));
        });
        editProjects.setOnClickListener(v -> {
            startActivity(new Intent(this, ProjectsActivity.class));
        });
        editSkill.setOnClickListener(v -> {
            startActivity(new Intent(this, SkillActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        chipContainer.removeAllViews();
        getProfileDetails();
    }

    private void postData() {
        LoginModel obj = Common.getUserData(ViewApplicantProfile.this);
        Gson gson = new Gson();
        String jobData = getIntent().getStringExtra("jobData");
        JobModel object = gson.fromJson(jobData, JobModel.class);
        Apply_JobModel modal = new Apply_JobModel(obj.email_id,1, object.job_id,"", object.recruiter_email_id);
        Call<Apply_JobModel> call = apiInterface.applyJob(modal);
        call.enqueue(new Callback<Apply_JobModel>() {
            @Override
            public void onResponse(Call<Apply_JobModel> call, Response<Apply_JobModel> response) {
                try {
                    String responseString = "Response Code : " + response.code();
                    if(response.code()==201 || response.code() == 200){
                        Toast.makeText(ViewApplicantProfile.this,"Application Posted Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ViewApplicantProfile.this, JobListingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        finish();
                        startActivity(intent);
                    }
                    System.out.println(responseString);
                } catch (Exception e) {
                    Common.printShort(ViewApplicantProfile.this,"Api Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Apply_JobModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getProfileDetails() {
        LoginModel obj = Common.getUserData(this);
        Dialog dialog = Common.progressDialog(this);
        dialog.show();
        ProfileRequestModel request = new ProfileRequestModel();
        request.emailId = obj.email_id;
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
                        ProfileResponseModel profileData = gson.fromJson(jsonStr, ProfileResponseModel.class);
                        if (profileData.employee_dp.isEmpty() == false) {
                            Glide.with(ViewApplicantProfile.this).load(profileData.employee_dp).into(userDp);
                        } else {
                            userDp.setImageResource(R.drawable.person);
                        }
                        txtName.setText(profileData.first_name + " " + profileData.last_name);
                        if (profileData.curr_position.isEmpty() == false) {
                            txtDesignation.setText(profileData.curr_position);
                        } else {
                            txtDesignation.setVisibility(View.GONE);
                        }
                        txtPhoneNum.setText(profileData.phone_num);
                        txtEmail.setText(profileData.email_id);
                        if (profileData.emp_summary.isEmpty() == false) {
                            txtSummary.setText(profileData.emp_summary);
                        } else {
                            txtSummary.setText("");
                        }
                        if (profileData.emp_workex.isEmpty() == false) {
                            txtWorkExp.setText(profileData.emp_workex);
                        } else {
                            txtWorkExp.setText("");
                        }
                        if (profileData.emp_education.isEmpty() == false) {
                            txtEducation.setText(profileData.emp_education);
                        } else {
                            txtEducation.setText("");
                        }
                        if (profileData.emp_projects.isEmpty() == false) {
                            txtProjects.setText(profileData.emp_projects);
                        } else {
                            txtProjects.setText("");
                        }
                        if (profileData.emp_skills.isEmpty() == false) {
                            String skillsString = profileData.emp_skills;
                            String[] skillsArray = skillsString.split(",");
                            for (String skill : skillsArray) {
                                Chip chip = new Chip(ViewApplicantProfile.this);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(10, 0, 10, 0);
                                chip.setText(skill.trim());
                                chip.setCloseIconVisible(false);
                                chipContainer.addView(chip,params);
                            }
                        }
                    } catch (Exception e) {

                    }
                } else {
                    Common.print(ViewApplicantProfile.this, "Failed to get profile data");
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