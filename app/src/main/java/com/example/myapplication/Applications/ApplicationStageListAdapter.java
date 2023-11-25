package com.example.myapplication.Applications;

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
import com.example.myapplication.R;

public class ApplicationStageListAdapter extends RecyclerView.Adapter<ApplicationStageListAdapter.ViewHolder> {
    private ApplicationStageListData[] listdata;
    private static ClickListener clickListener;

    public ApplicationStageListAdapter(ApplicationStageListData[] listdata, ClickListener clickListener){
        this.listdata=listdata;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitem = layoutInflater.inflate(R.layout.application_stage_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(listitem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ApplicationStageListData listData=listdata[position];
        holder.logo.setImageResource(listdata[position].getLogoId());
        holder.role.setText(listdata[position].getRole());
        holder.company.setText(listdata[position].getCompany());
        holder.status.setText(listdata[position].getStatus());
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
        return listdata.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView logo;
        public TextView role;
        public TextView company;
        public TextView status;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView){
            super(itemView);
            this.logo=(ImageView)itemView.findViewById(R.id.application_stage_item_image);
            this.role=(TextView)itemView.findViewById(R.id.application_stage_item_role);
            this.company=(TextView)itemView.findViewById(R.id.application_stage_item_company);
            this.status=(TextView)itemView.findViewById(R.id.application_stage_item_status);
            constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.application_stages_recycler_view);
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
