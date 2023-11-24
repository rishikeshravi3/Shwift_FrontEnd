package com.example.myapplication;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobListingActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listing);
        apiInterface = APIClient.getClient().create(APIInterface.class);

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
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });

        Call<ResponseBody> call = apiInterface.getJobList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s =  response.body().string();
                    Gson g = new Gson();
                    List<JobModel> ss = g.fromJson(s, new TypeToken<List<JobModel>>(){}.getType());
                    JobModel p = g.fromJson(s, JobModel.class);
                    System.out.println(s);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

        ArrayList<String> test = new ArrayList<>();
        test.add("Test 1");
        test.add("Test 2");
        test.add("Test 3");
        test.add("Test 4");
        test.add("Test 5");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recommendationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        JobsAdapter adapter = new JobsAdapter(test);
        recyclerView.setAdapter(adapter);

        RecyclerView recent = findViewById(R.id.recentJobList);
        recent.setLayoutManager(new LinearLayoutManager( this));
        recent.setAdapter(adapter);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(JobListingActivity.this, "Back?", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}