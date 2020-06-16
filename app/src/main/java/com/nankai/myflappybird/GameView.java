package com.nankai.myflappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nankai.myflappybird.entity.Background;
import com.nankai.myflappybird.entity.Bird;
import com.nankai.myflappybird.entity.Pipe;
import com.nankai.myflappybird.util.GameTool;

// SurfaceView适用于被动更新的情况，比如频繁刷新界面
// 开启一个子线程来对页面进行刷新，可以在自线程中更新UI，不会阻塞主线程，响应速度快。
class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private static final String TAG = GameView.class.getName();
    private Context mainContest = null;
    // 绘图需要的对象
    private Paint paint = null;
    private Canvas mCanvas = null;
    // 维护SurfaceView上绘制的内容
    private SurfaceHolder surfaceHolder;
    // 游戏要素
    private Bird bird;
    private Pipe pipe;
    private Background background;
    private int[] piHeight;
    private int score;

    private Thread thread;
    private boolean isRunning = true;
    private boolean startGame = true;

    public GameView(Context context) {
        super(context);
        mainContest = context;
        paint = new Paint();
        paint.setAntiAlias(true);  // 抗锯齿
        setKeepScreenOn(true);  // 屏幕常亮
        setFocusable(true);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        background = new Background(mainContest);
        bird = new Bird(mainContest);
        pipe = new Pipe(mainContest);
    }

    // Only one thread can ever draw into a Surface
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private boolean init = false;

    @Override
    public void run() {
        while (isRunning) {
            if (!init) {
                piHeight = new int[]{GameTool.getInstance().pipeCenterHeight / 5,
                        -GameTool.getInstance().pipeCenterHeight / 2,
                        (int) (GameTool.getInstance().pipeCenterHeight * 0.8),
                        -(int) (GameTool.getInstance().pipeCenterHeight * 0.3)};
                init = true;
            }
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50)
                    Thread.sleep(50 - (end - start));
            } catch (Exception e) {
                Log.e(TAG, "run " + e.toString());
            }
            if (!startGame) {
                startGame = true;
                isRunning = false;
            }
        }
    }

    private void draw() {
        try {
            mCanvas = surfaceHolder.lockCanvas();
            if (mCanvas != null) {
                background.draw(mCanvas);
                drawBirdDrop();
                drawPipe();
                drawScore();
                isPass();
            }
        } catch (Exception e) {
            Log.e(TAG, "draw ");
        } finally {
            if (mCanvas != null) {
                surfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private int birdHeight = 0;
    private int riseStart = 0;

    private void drawBirdDrop() {
        if (birdHeight == 0)
            birdHeight = mCanvas.getHeight() / 2;
        //上升
        if (rise >= 0) {
            birdHeight = birdHeight - getRise();
        }
        //下降
        else {
            birdHeight = birdHeight + getH();
        }

        if (birdHeight <= mCanvas.getHeight()) {
            bird.draw(mCanvas, birdHeight);
        }
        //到顶部
        else if (birdHeight <= 0) {
            bird.draw(mCanvas, 0);
        }
        //到底边
        else {
            birdHeight = mCanvas.getHeight() / 2;
            h = 0;
        }
    }

    int rise = -1;

    private int getRise() {
        rise = rise + GameTool.getInstance().jumpHeight * 15 / 50;
        Log.i(TAG, "getRise " + riseStart + "   " + birdHeight);
        if (GameTool.getInstance().jumpHeight < rise) {
            rise = -1;
            return -1;
        } else
            return rise;
    }

    private int h = 0;

    // 加速度函数
    private int getH() {
        h = h + 10;
        return h;
    }

    // 水管位置
    int p = -1;
    int lastPipeWidth = 10;
    int hhh = 0;

    private void drawPipe() {
        if (p == -1) {
            p = mCanvas.getWidth();
            hhh = piHeight[score % 4];
        }
        pipe.draw(mCanvas, p, hhh);
        lastPipeWidth = pipe.ww;
        p -= GameTool.getInstance().jumpHeight * 15 / 70;
        if (p < 0)
            p = -1;
    }

    private boolean startPass = false;

    private void isPass() {
        if (pipe.getWidth()[0] <= (bird.getRect().right) && pipe.getWidth()[1] >= bird.getRect().left) {
            startPass = true;
            if (birdHeight - bird.getBirdHeight() / 2 > pipe.getPass()[0] && birdHeight + bird.getBirdHeight() / 2 < pipe.getPass()[1]) {
                Log.i(TAG, "isPass true");
            }
            // 游戏失败
            else {
                Log.i(TAG, "isPass false");
                rise = -1;
                h = 0;
                p = -1;
                score = 0;
            }
        }
        if (pipe.getWidth()[1] < bird.getRect().left && startPass) {
            score++;
            startPass = false;
            Log.i(TAG, "isPass 1   " + score);
        }
    }

    private void drawScore() {
        Paint paint = new Paint();
        paint.setTextSize(32);
        paint.setColor(Color.parseColor("#333333"));
        mCanvas.drawText(score + "", mCanvas.getWidth() / 2, mCanvas.getHeight() / 3, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent ");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isRunning) {
                isRunning = true;
                new Thread(this).start();
            } else {
                riseStart = birdHeight;
                rise = 0;
                h = 0;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public boolean getRunning() {
        return this.isRunning;
    }

    public void setResume() {
        isRunning = true;
        startGame = false;
    }
}
