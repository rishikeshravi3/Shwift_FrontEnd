package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.ApplicationStageListAdapter;
import com.example.myapplication.ApplicationStageListData;
import com.example.myapplication.R;

public class Employer_View_Home_Page extends AppCompatActivity {

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
    }
}