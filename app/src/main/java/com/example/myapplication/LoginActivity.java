package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;
import com.example.myapplication.EmployerView.Employer_View_Home_Page;
import com.example.myapplication.Helper.Common;
import com.example.myapplication.Helper.Constants;
import com.example.myapplication.JobListing.JobListingActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(Constants.KEY_EMAIL, "");

        EditText Email = findViewById(R.id.email_id_login);
        EditText Password = findViewById(R.id.password_login);
        Email.setText(savedEmail);
        TextView to_forgotpassword = findViewById(R.id.forgot_pass);

        to_forgotpassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, passwordReset_Mode_Selection.class);
            startActivity(intent);
        });
        Button loginbtn = findViewById(R.id.button_account_login);
        ImageView show_password=findViewById(R.id.show_password);
        loginbtn.setOnClickListener(v -> {
            String Mail = Email.getText().toString().trim();
            String Pswd = Password.getText().toString().trim();
//            Mail = "hamza@rang";
//            Pswd = "@Hamza123";
//            Mail = "rishikeshravi@shwiftapp.com";
//            Pswd = "dcba";
//             Mail = "johndoe@abc.com";
//             Pswd = "abcabc";
            Mail = "rishitestss@abc.com";
            Pswd = "abc";
            if (Mail.isEmpty() || Pswd.isEmpty()) {
                Toast.makeText(this, "Both Fields are required", Toast.LENGTH_SHORT).show();
            } else {
                sendLoginRequest(Mail, Pswd);
            }
        });

        show_password.setOnClickListener(v -> {
            if (Password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                Password.setInputType(InputType.TYPE_CLASS_TEXT);
                show_password.setBackgroundResource(R.drawable.eye_slash_regular);
            } else {
                Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                show_password.setBackgroundResource(R.drawable.eye_regular);
            }
        });


        TextView to_createAccount = findViewById(R.id.go_to_create_account);
        to_createAccount.setOnClickListener(v -> {
            finish();
        });
    }

    private void sendLoginRequest(String userName, String password) {

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<LoginModel> call = apiInterface.login(userName, password);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.code() == 200) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body());
                    sharedPreferences.edit().putString(Constants.KEY_USER_DATA, json).apply();
                    if (response.body() != null) {
                        if ("employee".equals(response.body().acc_type)) {
                            Intent intent = new Intent(LoginActivity.this, JobListingActivity.class);
                            startActivity(intent);
                        } else if ("employer".equals(response.body().acc_type)) {
                            Intent intent = new Intent(LoginActivity.this, Employer_View_Home_Page.class);
                            startActivity(intent);
                        }
                    }
                    Common.printShort(LoginActivity.this, "Login Successful");
                    finish();
                } else {
                    // Handle login failure
                    Toast.makeText(LoginActivity.this, "Login failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // Handle network errors or other failures
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Common.printShort(LoginActivity.this, "Login Error");
                }
                t.printStackTrace();
            }
        });
    }


}