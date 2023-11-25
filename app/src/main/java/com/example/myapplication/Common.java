package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

public class Common {
    public static void print(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }
}
