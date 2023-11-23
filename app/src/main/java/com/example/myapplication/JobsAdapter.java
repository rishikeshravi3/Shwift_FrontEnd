package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private List<String> jobList;

    public JobsAdapter(List<String> jobs) {
        jobList = jobs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View jobView = inflater.inflate(R.layout.job_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(jobView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s = jobList.get(position);
        holder.txtRole.setText(s);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView companyLogo;
        TextView txtRole, txtCompanyName, txtLocation, txtSalary;
        public ViewHolder(View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.companyLogo);
            txtRole = itemView.findViewById(R.id.txtRole);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtSalary = itemView.findViewById(R.id.txtSalary);
        }
    }
}
