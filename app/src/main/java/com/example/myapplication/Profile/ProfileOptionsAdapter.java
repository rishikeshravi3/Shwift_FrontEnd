package com.example.myapplication.Profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ProfileOptionsAdapter extends RecyclerView.Adapter<ProfileOptionsAdapter.ViewHolder> {
    private List<ProfileOptionModel> profileOptionsList;
    Context context;

    public ProfileOptionsAdapter(Context ctx, List<ProfileOptionModel> options) {
        context = ctx;
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
        ProfileOptionModel obj = profileOptionsList.get(position);
        holder.txtOptionTitle.setText(obj.optionTitle);
        holder.optionIcon.setImageDrawable(getDrawableIcon(obj.optionType));
        holder.openPage.setOnClickListener(v -> {
            int optionType = profileOptionsList.get(position).optionType;
            ProfileOptionType option = ProfileOptionType.values()[optionType];
            switch (option) {
                case CONTACT_INFO:
                    context.startActivity(new Intent(context, ContactInformationActivity.class));
                    break;
                case SUMMARY:
                    context.startActivity(new Intent(context, ProfileSummaryActivity.class));
                    break;
                case WORK_EXP:
                    context.startActivity(new Intent(context, WorkExperienceActivity.class));
                    break;
                case EDUCATION:
                    context.startActivity(new Intent(context, EducationActivity.class));
                    break;
                case PROJECTS:
                    context.startActivity(new Intent(context, ProjectsActivity.class));
                    break;
                case SKILLS:
                    context.startActivity(new Intent(context, SkillActivity.class));
                    break;
                default:
                    break;
            }
        });
    }

    private Drawable getDrawableIcon(int optionType) {
        ProfileOptionType option = ProfileOptionType.values()[optionType];
        Drawable drawable;
        switch (option) {
            case CONTACT_INFO:
                drawable = context.getDrawable(R.drawable.profile_icon);
                drawable.setTint(ContextCompat.getColor(context, R.color.purple));
                return drawable;
            case SUMMARY:
                drawable = context.getDrawable(R.drawable.summary_icon);
                drawable.setTint(ContextCompat.getColor(context, R.color.purple));
                return drawable;
            case WORK_EXP:
                drawable = context.getDrawable(R.drawable.applications_icon);
                drawable.setTint(ContextCompat.getColor(context, R.color.purple));
                return drawable;
            case EDUCATION:
                drawable = context.getDrawable(R.drawable.education_icon);
                drawable.setTint(ContextCompat.getColor(context, R.color.purple));
                return drawable;
            case PROJECTS:
                drawable = context.getDrawable(R.drawable.projects_icon);
                drawable.setTint(ContextCompat.getColor(context, R.color.purple));
                return drawable;
            case SKILLS:
                drawable = context.getDrawable(R.drawable.skills_icon);
                drawable.setTint(ContextCompat.getColor(context, R.color.purple));
                return drawable;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return profileOptionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView optionIcon, openPage;
        TextView txtOptionTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            optionIcon = itemView.findViewById(R.id.optionIcon);
            openPage = itemView.findViewById(R.id.openPage);
            txtOptionTitle = itemView.findViewById(R.id.txtOptionTitle);
        }
    }
}
