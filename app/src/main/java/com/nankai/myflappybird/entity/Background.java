package com.nankai.myflappybird.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.nankai.myflappybird.R;
import com.nankai.myflappybird.util.ResolvePhoto;

public class Background {
    private Context mContext;
    private Paint paint;

    public Background(Context context) {
        mContext = context;
        paint = new Paint();
    }

    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Bitmap bitmap = ResolvePhoto.getBitmap(mContext, R.drawable.background, width, height);
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, width, height), null);
    }
}
