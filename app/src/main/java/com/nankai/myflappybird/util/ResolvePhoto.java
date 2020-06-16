package com.nankai.myflappybird.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Log;

public class ResolvePhoto {
    private static String TAG = ResolvePhoto.class.getName();
    private static Paint paint;

    public static Bitmap getBitmap(Context context, int drawableRes, int width, int height) {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableRes);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, "getBitmap " + e.toString());
            return null;
        }
    }

    public static Bitmap getBitmap(Context context, int drawableRes) {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableRes);
            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, "getBitmap " + e.toString());
            return null;
        }
    }

    public static Paint getPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        return paint;
    }
}
