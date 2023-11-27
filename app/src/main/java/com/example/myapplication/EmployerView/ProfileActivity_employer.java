package com.example.myapplication.EmployerView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Applications.ApplicationStagesActivity;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.Constants;
import com.example.myapplication.JobListing.JobListingActivity;
import com.example.myapplication.JobListing.SavedJobsActivity;
import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.ProfileActivity;
import com.example.myapplication.Profile.ProfileOptionModel;
import com.example.myapplication.Profile.ProfileOptionType;
import com.example.myapplication.Profile.ProfileOptionsAdapter;
import com.example.myapplication.Profile.ProfileRequestModel;
import com.example.myapplication.Profile.ProfileResponseModel;
import com.example.myapplication.Profile.Profile_Details_Activity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity_employer extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    APIInterface apiInterface;
    public static final String PREF_NAME = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_employer);
        TextView name = findViewById(R.id.txtName_profile);
        ImageView EditProfile = findViewById(R.id.editIcon_profile);
        EditProfile.setOnClickListener(v->{
            Intent intent = new Intent(this, Profile_Details_Activity.class);
            startActivity(intent);
        });
        apiInterface = APIClient.getClient().create(APIInterface.class);
        LoginModel obj = Common.getUserData(this);
        name.setText(obj.first_name + " " + obj.last_name);
        getProfileDetails();
        bottomNavigationView = findViewById(R.id.employer_view_home_page_bottom_Navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile_employer);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_employer) {
                startActivity(new Intent(getApplicationContext(), Employer_View_Home_Page.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.addListing_employer) {
                startActivity(new Intent(getApplicationContext(), JobDetailsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.myListing_employer) {
                startActivity(new Intent(getApplicationContext(), ApplicationEmployerList.class));
                overridePendingTransition(0, 0);
                finish();
                return true;

            } else if (itemId == R.id.profile_employer) {
                return true;
            }
            return false;
        });

        List<ProfileOptionModel> profileOptions = getProfileOptions();
        RecyclerView recyclerView = findViewById(R.id.profileOptionsList_Employer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProfileAdapterEmployer adapter = new ProfileAdapterEmployer(this, profileOptions);
        recyclerView.setAdapter(adapter);


        // set up the RecyclerView
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), Employer_View_Home_Page.class));
                overridePendingTransition(0, 0);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }



    public List<ProfileOptionModel> getProfileOptions() {
        List<ProfileOptionModel> options = new ArrayList<ProfileOptionModel>();
        options.add(new ProfileOptionModel((int) ProfileOptionTypeEmployer.ORG_NAME.ordinal(), "Organization Name"));
        options.add(new ProfileOptionModel((int) ProfileOptionTypeEmployer.SUMMARY.ordinal(), "Organization Description"));
//        options.add(new ProfileOptionModel((int)ProfileOptionType.WORK_EXP.ordinal(), "Work Experience"));
//        options.add(new ProfileOptionModel((int)ProfileOptionType.EDUCATION.ordinal(), "Education"));
//        options.add(new ProfileOptionModel((int)ProfileOptionType.PROJECTS.ordinal(), "Projects"));
//        options.add(new ProfileOptionModel((int)ProfileOptionType.SKILLS.ordinal(), "Skills"));
        return options;
    }

    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }

    private void getProfileDetails() {
        LoginModel obj = Common.getUserData(this);
        Dialog dialog = Common.progressDialog(this);
        dialog.show();
        ProfileRequestEmployer request = new ProfileRequestEmployer();
        String email = "rangwala@hamza.com";
        request.emailId = email;
        Call<ProfileResponseEmployer> call = apiInterface.getProfileInfoEmployer(request);
        call.enqueue(new Callback<ProfileResponseEmployer>() {
            @Override
            public void onResponse(Call<ProfileResponseEmployer> call, Response<ProfileResponseEmployer> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    try {
                        ProfileResponseEmployer json = response.body();
                        Gson gson = new Gson();
                        String jsonStr = gson.toJson(json);
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.KEY_PROFILE_EMPLOYER_DATA, jsonStr);
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
            public void onFailure(Call<ProfileResponseEmployer> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
}