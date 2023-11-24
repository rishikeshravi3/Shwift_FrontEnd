package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
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
import com.google.android.material.textfield.TextInputEditText;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        imageView = findViewById(R.id.imageView);
        TextInputEditText FName = findViewById(R.id.FirstName_signup);
        TextInputEditText LName = findViewById(R.id.MiddleLastName_signup);
        TextInputEditText mail = findViewById(R.id.Email_signup);
        TextInputEditText PhoneNo = findViewById(R.id.PhoneNumber_signup);
        Button Continue = findViewById(R.id.signup_screen__continue_btn);
        genderEditText = findViewById(R.id.Gender_signup);


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
                postData(FirstName,LastName,Email,"@HARRWEW",Phone);
                showPopup(v);

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

            Bitmap selectedBitmap = getBitmapFromUri(selectedImageUri);

            // Create a circular bitmap
            Bitmap circularBitmap = getCircularBitmap(selectedBitmap);

            // Update the ImageView with the circular image
            imageView.setImageBitmap(circularBitmap);

            imageView.setImageBitmap(circularBitmap);
            Toast.makeText(this,"Profile Image Updated",Toast.LENGTH_SHORT).show();
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
        popupWindow.showAtLocation(anchorView,Gravity.CENTER, 0, 0);

//        // Set up the ImageView and TextView in the popup layout
//        ImageView iconImageView = popupView.findViewById(R.id.iconImageView);
//        TextView textView = popupView.findViewById(R.id.textView);

        // You can customize the icon and text here
        // Example: iconImageView.setImageResource(R.drawable.your_custom_icon);
        // Example: textView.setText("Your custom text");
    }

    private void postData(String name, String lastName,String emailId,String pSWD,String phoneNum) {

        // below line is for displaying our progress bar.
        apiInterface = APIClient.getClient().create(APIInterface.class);
        // on below line we are creating a retrofit
        // builder and passing our base url
        // below line is to create an instance for our retrofit api class.
        String accType = "employee";
        // passing data from our text fields to our modal class.
        SignUpModel modal = new SignUpModel(name,lastName,emailId,pSWD,accType,phoneNum);

        // calling a method to create a post and passing our modal class.
        Call<SignUpModel> call = apiInterface.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
              try {
                  SignUpModel responseFromAPI = response.body();
                  // on below line we are getting our data from modal class and adding it to our string.
                  String responseString = "Response Code : " + response.code();
                  System.out.println(responseString);
              } catch (Exception e) {
                  e.printStackTrace();
              }
                // below line we are setting our
                // string to our text view.
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

}
