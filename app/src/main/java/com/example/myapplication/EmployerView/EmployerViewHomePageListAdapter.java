package com.example.myapplication.EmployerView;

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

import com.example.myapplication.R;

public class EmployerViewHomePageListAdapter extends RecyclerView.Adapter<EmployerViewHomePageListAdapter.ViewHolder> {
    private EmployerViewHomePageListData[] listdata;

    public EmployerViewHomePageListAdapter(EmployerViewHomePageListData[] listdata){
        this.listdata=listdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listitem = layoutInflater.inflate(R.layout.recent_applicants_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(listitem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final EmployerViewHomePageListData listData=listdata[position];
        holder.photo.setImageResource(listdata[position].getPhotoId());
        holder.role.setText(listdata[position].getRole());
        holder.name.setText(listdata[position].get_Name());
        holder.location.setText(listdata[position].getLocation());
        holder.availability.setText(listdata[position].getAvailability());
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;
        public TextView role;
        public TextView name;
        public TextView location;
        public TextView availability;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView){
            super(itemView);
            this.photo=(ImageView)itemView.findViewById(R.id.recent_applicants_item_photo);
            this.role=(TextView)itemView.findViewById(R.id.recent_applicants_item_txtRole);
            this.name=(TextView)itemView.findViewById(R.id.recent_applicants_item_txtName);
            this.location=(TextView)itemView.findViewById(R.id.recent_applicants_item_txtLocation);
            this.availability=(TextView)itemView.findViewById(R.id.recent_applicants_item_txtAvailability);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.employer_view_home_page_recycler_view);
        }
    }
}
