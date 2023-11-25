package com.example.myapplication.Profile;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Helper.Common;
import com.example.myapplication.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;

public class SkillActivity extends AppCompatActivity {
    private EditText editSkill;
    private FlexboxLayout chipContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);

        editSkill = findViewById(R.id.editSkill);
        chipContainer = findViewById(R.id.chipContainer);
        chipFromFetch();


        editSkill.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // Handle the "Enter" key press
                    addChip(editSkill.getText().toString());
                    editSkill.setText(""); // Clear the EditText
                    return true;
                }
                return false;
            }
        });
    }
private void chipFromFetch(){
    ProfileResponseModel obj = Common.getProfileData(this);
    if(obj!=null){
        String skillsString = obj.emp_skills;
        String[] skillsArray = skillsString.split(",");
        for (String skill : skillsArray) {
        Chip chip = new Chip(this);
        chip.setText(skill.trim());
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle chip removal
                chipContainer.removeView(chip);
            }
        });

        chipContainer.addView(chip);
    }
    }else{
        Toast.makeText(this, "Please enter a non-empty skill", Toast.LENGTH_SHORT).show();
    }
}
    private void addChip(String text) {
        if (!text.trim().isEmpty()) {
            Chip chip = new Chip(this);
            chip.setText(text);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle chip removal
                    chipContainer.removeView(chip);
                }
            });

            // Add the chip to the container
            chipContainer.addView(chip);
        } else {
            Toast.makeText(this, "Please enter a non-empty skill", Toast.LENGTH_SHORT).show();
        }
    }
}