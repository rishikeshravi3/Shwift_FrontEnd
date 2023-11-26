package com.example.myapplication.Profile;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Helper.Common;
import com.example.myapplication.LoginModel;
import com.example.myapplication.R;

import java.io.IOException;

public class Profile_Details_Activity extends AppCompatActivity {
    private ImageButton imageView;

    private static final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        imageView = findViewById(R.id.imageView1);
        EditText FirstName = findViewById(R.id.FirstName_profile);
        LoginModel obj = Common.getUserData(this);
        FirstName.setText(obj.first_name);
        EditText LastName = findViewById(R.id.LastName_profile);
        LastName.setText(obj.last_name);
        EditText CurrentPosition = findViewById(R.id.Current_position_profile);
        Button save = findViewById(R.id.profileDetails_savebtn);
        ProfileResponseModel profileData = Common.getProfileData(this);
        if(profileData!=null) {
            CurrentPosition.setText(profileData.curr_position);
        }

        save.setOnClickListener(v -> {
            UpdateProfileRequest req = new UpdateProfileRequest();
            req.col_name="curr_position";
            req.value = CurrentPosition.getText().toString().trim();
            UpdateProfileService.Service(this, req, new UpdateProfileService.UpdateProfileCallback() {
                @Override
                public void onUpdateSuccess() {
                    // Update successful, start the new activity
                    Intent intent = new Intent(Profile_Details_Activity.this, ProfileActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onUpdateFailure() {
                    // Handle update failure if needed
                    Toast.makeText(Profile_Details_Activity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });

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
}