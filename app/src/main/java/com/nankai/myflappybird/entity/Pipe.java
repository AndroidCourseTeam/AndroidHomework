package com.nankai.myflappybird.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.nankai.myflappybird.R;
import com.nankai.myflappybird.util.GameTool;
import com.nankai.myflappybird.util.ResolvePhoto;

// 水管障碍物
public class Pipe {
    private Context mContext;
    private int[] pass = new int[2];
    private int[] width = new int[2];
    private String TAG = Pipe.class.getName();
    public int ww = 0;

    public Pipe(Context context) {
        mContext = context;
    }

    public void draw(Canvas canvas, int width, int height) {
        Log.i(TAG, "draw " + height);
        ww = width;
        int topPipeHeight = canvas.getHeight() / 2 - GameTool.getInstance().pipeCenterHeight / 2;
        Bitmap bitmap = ResolvePhoto.getBitmap(mContext, R.drawable.pipe2,
                width + GameTool.getInstance().pipeWidth, topPipeHeight);
        canvas.drawBitmap(bitmap, null, new Rect(width, 0,
                width + GameTool.getInstance().pipeWidth, topPipeHeight + height), null);

        int bottomPipeHeight = canvas.getHeight() / 2 - GameTool.getInstance().pipeCenterHeight / 2;
        Bitmap bitmap2 = ResolvePhoto.getBitmap(mContext, R.drawable.pipe1,
                width + GameTool.getInstance().pipeWidth, bottomPipeHeight);
        canvas.drawBitmap(bitmap2, null, new Rect(width, canvas.getHeight() - bottomPipeHeight + height,
                width + GameTool.getInstance().pipeWidth, canvas.getHeight()), null);

        pass[0] = topPipeHeight + height;
        pass[1] = topPipeHeight + GameTool.getInstance().pipeCenterHeight + height;
        this.width[0] = width;
        this.width[1] = width + GameTool.getInstance().pipeWidth;
    }

    public int[] getPass() {
        return pass;
    }

    public int[] getWidth() {
        return width;
    }
}
