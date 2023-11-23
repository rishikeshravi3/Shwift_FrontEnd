package com.example.myapplication.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ProfileOptionsAdapter extends RecyclerView.Adapter<ProfileOptionsAdapter.ViewHolder> {
    private List<String> profileOptionsList;

    public ProfileOptionsAdapter(List<String> options) {
        profileOptionsList = options;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View jobView = inflater.inflate(R.layout.profile_option_item, parent, false);
        ProfileOptionsAdapter.ViewHolder viewHolder = new ProfileOptionsAdapter.ViewHolder(jobView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s = profileOptionsList.get(position);
        holder.txtOptionTitle.setText(s);
    }

    @Override
    public int getItemCount() {
        return profileOptionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView optionIcon;
        TextView txtOptionTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            optionIcon = itemView.findViewById(R.id.optionIcon);
            txtOptionTitle = itemView.findViewById(R.id.txtOptionTitle);
        }
    }
}
