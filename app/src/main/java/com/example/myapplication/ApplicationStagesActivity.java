package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ApplicationStagesActivity extends AppCompatActivity {
    //Dummy data
    ArrayList<String> jobType=new ArrayList<>();
    String salary="$8,000 - $20,000 / month";
    String location="New York, United Sates";
    //Dummy Data End



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_stages);
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

    }
}