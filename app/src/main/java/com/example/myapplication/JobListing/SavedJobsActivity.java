package com.example.myapplication.JobListing;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.ApplicantViewJobDescription;
import com.example.myapplication.Applications.ApplicationStagesActivity;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.ProfileActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedJobsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView savedJobsView;
    APIInterface apiInterface;
    ArrayList<JobModel> savedJobs = new ArrayList<>();
    String searchText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs);

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
                startActivity(new Intent(getApplicationContext(), ApplicationStagesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        apiInterface = APIClient.getClient().create(APIInterface.class);

        savedJobsView = findViewById(R.id.savedJobList);
        savedJobsView.setLayoutManager(new LinearLayoutManager( this));

        getSavedJobs();

        EditText editSearch = findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageView searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(v -> {
            getSavedJobs();
        });

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

    private void getSavedJobs() {
        LoginModel obj = Common.getUserData(this);
        Dialog dialog = Common.progressDialog(this);
        dialog.show();
        JobListingRequest request = new JobListingRequest();
        request.emailId = obj.email_id;
        request.searchText = searchText;
        Call<ResponseBody> call = apiInterface.getSavedJobs(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    try {
                        Gson g = new Gson();
                        String json = response.body().string();
                        savedJobs = g.fromJson(json, new TypeToken<ArrayList<JobModel>>(){}.getType());
                        JobsAdapter jobsAdapter = new JobsAdapter(SavedJobsActivity.this, savedJobs, 2,(position, v) ->
                        {
                            Intent intent = new Intent(SavedJobsActivity.this, ApplicantViewJobDescription.class);
                            JobModel obj = savedJobs.get(position);
                            Gson gson = new Gson();
                            String jayson = gson.toJson(obj);
                            intent.putExtra("jobData",jayson);
                            startActivity(intent);
                        });
                        savedJobsView.setAdapter(jobsAdapter);
                    } catch (Exception e) {

                    }
                } else {
                    Common.print(SavedJobsActivity.this, "Failed to get job list");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.saved_jobs);
    }
}