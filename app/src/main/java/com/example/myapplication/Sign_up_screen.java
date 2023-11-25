package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.JobListing.JobListingActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// MainActivity.java
public class Sign_up_screen extends Activity {

    private static final int PICK_IMAGE = 1;
    private EditText dobEditText;
    private SimpleDateFormat dateFormatter;
    private ImageButton imageView;
    private Button uploadButton;
    private AutoCompleteTextView genderEditText;
    APIInterface apiInterface;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_EMAIL = "emailKey";

    private Intent intent;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
        imageView = findViewById(R.id.imageView);
        TextInputEditText FName = findViewById(R.id.FirstName_signup);
        TextInputEditText LName = findViewById(R.id.MiddleLastName_signup);
        TextInputEditText mail = findViewById(R.id.Email_signup);
        TextInputEditText PhoneNo = findViewById(R.id.PhoneNumber_signup);
        Button Continue = findViewById(R.id.signup_screen__continue_btn);
        genderEditText = findViewById(R.id.Gender_signup);
        mail.setText(savedEmail);


        genderEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    // Show the list popup when the drawable end icon is clicked
                    showGenderOptionsPopup();
                    return true;
                }
                return false;
            }
        });
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FirstName =FName.getText().toString();
                String LastName =LName.getText().toString();
                String Email =mail.getText().toString();
                String Phone =PhoneNo.getText().toString();

                if (FirstName.isEmpty() || LastName.isEmpty() || Email.isEmpty() || Phone.isEmpty()) {
                    // Show an error message or toast indicating that all fields are required
                    Toast.makeText(Sign_up_screen.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }else {
                    Intent intent = getIntent();
                    String pswd = intent.getStringExtra("PasswordKey");
                    postData(FirstName, LastName, Email, pswd, Phone);
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image picker when the button is clicked
                openImagePicker();
            }
        });
        dobEditText = findViewById(R.id.Dob_signup);
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        dobEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDatePickerDialog();
                    return true;
                }
                return false;
            }
        });
    }

    private void openImagePicker() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    private void showDatePickerDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);
                        dobEditText.setText(dateFormatter.format(selectedDate.getTime()));
                    }
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            // Update the ImageView with the selected image
            Uri selectedImageUri = data.getData();
            //String imagePath = saveImageToLocal(selectedImageUri);
            Bitmap selectedBitmap = getBitmapFromUri(selectedImageUri);

            // Create a circular bitmap
            Bitmap circularBitmap = getCircularBitmap(selectedBitmap);
            String imagePath = saveImageToLocal(selectedImageUri);
            Intent intent = new Intent(this, JobListingActivity.class);
            intent.putExtra("imagePath", imagePath);
            // Update the ImageView with the circular image
            imageView.setImageBitmap(circularBitmap);
            Toast.makeText(this,"Profile Image Updated",Toast.LENGTH_SHORT).show();
        }
    }
    private String saveImageToLocal(Uri imageUri) {
        try {
            // Use ContentResolver to get a bitmap from the URI
            Bitmap selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            // Save the bitmap to a file in local storage
            String fileName = "profile_image.jpg";
            File file = new File(getFilesDir(), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            // Return the path of the saved image
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void showGenderOptionsPopup() {
        ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        List<String> genderOptions = new ArrayList<>();
        genderOptions.add("Male");
        genderOptions.add("Female");
        genderOptions.add("Rather not say");

        ArrayAdapter adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                genderOptions
        );

        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(genderEditText);
        listPopupWindow.setWidth(genderEditText.getWidth());
        listPopupWindow.setDropDownGravity(Gravity.TOP);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the selected gender
                String selectedGender = genderOptions.get(position);
                genderEditText.setText(selectedGender);
                listPopupWindow.dismiss();
            }
        });

        listPopupWindow.show();
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

    private class PostDataTask extends AsyncTask<SignUpModel, Void, Response<SignUpModel>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show loader or progress dialog
            progressDialog = ProgressDialog.show(Sign_up_screen.this, "Please wait", "Sending data...", true, false);
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
                         intent = new Intent(Sign_up_screen.this, JobListingActivity.class);
                        startActivity(intent);
                    } else {
                        // If the status code is not 200, handle the error or show an appropriate message
                        Toast.makeText(Sign_up_screen.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    // on below line we are getting our data from modal class and adding it to our string.
                    String responseString = "Response Code : " + response.code();
                    System.out.println(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Handle the case where the response is null or an exception occurred
                Toast.makeText(Sign_up_screen.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void postData(String name, String lastName, String emailId, String pSWD, String phoneNum) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        String accType = "employee";
        SignUpModel modal = new SignUpModel(name, lastName, emailId, pSWD, accType, phoneNum);

        // Execute the network operation using AsyncTask
        new PostDataTask().execute(modal);
    }
}


