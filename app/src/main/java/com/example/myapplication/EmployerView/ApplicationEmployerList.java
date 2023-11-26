package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Profile.ProfileActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ApplicationEmployerList extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_employer_list);

        bottomNavigationView = findViewById(R.id.employer_view_home_page_bottom_Navigation);
        bottomNavigationView.setSelectedItemId(R.id.myListing_employer);
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
                return true;

            } else if (itemId == R.id.profile_employer) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }
}