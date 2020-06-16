package com.nankai.myflappybird.util;

import android.util.Log;

public class GameTool {
    private static GameTool tool;
    private static final String TAG = GameTool.class.getName();

    public static GameTool getInstance() {
        if (tool == null) {
            tool = new GameTool();
        }
        return tool;
    }

    private int screenWidth;
    private int screenHeight;

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
        pipeHeight = screenHeight * 2 / 5;
        birdHeight = (int) (screenHeight * 0.055);
        birdWidth = birdHeight * 34 / 24;
        pipeWidth = (int) (birdWidth * 1.3);
        pipeCenterHeight = screenHeight / 5;
        Log.i(TAG, "setScreenHeight " + screenHeight + "   pipeHeight" + pipeHeight
                + "   birdWidth" + birdWidth + "   " + "pipeWidth" + pipeWidth
                + "   pipeCenterHeight" + pipeCenterHeight);
        jumpHeight = screenHeight * 50 / 850;
    }

    public int birdWidth = 0;
    public int birdHeight = 0;
    public int pipeWidth = 0;
    public int pipeHeight = 0;
    public int pipeCenterHeight = 0;
    public int jumpHeight = 0;
    private float dpi = 0;

    public float getDpi() {
        return dpi;
    }

    public void setDpi(float dpi) {
        this.dpi = dpi;
    }
}
