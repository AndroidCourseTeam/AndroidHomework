package com.nankai.myflappybird.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.nankai.myflappybird.R;
import com.nankai.myflappybird.util.GameTool;
import com.nankai.myflappybird.util.ResolvePhoto;

public class Bird {
    private int height;
    private int CanvasHeight;
    private Context mContest;
    private Rect rect;
    private int birdHeight = -2;

    public Bird(Context context) {
        mContest = context;
    }

    public void draw(Canvas canvas, int height) {
        CanvasHeight = canvas.getHeight();

        int centerWidth = canvas.getWidth() / 2;
        int centerHeight = height;
        this.height = height;
        Bitmap bitmap = ResolvePhoto.getBitmap(mContest, R.drawable.bird,
                GameTool.getInstance().birdWidth, GameTool.getInstance().birdHeight);

        int birdWidth = bitmap.getWidth();
        int birdHeight = bitmap.getHeight();
        this.birdHeight = birdHeight;
        rect = new Rect(centerWidth - birdWidth / 2, centerHeight - birdHeight / 2,
                centerWidth + birdWidth / 2, centerHeight + birdHeight / 2);
        canvas.drawBitmap(bitmap, null, rect, null);

    }

    public int getHeight() {
        return height;
    }

    public Rect getRect() {
        return rect;
    }

    public int getBirdHeight() {
        return birdHeight;
    }
}
