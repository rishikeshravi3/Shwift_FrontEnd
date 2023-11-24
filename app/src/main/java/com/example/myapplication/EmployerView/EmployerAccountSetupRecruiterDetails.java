package com.example.myapplication.EmployerView;

import androidx.appcompat.app.AppCompatActivity;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.IOException;

public class EmployerAccountSetupRecruiterDetails extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private ImageButton imageView;

    private Button Continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_account_setup_recruiter_details);
        imageView = findViewById(R.id.employer_account_setup_recruiter_details_image);
        Continue=findViewById(R.id.activity_employer_account_setup_recruiter_details_button);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image picker when the button is clicked
                openImagePicker();
            }
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);

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
}

