package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Applications.ApplicationStagesActivity;
import com.example.myapplication.JobListing.SavedJobsActivity;
import com.example.myapplication.Profile.ProfileActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Employer_View_Home_Page extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view_home_page);
        EmployerViewHomePageListData[] listData=new EmployerViewHomePageListData[]{
                new EmployerViewHomePageListData("Project Manager","Shaurya Guliani","Delhi, India","5 days a week","Full Time", "Remote", R.drawable.email_icon),
                new EmployerViewHomePageListData("Project Manager","Shaurya Guliani","Delhi, India","5 days a week","Full Time", "Remote", R.drawable.email_icon),
                new EmployerViewHomePageListData("Project Manager","Shaurya Guliani","Delhi, India","5 days a week","Full Time", "Remote", R.drawable.email_icon),
                new EmployerViewHomePageListData("Project Manager","Shaurya Guliani","Delhi, India","5 days a week","Full Time", "Remote", R.drawable.email_icon),
                new EmployerViewHomePageListData("Project Manager","Shaurya Guliani","Delhi, India","5 days a week","Full Time", "Remote", R.drawable.email_icon),
                new EmployerViewHomePageListData("Project Manager","Shaurya Guliani","Delhi, India","5 days a week","Full Time", "Remote", R.drawable.email_icon)
        };
//        test data end

        RecyclerView recyclerView=findViewById(R.id.employer_view_home_page_recycler_view);
        EmployerViewHomePageListAdapter adapter=new EmployerViewHomePageListAdapter(listData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
    }
}