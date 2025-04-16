package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

// MainActivity.java
public class Sign_up_screen extends Activity {

    private static final int PICK_IMAGE = 1;
    private EditText dobEditText;
    private SimpleDateFormat dateFormatter;
    private ImageButton imageView;
    private Button uploadButton;
    private AutoCompleteTextView genderEditText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        imageView = findViewById(R.id.imageView);
        Button Toasttemp = findViewById(R.id.signup_screen__continue_btn);
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

        Toasttemp.setOnClickListener(v->{
            Toast.makeText(this, "Implementation in Progress...", Toast.LENGTH_LONG).show();
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
}
