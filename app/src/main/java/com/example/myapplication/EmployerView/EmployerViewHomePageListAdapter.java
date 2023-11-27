package com.example.myapplication.EmployerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.JobListing.JobModel;
import com.example.myapplication.JobListing.JobsAdapter;
import com.example.myapplication.R;

import java.util.List;

public class EmployerViewHomePageListAdapter extends RecyclerView.Adapter<EmployerViewHomePageListAdapter.ViewHolder> {
    private List<UserApplicationModel> applicationList;
    Context context;
    private JobsAdapter.ClickListener clickListener;

    public EmployerViewHomePageListAdapter(Context ctx, List<UserApplicationModel> apps, JobsAdapter.ClickListener cListener) {
        context = ctx;
        applicationList = apps;
        clickListener = cListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recent_applicants_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserApplicationModel obj = applicationList.get(position);
        if (obj.employee_dp != null && obj.employee_dp.isEmpty() == false) {
            Glide.with(context).load(obj.employee_dp).into(holder.photo);
        } else {
            holder.photo.setImageResource(R.drawable.person);
        }
        holder.role.setText(obj.job_title);
        holder.name.setText(obj.first_name + " " + obj.last_name);
        holder.txtEmail.setText(obj.email_id);
        holder.txtPhoneNum.setText(obj.phone_num);
        if (obj.availability.isEmpty()) {
            holder.availability.setText("Availability: " + context.getString(R.string.mon_tue_wed_thu_fri_sat_sun));
        } else {
            holder.availability.setText("Availability: " + obj.availability);
        }
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView photo;
        public TextView role;
        public TextView name;
        public TextView txtEmail;
        public TextView txtPhoneNum;
        public TextView availability;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.photo = (ImageView) itemView.findViewById(R.id.recent_applicants_item_photo);
            this.role = (TextView) itemView.findViewById(R.id.recent_applicants_item_txtRole);
            this.name = (TextView) itemView.findViewById(R.id.recent_applicants_item_txtName);
            this.txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            this.txtPhoneNum = (TextView) itemView.findViewById(R.id.txtPhoneNum);
            this.availability = (TextView) itemView.findViewById(R.id.recent_applicants_item_txtAvailability);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.employer_view_home_page_recycler_view);
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
