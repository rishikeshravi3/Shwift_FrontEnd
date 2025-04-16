package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

public class CreateAccountActivity extends AppCompatActivity {
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_EMAIL = "emailKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ImageView showPassword = findViewById(R.id.show_password);
        EditText password = findViewById(R.id.password);
        showPassword.setOnClickListener(v -> {
            if (password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                showPassword.setBackgroundResource(R.drawable.eye_slash_regular);
            } else {
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showPassword.setBackgroundResource(R.drawable.eye_regular);
            }
        });

        ImageView showConfirmPassword = findViewById(R.id.show_confirm_password);
        EditText confirm_password = findViewById(R.id.confirm_password);
        showConfirmPassword.setOnClickListener(v -> {
            if (confirm_password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                confirm_password.setInputType(InputType.TYPE_CLASS_TEXT);
                showConfirmPassword.setBackgroundResource(R.drawable.eye_slash_regular);
            } else {
                confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showConfirmPassword.setBackgroundResource(R.drawable.eye_regular);
            }
        });

        TextView login = findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        Button to_accountsetup = findViewById(R.id.button_create_account);
        to_accountsetup.setOnClickListener(v -> {
            EditText mail = findViewById(R.id.email_id_createAccount);
            SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String pswd =password.getText().toString().trim();
            String confirmPassword = confirm_password.getText().toString().trim();
            String Email = mail.getText().toString().trim();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL,Email);
            editor.apply();
            // Validate password and confirm password
            if(Email.isEmpty()){
                showToast("Email field is required");
            }
            else if (pswd.isEmpty() || confirmPassword.isEmpty()) {
                // Show an error message or toast indicating that both fields are required
                showToast("Password and Confirm Password are required");
            } else if (pswd.length() < 6) {
                // Show an error message or toast indicating that the password is too short
                showToast("Password must be at least 6 characters");
            } else if (!pswd.equals(confirmPassword)) {
                // Show an error message or toast indicating that passwords do not match
                showToast("Passwords do not match");
            }else{
            Intent intent=new Intent(this, AccountSetupHome.class);
            intent.putExtra("PasswordKey", confirmPassword);
            startActivity(intent);
            }
        });

    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
