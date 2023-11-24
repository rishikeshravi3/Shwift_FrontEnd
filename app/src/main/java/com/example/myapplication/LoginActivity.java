package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.APIHelper.APIClient;
import com.example.myapplication.APIHelper.APIInterface;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_EMAIL = "emailKey";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");

        EditText Email = findViewById(R.id.email_id_login);
        EditText Password = findViewById(R.id.password_login);
        Email.setText(savedEmail);
        TextView to_forgotpassword = findViewById(R.id.forgot_pass);

        to_forgotpassword.setOnClickListener(v -> {
            Intent intent=new Intent(this,passwordReset_Mode_Selection.class);
            startActivity(intent);
        });
        Button loginbtn = findViewById(R.id.button_account_login);
        loginbtn.setOnClickListener(v -> {
            String Mail = Email.getText().toString().trim();
            String Pswd = Password.getText().toString().trim();
            if(Mail.isEmpty()|| Pswd.isEmpty()){
            Toast.makeText(this,"Both Fields are required",Toast.LENGTH_SHORT).show();
            return;
            }else{
                sendLoginRequest(Mail,Pswd);
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
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, JobListingActivity.class);
                    startActivity(intent);

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
                }
                t.printStackTrace();
            }
        });
    }


}