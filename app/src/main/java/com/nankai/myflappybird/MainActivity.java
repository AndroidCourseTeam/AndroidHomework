package com.nankai.myflappybird;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nankai.myflappybird.util.GameTool;

public class MainActivity extends AppCompatActivity {
    private LinearLayout addGameView;
    private GameView gameView;
    private TextView pause;
    // 游戏窗口的宽与高
    public static int windowHeight;
    public static int windowWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置窗口全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 获取窗口尺寸
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        windowHeight = metrics.heightPixels;
        windowWidth = metrics.widthPixels;
        // 绘制游戏界面
        setContentView(R.layout.activity_main);
        addGameView = findViewById(R.id.addGameView);
        gameView = new GameView(this);
        addGameView.addView(gameView);
        // 暂停键
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameView != null) {
                    if (gameView.getRunning())
                        gameView.setRunning(false);
                    else
                        gameView.setRunning(true);
                }
            }
        });

        GameTool.getInstance().setScreenHeight(windowHeight);
        GameTool.getInstance().setScreenWidth(windowWidth);

    }

    @Override
    protected void onResume() {
        if (gameView != null)
            gameView.setResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (gameView != null)
            gameView.setRunning(false);
        super.onPause();
    }
}
