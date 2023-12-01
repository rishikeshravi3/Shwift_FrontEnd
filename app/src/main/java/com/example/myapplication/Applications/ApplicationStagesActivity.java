package com.example.myapplication.Applications;

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
import com.example.myapplication.ApplicationStageOnClickActivity;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.JobListing.JobListingActivity;
import com.example.myapplication.JobListing.JobListingRequest;
import com.example.myapplication.JobListing.SavedJobsActivity;
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

public class ApplicationStagesActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    APIInterface apiInterface;
    RecyclerView applicationsView;
    ArrayList<JobApplicationModel> applicationList = new ArrayList<>();
    String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_stages);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), JobListingActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.saved_jobs) {
                startActivity(new Intent(getApplicationContext(), SavedJobsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.applications) {
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

        applicationsView = findViewById(R.id.application_stages_recycler_view);
        applicationsView.setHasFixedSize(true);
        applicationsView.setLayoutManager(new LinearLayoutManager(this));
        getApplications();

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

        ImageView searchBtn = findViewById(R.id.searchBtn)     ;
        searchBtn.setOnClickListener(v -> {
            getApplications();
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

    private void getApplications() {
        LoginModel obj = Common.getUserData(this);
        Dialog dialog = Common.progressDialog(this);
        dialog.show();
        JobListingRequest request = new JobListingRequest();
        request.emailId = obj.email_id;
        request.searchText = searchText;
        Call<ResponseBody> call = apiInterface.getApplicationsByEmail(request);
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
                        applicationList = g.fromJson(json, new TypeToken<ArrayList<JobApplicationModel>>(){}.getType());
                        ApplicationStageListAdapter adapter = new ApplicationStageListAdapter(ApplicationStagesActivity.this, applicationList, (ApplicationStageListAdapter.ClickListener) (position, v) -> {
                            Intent intent = new Intent(ApplicationStagesActivity.this, ApplicationStageOnClickActivity.class);
                            JobApplicationModel obj = applicationList.get(position);
                            intent.putExtra("role", obj.job_title);
                            intent.putExtra("company", obj.org_name);
                            if (obj.application_status == 1) {
                                intent.putExtra("status", "Application Sent");
                            } else if (obj.application_status == 2) {
                                intent.putExtra("status", "Application Accepted");
                            } else {
                                intent.putExtra("status", "Application Rejected");
                            }
                            intent.putExtra("logoId", R.drawable.shwift_logo);
                            intent.putExtra("location", obj.job_location);
                            intent.putExtra("salary", obj.pay_scale);
                            intent.putExtra("positionType", obj.position_type);
                            intent.putExtra("onSite", obj.position_onsite);
                            intent.putExtra("logo", obj.employer_dp);

                            startActivity(intent);
                        });
                        applicationsView.setAdapter(adapter);
                    } catch (Exception e) {

                    }
                } else {
                    Common.print(ApplicationStagesActivity.this, "Failed to get applications list");
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
        bottomNavigationView.setSelectedItemId(R.id.applications);
    }
}