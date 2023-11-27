package com.example.myapplication.JobListing;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Applications.ApplicationStagesActivity;
import com.example.myapplication.Helper.Common;
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

public class JobListingActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    APIInterface apiInterface;
    ArrayList<JobModel> recentJobs = new ArrayList<>();
    ArrayList<JobModel> recommendedJobs = new ArrayList<>();
    RecyclerView recommendedJobsView, recentJobsView;
    LinearLayout recommendationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);
        TextView txtGreeting = findViewById(R.id.txtGreeting);
        TextView txtName = findViewById(R.id.txtName);

        LoginModel obj = Common.getUserData(this);
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
        ImageView imageView = findViewById(R.id.userDp);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.saved_jobs) {
                startActivity(new Intent(getApplicationContext(), SavedJobsActivity.class));
                overridePendingTransition(0, 0);
                finish();
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

        recommendationLayout = findViewById(R.id.recommendationLayout);
        recommendedJobsView = findViewById(R.id.recommendationList);
        recommendedJobsView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        recentJobsView = findViewById(R.id.recentJobList);
        recentJobsView.setLayoutManager(new LinearLayoutManager(this));

        getRecommendedJobs();
        getJobList();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(JobListingActivity.this);
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

    private void getRecommendedJobs() {
        LoginModel obj = Common.getUserData(this);
        Dialog dialog = Common.progressDialog(this);
        dialog.show();
        JobListingRequest request = new JobListingRequest();
        request.emailId = obj.email_id;
        Call<ResponseBody> call = apiInterface.getRecommendedJobs(request);
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
                        recommendedJobs = g.fromJson(json, new TypeToken<ArrayList<JobModel>>(){}.getType());
                        JobsAdapter jobsAdapter = new JobsAdapter(JobListingActivity.this, recommendedJobs, 3);
                        recommendedJobsView.setAdapter(jobsAdapter);
                        if (recommendedJobs.size() == 0) {
                            recommendationLayout.setVisibility(View.GONE);
                            recommendedJobsView.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                    }
                } else {
                    Common.print(JobListingActivity.this, "Failed to get recommended job list");
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

    private void getJobList() {
        LoginModel obj = Common.getUserData(this);
        Dialog dialog = Common.progressDialog(this);
        dialog.show();
        JobListingRequest request = new JobListingRequest();
        request.emailId = obj.email_id;
        Call<ResponseBody> call = apiInterface.getJobList(request);
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
                        recentJobs = g.fromJson(json, new TypeToken<ArrayList<JobModel>>(){}.getType());
                        JobsAdapter jobsAdapter = new JobsAdapter(JobListingActivity.this, recentJobs, 1);
                        recentJobsView.setAdapter(jobsAdapter);
                    } catch (Exception e) {

                    }
                } else {
                    Common.print(JobListingActivity.this, "Failed to get job list");
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
    }

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        // Create a circular bitmap using the original bitmap
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}