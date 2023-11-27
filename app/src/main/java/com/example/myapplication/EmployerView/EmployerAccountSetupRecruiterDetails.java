package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.SignUpModel;
import com.example.myapplication.Sign_up_screen;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class EmployerAccountSetupRecruiterDetails extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private ImageButton imageView;
    APIInterface apiInterface;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_account_setup_recruiter_details);
        imageView = findViewById(R.id.employer_account_setup_recruiter_details_image);
        Button continueButton = findViewById(R.id.activity_employer_account_setup_recruiter_details_button);
        TextInputEditText firstNameEditText = findViewById(R.id.activity_employer_account_setup_recruiter_details_first_name);
        TextInputEditText lastNameEditText = findViewById(R.id.activity_employer_account_setup_recruiter_details_last_name);
        TextInputEditText emailEditText = findViewById(R.id.activity_employer_account_setup_recruiter_details_organization_email);
        TextInputEditText phoneEditText = findViewById(R.id.activity_employer_account_setup_recruiter_phone);
        TextInputEditText linkedinEditText = findViewById(R.id.activity_employer_account_setup_recruiter_details_linkedin_url);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image picker when the button is clicked
                openImagePicker();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String linkedinUrl = linkedinEditText.getText().toString().trim();

                // Perform validation
                if (firstName.isEmpty()) {
                    firstNameEditText.setError("First Name is required");
                    return;
                }

                if (lastName.isEmpty()) {
                    lastNameEditText.setError("Last Name is required");
                    return;
                }

                if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    return;
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Invalid email address");
                    return;
                }

                if (phone.isEmpty()) {
                    phoneEditText.setError("Phone number is required");
                    return;
                }

                // You can perform additional validation for phone number format if needed

                // If all validations pass, you can proceed to the next step
                // (Start the next activity or perform any other action)
                // Example:
                Intent intent = getIntent();
                String pswd = intent.getStringExtra("PasswordKey");
                String orgName = intent.getStringExtra("OrgName");
                String orgDesc = intent. getStringExtra("orgDesc");
                System.out.println(pswd);
                showPopup(v);
                String JobType = "employer";
                postData(firstName,JobType,lastName,email,pswd,phone,orgName,orgDesc);

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
            // Get the selected image URI
            // Update the ImageView with the selected image
            Uri selectedImageUri = data.getData();

            Bitmap selectedBitmap = getBitmapFromUri(selectedImageUri);

            // Create a circular bitmap
            Bitmap circularBitmap = getCircularBitmap(selectedBitmap);

            // Update the ImageView with the circular image
            imageView.setImageBitmap(circularBitmap);

            imageView.setImageBitmap(circularBitmap);
            Toast.makeText(this,"Profile Image Updated",Toast.LENGTH_SHORT).show();
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

    private void showPopup(View anchorView) {
        // Inflate the popup_layout.xml
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);

        // Create a PopupWindow
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        // Set a focusable flag to make it respond to touch events outside of the popup
        popupWindow.setFocusable(true);
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int anchorX = location[0] + anchorView.getWidth() / 2;
        int anchorY = location[1] + anchorView.getHeight() / 2;

        // Show the popup at the center of the screen, you can customize the position
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

//        // Set up the ImageView and TextView in the popup layout
//        ImageView iconImageView = popupView.findViewById(R.id.iconImageView);
//        TextView textView = popupView.findViewById(R.id.textView);

        // You can customize the icon and text here
        // Example: iconImageView.setImageResource(R.drawable.your_custom_icon);
        // Example: textView.setText("Your custom text");
    }
    private class PostDataTask1 extends AsyncTask<SignUpModel, Void, Response<SignUpModel>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show loader or progress dialog
            progressDialog = ProgressDialog.show(EmployerAccountSetupRecruiterDetails.this, "Please wait", "Sending data...", true, false);
        }

        @Override
        protected Response<SignUpModel> doInBackground(SignUpModel... signUpModels) {
            // Execute the network operation in the background
            Call<SignUpModel> call = apiInterface.createPost(signUpModels[0]);
            try {
                return call.execute();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Response<SignUpModel> response) {
            super.onPostExecute(response);
            // Dismiss the loader or progress dialog
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (response != null) {
                try {
                    if (response.isSuccessful()) {
                        // Show the popup only if the status code is 200
                        intent = new Intent(EmployerAccountSetupRecruiterDetails.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If the status code is not 200, handle the error or show an appropriate message
                        Toast.makeText(EmployerAccountSetupRecruiterDetails.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    // on below line we are getting our data from modal class and adding it to our string.
                    String responseString = "Response Code : " + response.code();
                    System.out.println(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Handle the case where the response is null or an exception occurred
                Toast.makeText(EmployerAccountSetupRecruiterDetails.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void postData(String name,String accType, String lastName, String emailId, String pSWD, String phoneNum,String orgName, String orgDesc) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        SignUpModel modal = new SignUpModel(name, lastName, emailId, pSWD, accType, phoneNum,"",orgName,"12345",orgDesc,"");

        // Execute the network operation using AsyncTask
        new  PostDataTask1().execute(modal);
    }
}

