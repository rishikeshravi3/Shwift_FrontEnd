package com.example.myapplication.Profile;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// ... (import statements)

public class UpdateProfileService {
    public interface UpdateProfileCallback {
        void onUpdateSuccess();
        void onUpdateFailure();
    }
    public static void Service(Context context, UpdateProfileRequest updateProfileRequest, UpdateProfileCallback callback) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        LoginModel obj = Common.getUserData(context);
        Dialog dialog = Common.progressDialog(context);
        dialog.show();
        UpdateProfileRequest request = updateProfileRequest;

        request.emailId = "va_thakur532";
        Call<UpdateProfileResponse> call = apiInterface.getUpdateInfo(request);
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    try {
                        // Uncomment and modify the code below if needed
                        // UpdateProfileResponse json = response.body();
                        // Gson gson = new Gson();
                        // String jsonStr = gson.toJson(json);
                        // SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                        // SharedPreferences.Editor editor = sharedPreferences.edit();
                        // editor.putString(Constants.KEY_PROFILE_DATA, jsonStr);
                        // editor.apply();
                        // recentJobs = g.fromJson(json, new TypeToken<ArrayList<JobModel>>(){}.getType());
                        // JobsAdapter jobsAdapter = new JobsAdapter(JobListingActivity.this, recentJobs);
                        // recentJobsView.setAdapter(jobsAdapter);
                        // recommendedJobsView.setAdapter(jobsAdapter);
                        callback.onUpdateSuccess();
                        Toast.makeText(context, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        // Handle the exception, log or display an error message
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("error");
                    Common.print(context, "Failed to get Updated List");
                    callback.onUpdateFailure();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
}

