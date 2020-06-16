package com.nankai.myflappybird;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
public class MainActivity extends AppCompatActivity {
    public static final String TAG_EXIT = "exit";
    public static final String TAG_SCORE = "score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_layout);
        final ImageView MainBackground = (ImageView) findViewById(R.id.MainBackground);
        final ImageView SecondBackground = (ImageView) findViewById(R.id.SecondBackground);
        final ValueAnimator BackgroundAnimator = ValueAnimator.ofFloat(0.0f,1.0f);
        BackgroundAnimator.setRepeatCount(ValueAnimator.INFINITE);
        BackgroundAnimator.setInterpolator(new LinearInterpolator());
        BackgroundAnimator.setDuration(10000L);
        BackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float Progress = (float) animation.getAnimatedValue();
                final float Width = MainBackground.getWidth();
                final float TranslationX = Width * Progress;
                MainBackground.setTranslationX(TranslationX);
                SecondBackground.setTranslationX(TranslationX - Width);
            }
        });
        BackgroundAnimator.start();
    }
    public void onPlayButtonClicked(View view){
        Intent intent=new Intent(MainActivity.this,GameActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
            if (isExit) {
                AlertDialog.Builder finishDialog=new AlertDialog.Builder(MainActivity.this);
                finishDialog.setTitle("Game Over!");
                String finishMessage="Game Over! Your score is "+intent.getExtras().getString(TAG_SCORE)+" !";
                finishDialog.setMessage(finishMessage);
                finishDialog.setPositiveButton("Continue",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.this.onPlayButtonClicked(null);
                    }
                });
                finishDialog.setNegativeButton("Exit",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       MainActivity.this.finish();
                    }
                });
                finishDialog.create().show();
            }
        }
    }
}