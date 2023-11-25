package com.example.myapplication.APIHelper;

import com.example.myapplication.JobListing.JobListingRequest;
import com.example.myapplication.JobListing.SaveJobModel;
import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.ProfileRequestModel;
import com.example.myapplication.Profile.ProfileResponseModel;
import com.example.myapplication.Profile.UpdateProfileRequest;
import com.example.myapplication.Profile.UpdateProfileResponse;
import com.example.myapplication.SignUpModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("/shwift/fetchSpecificListing")
    Call<ResponseBody> getJobList(@Body JobListingRequest input);

    @POST("/shwift/signUp")
    Call<SignUpModel> createPost(@Body SignUpModel signUpModel);

    @GET("shwift/login/{userName}")
    Call<LoginModel> login(
            @Path("userName") String userName,
            @Query("password") String password
    );

    @POST("/shwift/saveJob")
    Call<ResponseBody> saveJob(@Body SaveJobModel saveJobModel);

    @POST("/shwift/deleteSavedJob")
    Call<ResponseBody> deleteSavedJob(@Body SaveJobModel saveJobModel);

    @POST("/shwift/fetchAllEmployeeInfo")
    Call<ProfileResponseModel> getProfileInfo(@Body ProfileRequestModel profileRequestModel);

    @POST("/shwift/updateEmployeeInfo")
    Call<UpdateProfileResponse> getUpdateInfo(@Body UpdateProfileRequest updateProfileRequest);
    @POST("/shwift/getSavedJobs")
    Call<ResponseBody> getSavedJobs(@Body JobListingRequest input);
}
