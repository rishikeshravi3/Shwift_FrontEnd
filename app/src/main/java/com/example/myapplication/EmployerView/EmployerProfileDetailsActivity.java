package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.ApplicantViewJobDescription;
import com.example.myapplication.EmployerView.ProfileActivity_employer;
import com.example.myapplication.EmployerView.UpdateEmployerInfo;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.UploadImageRequest;
import com.example.myapplication.JobListing.JobListingActivity;
import com.example.myapplication.JobListing.JobModel;
import com.example.myapplication.JobListing.JobsAdapter;
import com.example.myapplication.LoginModel;
import com.example.myapplication.Profile.UpdateProfileRequest;
import com.example.myapplication.Profile.UpdateProfileService;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerProfileDetailsActivity extends AppCompatActivity {
    private ImageView imageView;

    private static final int PICK_IMAGE = 1;

    String imageString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile_details);
        imageView = findViewById(R.id.Employer_userDp);
        EditText FirstName = findViewById(R.id.Employer_FirstName_profile);
        LoginModel obj = Common.getUserData(this);
        FirstName.setText(obj.first_name);
        EditText LastName = findViewById(R.id.Employer_LastName_profile);
        LastName.setText(obj.last_name);
        Button save = findViewById(R.id.Employer_profileDetails_savebtn);
        ProfileResponseEmployer profileData = Common.getProfileDataEmployer(this);

        if (profileData != null) {
            Glide.with(this).load(profileData.employer_dp).into(imageView);
        }

        save.setOnClickListener(v -> {
            if (imageString != null && imageString.isEmpty() == false) {
                UpdateProfileRequest req = new UpdateProfileRequest();
                req.col_name="employer_dp";
                req.value = imageString;
                UpdateEmployerInfo.Service(this, req, new UpdateProfileService.UpdateProfileCallback() {
                    @Override
                    public void onUpdateSuccess() {
                    }
                    @Override
                    public void onUpdateFailure() {
                    }
                });
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image picker when the button is clicked
                openImagePicker();
            }
        });

    }

    private void openImagePicker() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Bitmap selectedBitmap = getBitmapFromUri(selectedImageUri);
            // Create a circular bitmap
//            Bitmap circularBitmap = getCircularBitmap(selectedBitmap);

            imageView.setImageBitmap(selectedBitmap);
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            UploadImageRequest imageRequest = new UploadImageRequest();
            imageRequest.base64Image = Common.drawableToBase64(imageView.getDrawable());
            if (imageRequest.base64Image != null) {
                Dialog dialog = Common.progressDialog(this);
                dialog.show();
                Call<ResponseBody> call = apiInterface.uploadImage(imageRequest);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        if (response.isSuccessful()) {
                            try {
                                imageString = response.body().string();
                            } catch (Exception e) {

                            }
                        } else {
                            Common.print(EmployerProfileDetailsActivity.this, "Failed to upload image");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            }

//            Toast.makeText(this, "Profile Image Updated", Toast.LENGTH_SHORT).show();
        }
    }


    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // Use ContentResolver to get a bitmap from the URI
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        // Create a circular bitmap using the original bitmap
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}