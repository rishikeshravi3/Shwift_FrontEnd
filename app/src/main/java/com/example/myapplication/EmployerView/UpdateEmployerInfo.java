package com.example.myapplication.EmployerView;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.UpdateProfileRequest;
import com.example.myapplication.Profile.UpdateProfileResponse;
import com.example.myapplication.Profile.UpdateProfileService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployerInfo {

        public interface UpdateEmployerCallback {
            void onUpdateSuccess();
            void onUpdateFailure();
        }
        public static void Service(Context context, UpdateProfileRequest updateProfileRequest, com.example.myapplication.Profile.UpdateProfileService.UpdateProfileCallback callback) {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

            LoginModel obj = Common.getUserData(context);
            Dialog dialog = Common.progressDialog(context);
            dialog.show();
            UpdateProfileRequest request = updateProfileRequest;
            String email = obj.email_id;
            request.emailId = email;
            Call<UpdateProfileResponse> call = apiInterface.getUpdateEmployerInfo(request);
            call.enqueue(new Callback<UpdateProfileResponse>() {
                @Override
                public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    if (response.isSuccessful()) {
                        try {
                            callback.onUpdateSuccess();
                            Toast.makeText(context, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            // Handle the exception, log or display an error message
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("error");
//                        Common.print(context, "Failed to update profile");
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

