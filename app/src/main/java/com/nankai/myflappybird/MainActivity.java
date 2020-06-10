package com.nankai.myflappybird;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
