package com.example.myapplication.Helper;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.ProfileResponseModel;
import com.example.myapplication.R;
import com.google.gson.Gson;

public class Common {
    public static void print(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }
    public static void printShort(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static Dialog progressDialog(Context ctx) {
        Dialog dialog = new Dialog(ctx);
        View inflate = LayoutInflater.from(ctx).inflate(R.layout.progess_dialog, null);
        dialog.setContentView(inflate);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    public static LoginModel getUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString(Constants.KEY_USER_DATA, "");
        Gson gson = new Gson();
        LoginModel user = gson.fromJson(userData, LoginModel.class);
        return user;
    }

    public static ProfileResponseModel getProfileData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        String jsonStr = sharedPreferences.getString(Constants.KEY_PROFILE_DATA, "");

// Use Gson to convert the JSON string back to a ProfileResponseModel object
        Gson gson = new Gson();
        ProfileResponseModel profileData = gson.fromJson(jsonStr, ProfileResponseModel.class);
        return profileData;
    }
}
