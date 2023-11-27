package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Applications.ApplicationStageListAdapter;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.Constants;
import com.example.myapplication.JobListing.JobModel;
import com.example.myapplication.JobListing.SaveJobModel;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private List<JobModel> jobList;
    Context context;
    int activityType; // 1 - JobListing, 2 - SavedJobs
    private static ClickListener clickListener;

    public JobsAdapter(Context ctx, List<JobModel> jobs, int actType, ClickListener clickListener)
    {
        context = ctx;
        jobList = jobs;
        activityType = actType;
        this.clickListener=clickListener;
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
        JobModel obj = jobList.get(position);
        holder.txtRole.setText(obj.job_title);
        holder.txtCompanyName.setText(obj.recruiter_name);
        holder.txtLocation.setText(obj.job_location);
        holder.txtSalary.setText(obj.pay_scale);

        updateSavedIcon(holder, obj.job_saved);

        if (obj.position_type == 1) {
            holder.txtJobAttr1.setText(context.getResources().getText(R.string.full_time));
        } else if (obj.position_type == 2) {
            holder.txtJobAttr1.setText(context.getResources().getText(R.string.part_time));
        } else {
            holder.txtJobAttr1.setText(context.getResources().getText(R.string.temp));
        }

        if (obj.position_onsite == 1) {
            holder.txtJobAttr1.setText(context.getResources().getText(R.string.on_site));
        }  else {
            holder.txtJobAttr1.setText(context.getResources().getText(R.string.remote));
        }

        holder.bookmark.setOnClickListener(v -> {
            JobModel jobObj = jobList.get(position);
            if (jobObj.job_saved) {
                if (activityType == 1) {
                    deleteJob(holder, jobList.get(position));
                } else {
                    deleteJobInSavedJobs(holder, jobList.get(position));
                }
            } else {
                saveJob(holder, jobList.get(position));
            }
        });
    }

    private void deleteJobInSavedJobs(ViewHolder holder, JobModel jobObj) {
        LoginModel user = Common.getUserData(context);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Dialog dialog = Common.progressDialog(context);
        dialog.show();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            SaveJobModel saveJobModel = new SaveJobModel();
            saveJobModel.jobId = jobObj.job_id;
            saveJobModel.emailId = user.email_id;
            Call<ResponseBody> call = apiInterface.deleteSavedJob(saveJobModel);
            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        });

        try {
            String result = future.get();
            if (result != null) {
                int pos = jobList.indexOf(jobObj);
                jobList.remove(jobObj);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, jobList.size());
            } else {
                Common.print(context, "Job delete failed");
            }
        } catch (Exception e) {

        } finally {
            executor.shutdown();
            dialog.dismiss();
        }
    }

    private void deleteJob(ViewHolder holder, JobModel jobObj) {
        LoginModel user = Common.getUserData(context);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Dialog dialog = Common.progressDialog(context);
        dialog.show();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            SaveJobModel saveJobModel = new SaveJobModel();
            saveJobModel.jobId = jobObj.job_id;
            saveJobModel.emailId = user.email_id;
            Call<ResponseBody> call = apiInterface.deleteSavedJob(saveJobModel);
            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        });

        try {
            String result = future.get();
            if (result != null) {
                jobObj.job_saved = false;
                updateSavedIcon(holder, false);
                Common.print(context, "Job deleted successfully");
            } else {
                Common.print(context, "Job delete failed");
            }
        } catch (Exception e) {

        } finally {
            executor.shutdown();
            dialog.dismiss();
        }
    }

    private void updateSavedIcon(ViewHolder holder, boolean jobSaved) {
        if (jobSaved) {
            holder.bookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_fill));
        } else {
            holder.bookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark));
        }
    }

    private void saveJob(ViewHolder holder, JobModel jobObj) {
        LoginModel user = Common.getUserData(context);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Dialog dialog = Common.progressDialog(context);
        dialog.show();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            SaveJobModel saveJobModel = new SaveJobModel();
            saveJobModel.jobId = jobObj.job_id;
            saveJobModel.emailId = user.email_id;
            Call<ResponseBody> call = apiInterface.saveJob(saveJobModel);
            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        });

        try {
            String result = future.get();
            if (result != null) {
                jobObj.job_saved = true;
                updateSavedIcon(holder, true);
                Common.print(context, "Job saved successfully");
            } else {
                Common.print(context, "Job save failed");
            }
        } catch (Exception e) {

        } finally {
            executor.shutdown();
            dialog.dismiss();
        }
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView companyLogo, bookmark;
        TextView txtRole, txtCompanyName, txtLocation, txtSalary, txtJobAttr1, txtJobAttr2;
        public ViewHolder(View itemView) {
            super(itemView);
            companyLogo = itemView.findViewById(R.id.companyLogo);
            txtRole = itemView.findViewById(R.id.txtRole);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            txtJobAttr1 = itemView.findViewById(R.id.txtJobAttr1);
            txtJobAttr2 = itemView.findViewById(R.id.txtJobAttr2);
            bookmark = itemView.findViewById(R.id.bookmark);
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
