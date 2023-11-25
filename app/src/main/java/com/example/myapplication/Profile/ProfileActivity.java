package com.example.myapplication.Profile;

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
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.Constants;
import com.example.myapplication.JobListing.JobListinRequest;
import com.example.myapplication.JobListing.JobListingActivity;
import com.example.myapplication.JobListing.JobModel;
import com.example.myapplication.JobsAdapter;
import com.example.myapplication.LoginModel;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    APIInterface apiInterface;
    public static final String PREF_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.saved_jobs);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), JobListingActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.saved_jobs) {
                return true;
            } else if (itemId == R.id.applications) {
                return true;
            } else if (itemId == R.id.profile) {
                return true;
            }
            return false;
        });

        List<ProfileOptionModel> profileOptions = getProfileOptions();
        RecyclerView recyclerView = findViewById(R.id.profileOptionsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProfileOptionsAdapter adapter = new ProfileOptionsAdapter(this, profileOptions);
        recyclerView.setAdapter(adapter);


        // set up the RecyclerView
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), JobListingActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public List<ProfileOptionModel> getProfileOptions() {
        List<ProfileOptionModel> options = new ArrayList<ProfileOptionModel>();
        options.add(new ProfileOptionModel((int)ProfileOptionType.CONTACT_INFO.ordinal(), "Contact Information"));
        options.add(new ProfileOptionModel((int)ProfileOptionType.SUMMARY.ordinal(), "Summary"));
        options.add(new ProfileOptionModel((int)ProfileOptionType.WORK_EXP.ordinal(), "Work Experience"));
        options.add(new ProfileOptionModel((int)ProfileOptionType.EDUCATION.ordinal(), "Education"));
        options.add(new ProfileOptionModel((int)ProfileOptionType.PROJECTS.ordinal(), "Projects"));
        options.add(new ProfileOptionModel((int)ProfileOptionType.SKILLS.ordinal(), "Skills"));
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
        ProfileRequestModel request = new ProfileRequestModel();

        request.emailId = "va_thakur532";
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
                    Common.print(ProfileActivity.this, "Failed to get job list");
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