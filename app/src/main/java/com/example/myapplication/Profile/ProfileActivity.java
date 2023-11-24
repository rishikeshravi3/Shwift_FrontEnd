package com.example.myapplication.Profile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.JobListingActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.employer_view_home_page_bottom_Navigation);
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
}