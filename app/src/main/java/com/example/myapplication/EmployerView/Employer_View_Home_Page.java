package com.example.myapplication.EmployerView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Applications.ApplicationStagesActivity;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.JobListing.JobListingActivity;
import com.example.myapplication.JobListing.JobListingRequest;
import com.example.myapplication.JobListing.JobModel;
import com.example.myapplication.JobListing.JobsAdapter;
import com.example.myapplication.JobListing.SavedJobsActivity;
import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.ProfileActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employer_View_Home_Page extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    APIInterface apiInterface;
    ArrayList<UserApplicationModel> applicationList = new ArrayList<>();
    RecyclerView applicationListView;
    TextView txtNoData;
    ImageView userDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view_home_page);

        TextView txtGreeting = findViewById(R.id.employer_view_home_page_txt_greeting);
        TextView txtName = findViewById(R.id.employer_view_home_page_txt_name);
        userDp = findViewById(R.id.employer_view_home_page_user_logo);

        LoginModel obj = Common.getUserData(this);
        if (obj.user_dp.isEmpty() == false) {
            Glide.with(this).load(obj.user_dp).into(userDp);
        }
        txtName.setText(obj.first_name + " " + obj.last_name);

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour > 6 && hour < 12) {
            txtGreeting.setText(getResources().getText(R.string.good_morning));
        } else if (hour >= 12 && hour <= 18) {
            txtGreeting.setText(getResources().getText(R.string.good_afternoon));
        } else {
            txtGreeting.setText(getResources().getText(R.string.good_evening));
        }

        apiInterface = APIClient.getClient().create(APIInterface.class);
        ImageView imageView = findViewById(R.id.employer_view_home_page_user_logo);

        bottomNavigationView = findViewById(R.id.employer_view_home_page_bottom_Navigation);
        bottomNavigationView.setSelectedItemId(R.id.home_employer);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_employer) {
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
                startActivity(new Intent(getApplicationContext(), ProfileActivity_employer.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        txtNoData = findViewById(R.id.txtNoData);
        applicationListView = findViewById(R.id.employer_view_home_page_recycler_view);
        applicationListView.setLayoutManager(new LinearLayoutManager(this));

        getApplications();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Employer_View_Home_Page.this);
                builder.setMessage(R.string.exit_message);
                builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                    finish();
                    System.exit(0);
                });
                builder.setNegativeButton(R.string.no, (dialog, which) -> {

                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
        Call<ResponseBody> call = apiInterface.fetchAllApplications(request);
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
                        applicationList = g.fromJson(json, new TypeToken<ArrayList<UserApplicationModel>>(){}.getType());
                        EmployerViewHomePageListAdapter adapter = new EmployerViewHomePageListAdapter(Employer_View_Home_Page.this, applicationList , (position, v) -> {
                            UserApplicationModel obj = applicationList.get(position);
                                Intent intent = new Intent(Employer_View_Home_Page.this, Employee_Detailspage.class);
                                intent.putExtra("EmployeeEmail",obj.email_id);
                                startActivity(intent);
                                // start activity
                        });
                        applicationListView.setAdapter(adapter);
                        if (applicationList.size() == 0) {
                            applicationListView.setVisibility(View.GONE);
                            txtNoData.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        Log.i("Employer Home", e.getMessage());
                    }
                } else {
                    Common.print(Employer_View_Home_Page.this, "Failed to get job list");
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

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.home);
        LoginModel obj = Common.getUserData(this);
        if (obj.user_dp.isEmpty() == false) {
            Glide.with(this).load(obj.user_dp).into(userDp);
        }
    }
}