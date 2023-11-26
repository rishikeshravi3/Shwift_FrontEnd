package com.example.myapplication.Applications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ApplicationStageListData;
import com.example.myapplication.JobListing.JobModel;
import com.example.myapplication.R;

import java.util.List;

public class ApplicationStageListAdapter extends RecyclerView.Adapter<ApplicationStageListAdapter.ViewHolder> {
    private List<JobApplicationModel> applicationList;
    Context context;
    private static ClickListener clickListener;

    public ApplicationStageListAdapter(Context ctx, List<JobApplicationModel> appList, ClickListener clickListener) {
        this.context = ctx;
        this.applicationList = appList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.application_stage_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobApplicationModel obj = applicationList.get(position);
        holder.logo.setImageResource(R.drawable.shwift_logo);
        holder.role.setText(obj.job_title);
        holder.company.setText(obj.recruiter_name);
        if (obj.application_status == 1) {
            holder.status.setText("Application Sent");
        } else if (obj.application_status == 2) {
            holder.status.setText("Application Accepted");
        } else {
            holder.status.setText("Application Rejected");
        }
        holder.status.setPadding(17,3,17,3);
        if(holder.status.getText().equals("Application Sent")){
           holder.status.setTextAppearance(R.style.application_stage_item_status_style_sent);
           holder.status.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.application_stage_item_sent_background));
        }
        else if(holder.status.getText().equals("Application Accepted")){
            holder.status.setTextAppearance(R.style.application_stage_item_status_style_accepted);
            holder.status.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.application_stage_item_accepted_background));
        }
        else if(holder.status.getText().equals("Application Rejected")){
            holder.status.setTextAppearance(R.style.application_stage_item_status_style_rejected);
            holder.status.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.application_stage_item_rejected_background));
        }

//        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(v.getContext(), ApplicationStageOnClickActivity.class);
//                startActivity(intent,v.getContext());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView logo;
        public TextView role;
        public TextView company;
        public TextView status;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView){
            super(itemView);
            this.logo = itemView.findViewById(R.id.application_stage_item_image);
            this.role = itemView.findViewById(R.id.application_stage_item_role);
            this.company = itemView.findViewById(R.id.application_stage_item_company);
            this.status = itemView.findViewById(R.id.application_stage_item_status);
            constraintLayout = itemView.findViewById(R.id.application_stages_recycler_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int position = getBindingAdapterPosition();
            if(position>=0){
                clickListener.onItemClick(position,v);
            }
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
