package com.app.carnelao;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class PlayScreen extends AppCompatActivity {


    private static final long DEFAULT_ANIMATION_DURATION = 2000;
    ImageView item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        item = (ImageView) findViewById(R.id.imgItem);
        item.bringToFront();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Display display = getWindowManager().getDefaultDisplay();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, display.getHeight());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                item.setTranslationY(value);
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(DEFAULT_ANIMATION_DURATION);
//6
        valueAnimator.start();

        return super.onTouchEvent(event);
    }
}
