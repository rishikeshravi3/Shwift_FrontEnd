package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.Constants;
import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.ProfileRequestModel;
import com.example.myapplication.Profile.ProfileResponseModel;
import com.example.myapplication.R;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employee_Detailspage extends AppCompatActivity {
    APIInterface apiInterface;
    private static final int REQUEST_PERMISSION_CODE = 123;
    UserApplicationModel obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detailspage);

//        TextView name = findViewById(R.id.textViewEmployeeName);
//        TextView currpositon = findViewById(R.id.textViewPosition);
//        TextView Education = findViewById(R.id.textViewEmployeeEducation);
//        TextView Skills = findViewById(R.id.textViewEmployeeSkills);
//        TextView Summary = findViewById(R.id.bodySummary);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        LoginModel objUser = Common.getUserData(this);
//        name.setText(obj.first_name + " " + obj.last_name);

        Button btnOpenResume = findViewById(R.id.btnOpenResume);
        btnOpenResume.setOnClickListener(v -> {
            if (obj.resume_url.isEmpty() == false) {
                downloadAndOpenPdf();
//                if (checkPermission()) {
//                    downloadAndOpenPdf();
//                } else {
//                    requestPermission();
//                }
            } else {
                Common.print(this,"Resume was not uploaded");
            }
        });

        Intent intent = getIntent();
        String res = intent.getStringExtra("EmployeeEmail");
        String appData = intent.getStringExtra("ApplicationData");
        Gson gson = new Gson();
        obj = gson.fromJson(appData, UserApplicationModel.class);

        RelativeLayout item = findViewById(R.id.containerView);
        View child = getLayoutInflater().inflate(R.layout.recent_applicants_list_item, null);

        MaterialCardView.LayoutParams jobListingLayoutParams = new MaterialCardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        child.setLayoutParams(jobListingLayoutParams);
        item.addView(child);

        ImageView photo = (ImageView) child.findViewById(R.id.recent_applicants_item_photo);
        TextView role = (TextView) child.findViewById(R.id.recent_applicants_item_txtRole);
        TextView name = (TextView) child.findViewById(R.id.recent_applicants_item_txtName);
        TextView txtEmail = (TextView) child.findViewById(R.id.txtEmail);
        TextView txtPhoneNum = (TextView) child.findViewById(R.id.txtPhoneNum);
        TextView availability = (TextView) child.findViewById(R.id.recent_applicants_item_txtAvailability);

        if (obj.employee_dp != null && obj.employee_dp.isEmpty() == false) {
            Glide.with(this).load(obj.employee_dp).into(photo);
        } else {
            photo.setImageResource(R.drawable.person);
        }
        role.setText(obj.job_title);
        name.setText(obj.first_name + " " + obj.last_name);
        txtEmail.setText(obj.email_id);
        txtPhoneNum.setText(obj.phone_num);
        if (obj.availability.isEmpty()) {
            availability.setText("Availability: " + getString(R.string.mon_tue_wed_thu_fri_sat_sun));
        } else {
            availability.setText("Availability: " + obj.availability);
        }

        getProfileDetails(res);

//        if (obj.resume_url.isEmpty() == false) {
//            if (checkPermission()) {
//                downloadAndOpenPdf();
//            } else {
//                requestPermission();
//            }
//        }


//        ProfileResponseModel object = Common.getProfileData(Employee_Detailspage.this);
//        if (object != null) {
//            currpositon.setText(object.curr_employment_status);
//            Education.setText(object.emp_education);
//            Summary.setText(object.emp_summary);
//            Skills.setText(object.emp_skills);
//
//        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_CODE
        );
    }

    private void downloadAndOpenPdf() {
        String pdfUrl = obj.resume_url;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, obj.job_id + ".pdf");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadAndOpenPdf();
                } else {
                    // Handle the case where the user denied the permission
                    // You may want to show a message or prompt the user to grant the permission
                }
                return;
            }
        }
    }

    private void getProfileDetails(String Email) {
        Dialog dialog = Common.progressDialog(this);
        dialog.show();
        ProfileRequestModel request = new ProfileRequestModel();
        request.emailId = Email;
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