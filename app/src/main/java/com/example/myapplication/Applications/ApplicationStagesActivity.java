package com.example.myapplication.Applications;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.ApplicationStageListData;
import com.example.myapplication.ApplicationStageOnClickActivity;
import com.example.myapplication.JobListing.JobListingActivity;
import com.example.myapplication.JobListing.SavedJobsActivity;
import com.example.myapplication.Profile.ProfileActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ApplicationStagesActivity extends AppCompatActivity {
    //Dummy data
    ArrayList<String> jobType=new ArrayList<>();
    String salary="$8,000 - $20,000 / month";
    String location="New York, United Sates";
    //Dummy Data End

    BottomNavigationView bottomNavigationView;
    APIInterface apiInterface;

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

//        test data
        jobType.add("Remote");
        jobType.add("Full-Time");
        ApplicationStageListData[] listData=new ApplicationStageListData[]{
                new ApplicationStageListData("Project Manager","Amazon","Application Sent",R.drawable.email_icon),
                new ApplicationStageListData("Software Developer", "Google", "Application Accepted",R.drawable.shwift_logo),
                new ApplicationStageListData("Software Tester", "Stack Blocks","Application Rejected",R.drawable.tie_logo),
                new ApplicationStageListData("Project Manager","Amazon","Application Sent",R.drawable.email_icon),
                new ApplicationStageListData("Software Developer", "Google", "Application Accepted",R.drawable.shwift_logo),
                new ApplicationStageListData("Software Tester", "Stack Blocks","Application Rejected",R.drawable.tie_logo),
                new ApplicationStageListData("Project Manager","Amazon","Application Sent",R.drawable.email_icon),
                new ApplicationStageListData("Software Developer", "Google", "Application Accepted",R.drawable.shwift_logo),
                new ApplicationStageListData("Software Tester", "Stack Blocks","Application Rejected",R.drawable.tie_logo)
        };
//        test data end

        RecyclerView recyclerView=findViewById(R.id.application_stages_recycler_view);
        ApplicationStageListAdapter adapter=new ApplicationStageListAdapter(listData, new ApplicationStageListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent=new Intent(ApplicationStagesActivity.this, ApplicationStageOnClickActivity.class);
                intent.putExtra("role",listData[position].getRole());
                intent.putExtra("company",listData[position].getCompany());
                intent.putExtra("status",listData[position].getStatus());
                intent.putExtra("logoId",listData[position].getLogoId());
                intent.putExtra("location",location);
                intent.putExtra("salary",salary);
                intent.putStringArrayListExtra("jobType",jobType);

                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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

    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.applications);
    }
}