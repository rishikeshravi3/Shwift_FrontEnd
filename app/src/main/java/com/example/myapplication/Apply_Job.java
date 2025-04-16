package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.EmployerView.Employer_View_Home_Page;
import com.example.myapplication.EmployerView.JobDetailsActivityPreview;
import com.example.myapplication.EmployerView.JobDetailsModel;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.Constants;
import com.example.myapplication.Helper.UploadImageRequest;
import com.example.myapplication.JobListing.JobListingActivity;
import com.example.myapplication.JobListing.JobModel;
import com.example.myapplication.Profile.Profile_Details_Activity;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Apply_Job extends Activity {
    APIInterface apiInterface;
    private static final int PICK_PDF_REQUEST = 1;
    String resumeUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        MaterialTextView btnUploadCV = findViewById(R.id.btnUploadCV);
        LoginModel obj = Common.getUserData(Apply_Job.this);

        btnUploadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the file picker when the TextView is clicked
                openFilePicker();
            }
        });

        Button apply=findViewById(R.id.applyButton);

        Gson gson = new Gson();
        String jobData=getIntent().getStringExtra("jobData");
        JobModel object = gson.fromJson(jobData, JobModel.class);

        TextView applicantEmail=findViewById(R.id.editTextEmail);
        applicantEmail.setText(obj.email_id);
        TextView fullName=findViewById(R.id.editTextFullName);
        fullName.setText(obj.first_name+" "+obj.last_name);
        String applicantEmailText=applicantEmail.getText().toString();
        String jobId=object.job_id;
        String EmployerEmailText=object.recruiter_email_id;
//        String resumeUrl="";
        int applicationStatus=1;
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(applicantEmailText,applicationStatus,jobId,resumeUrl,EmployerEmailText);
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf"); // Limit to PDF files

        startActivityForResult(intent, PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedFileUri = data.getData();
                uploadResume(selectedFileUri);
                uploadFile(selectedFileUri);
            }
        }
    }

    private void uploadResume(Uri selectedFileUri) {
        String path = getPathFromUri(selectedFileUri);
        new UploadPdfBase64Task(Constants.BASE_URL + "/shwift/uploadPdf", selectedFileUri.getPath(), selectedFileUri).execute();
    }

    private void uploadFile(Uri fileUri) {
        // Simulate file upload (replace this with your actual file upload logic)
        String fileName = getFileName(fileUri);

        // Save the file to internal storage
        saveFileToInternalStorage(fileUri, fileName);

        String message = "File Uploaded: " + fileName;

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        MaterialTextView btnUploadCV = findViewById(R.id.btnUploadCV);
        btnUploadCV.setText("File Uploaded: " + fileName);
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void saveFileToInternalStorage(Uri sourceUri, String fileName) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(sourceUri);
            File internalStorageDir = getDir("internal_storage", Context.MODE_PRIVATE);
            File outputFile = new File(internalStorageDir, fileName);
            OutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postData(String applicantEmailId, int applicationStatus, String jobId, String resumeUrl,
                          String employerEmailId) {

        // below line is for displaying our progress bar.
        LoginModel obj = Common.getUserData(Apply_Job.this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        // on below line we are creating a retrofit
        // builder and passing our base url
        // below line is to create an instance for our retrofit api class.
        // passing data from our text fields to our modal class.
        Apply_JobModel modal = new Apply_JobModel(obj.email_id,applicationStatus,jobId,resumeUrl,employerEmailId);

        // calling a method to create a post and passing our modal class.
        Call<Apply_JobModel> call = apiInterface.applyJob(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<Apply_JobModel>() {
            @Override
            public void onResponse(Call<Apply_JobModel> call, Response<Apply_JobModel> response) {
                try {
                    //JobDetailsModel responseFromAPI = response.body();
                    // on below line we are getting our data from modal class and adding it to our string.
                    String responseString = "Response Code : " + response.code();
                    if(response.code()==201 || response.code() == 200){
                        Toast.makeText(Apply_Job.this,"Application Posted Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Apply_Job.this, JobListingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        finish();
                        startActivity(intent);
                    }
                    System.out.println(responseString);
                } catch (Exception e) {
                    Common.printShort(Apply_Job.this,"Api Error");
                    e.printStackTrace();
                }
                // below line we are setting our
                // string to our text view.
            }

            @Override
            public void onFailure(Call<Apply_JobModel> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    public  String getPathFromUri(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
            cursor.close();
        }
        return filePath;
    }

    public class UploadPdfBase64Task extends AsyncTask<Void, Void, String> {

        private String apiUrl;
        private String pdfFilePath;
        private Uri pdfUri;

        public UploadPdfBase64Task(String apiUrl, String pdfFilePath, Uri uri) {
            this.apiUrl = apiUrl;
            this.pdfFilePath = pdfFilePath;
            this.pdfUri = uri;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // Read the PDF file and encode it as Base64
                ContentResolver contentResolver = getContentResolver();
                InputStream inputStream = contentResolver.openInputStream(pdfUri);
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                byte[] bytes = output.toByteArray();
                String base64pdf = Base64.encodeToString(bytes, Base64.DEFAULT);
                inputStream.close();

                // Construct the JSON payload with the Base64-encoded PDF
                String jsonPayload = "data:" + "application/pdf" + ";base64," + base64pdf;

                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                UploadPdfRequest request = new UploadPdfRequest();
                request.base64Pdf = jsonPayload;
                if (request.base64Pdf != null) {
                    Call<ResponseBody> call = apiInterface.uploadPdf(request);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()) {
                                try {
                                    resumeUrl = response.body().string();
                                } catch (Exception e) {

                                }
                            } else {
                                Common.print(Apply_Job.this, "Failed to upload pdf");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        // You can override onPostExecute to handle the result if needed
        @Override
        protected void onPostExecute(String result) {
            // Handle the result after the task completes
        }
    }
}