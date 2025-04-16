package com.example.myapplication;



import com.google.android.material.chip.Chip;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
// Replace with your actual package name
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
 public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

        private List<FilterItem> filterItems;

        public FilterAdapter(List<FilterItem> filterItems) {
            this.filterItems = filterItems;
        }

        @NonNull
        @Override
        public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);
            return new FilterViewHolder(view);

        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
            FilterItem filterItem = filterItems.get(position);
            holder.textViewTitle.setText(filterItem.getTitle());
            holder.textViewTitle.setTextSize(30);


            // Customize this part to populate each filter item with its specific options
            // For example, you can add a Spinner, SeekBar, CheckBox, etc.
            // based on the type of filter (location & salary, work type, etc.).
            Log.i("HAM",filterItem.getTitle());

            holder.textViewTitle.setOnCheckedChangeListener((buttonView, isChecked) ->{
                if(isChecked){
                    holder.textViewTitle.setCloseIconResource(android.R.drawable.arrow_up_float);
                }else{
                    holder.textViewTitle.setCloseIconResource(android.R.drawable.arrow_down_float);
                }
            });

            holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                // Handle the selected radio button for the specific item
                if(checkedId ==R.id.radioButton2){
                    return;
                }else if(checkedId ==R.id.radioButton){
                    return;
                }
                notifyDataSetChanged();

            });

            if(filterItem.getTitle()=="Work Type") {
                holder.textViewTitle.setOnClickListener(v -> {
                    if (holder.expandableLayout1.getVisibility() == View.VISIBLE) {
                        holder.expandableLayout1.setVisibility(View.GONE);
                    } else {
                        holder.expandableLayout1.setVisibility(View.VISIBLE);
                    }
                });
            }else if(filterItem.getTitle()=="Location & Salary") {
                holder.textViewTitle.setOnClickListener(v -> {
                    if (holder.expandableLayout2.getVisibility() == View.VISIBLE) {
                        holder.expandableLayout2.setVisibility(View.GONE);
                    } else {
                        holder.expandableLayout2.setVisibility(View.VISIBLE);
                    }
                });
            }
            else{
                holder.textViewTitle.setOnClickListener(v -> {
                    if (holder.expandableLayout.getVisibility() == View.VISIBLE) {
                        holder.expandableLayout.setVisibility(View.GONE);
                    } else {
                        holder.expandableLayout.setVisibility(View.VISIBLE);
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return filterItems.size();
        }

        static class FilterViewHolder extends RecyclerView.ViewHolder {
            Chip textViewTitle;

            LinearLayout expandableLayout;
            LinearLayout  expandableLayout1;
            LinearLayout expandableLayout2;
            RadioGroup radioGroup;
            RadioButton RadioButton2,RadioButton;


            public FilterViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle =     itemView.findViewById(R.id.textViewFilterTitle);
                expandableLayout = itemView.findViewById(R.id.expandableLayout);
                expandableLayout1 = itemView.findViewById(R.id.expandableLayout1);
                expandableLayout2 = itemView.findViewById(R.id.expandableLayout2);
                radioGroup = itemView.findViewById(R.id.radioGroup);
                RadioButton = itemView.findViewById(R.id.radioButton);
                RadioButton2 = itemView.findViewById(R.id.radioButton2);




            }
        }

    }

