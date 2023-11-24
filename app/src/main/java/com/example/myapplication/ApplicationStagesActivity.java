package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class ApplicationStagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_stages);
//        test data
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
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}