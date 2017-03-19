package com.app.carnelao.presentation.ui.PlayScreen;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.carnelao.R;
import com.app.carnelao.util.Constants;

import java.util.logging.Level;

public class PlayActivity extends AppCompatActivity implements PlayContract.View{

    private ImageView item;
    private TextView lblScoreRight;
    private TextView lblScoreLeft;
    private RelativeLayout lytTargetsContainer;
    ValueAnimator valueAnimator;
    private boolean isMoving = false;
    private PlayContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        item = (ImageView) findViewById(R.id.imgItem);
        lblScoreLeft = (TextView)findViewById(R.id.lbl_score_left);
        lblScoreRight = (TextView)findViewById(R.id.lbl_score_right);
        lytTargetsContainer = (RelativeLayout) findViewById(R.id.lyt_targets_container);

        presenter = new PlayPresenter();
        presenter.attach(this);
        presenter.setContext(getApplicationContext());
        presenter.startGame();

    }

    // UI methods
    public void btnLeftClicked(View button){
        if(!isMoving)
            presenter.moveLeft();
    }

    public void btnRightClicked(View button){
        if(!isMoving)
            presenter.moveRight();
    }

    // PRESENTER
    @Override
    public void startGame(int finalPosition, int animationDuration) {
        lblScoreLeft.setText("0");
        lblScoreRight.setText("0");
        moveDown(finalPosition, animationDuration);
    }

    @Override
    public void moveRight(final int distance, final int animationDuration) {

        isMoving = true;
        item.bringToFront();
        valueAnimator = ValueAnimator.ofFloat(0,distance );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                item.setTranslationX(value);

                if(value >= distance && insideTargetRange()) {
                    presenter.hitRightSide();
                }
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(animationDuration);
        valueAnimator.start();
    }

    @Override
    public void moveLeft(final int distance, final int animationDuration) {

        isMoving = true;
        item.bringToFront();
        valueAnimator = ValueAnimator.ofFloat(0, distance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                item.setTranslationX(value);

                if(value <= distance && insideTargetRange()){
                    presenter.hitLeftSide();
                }
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(animationDuration);
        valueAnimator.start();
    }


    public void moveDown(final int distance, final int animationDuration){

        item.bringToFront();
        valueAnimator = ValueAnimator.ofFloat(0,distance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                item.setTranslationY(value);

                if(value >= distance){

                    int xPosition = getWindowManager().getDefaultDisplay().getWidth()/ 2 - 70;
                    item.setX(xPosition);
                    moveDown(distance,animationDuration);
                    isMoving = false;
                }

            }

        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(animationDuration);
        valueAnimator.start();
    }

    @Override
    public void updateLeftScoreLabel(String score) {
        lblScoreLeft.setText(score);
    }

    @Override
    public void updateRightScoreLabel(String score) {
        lblScoreRight.setText(score);
    }

    public boolean insideTargetRange() {
        float minY = lytTargetsContainer.getY();
        float maxY = minY + lytTargetsContainer.getHeight();
        return (item.getY() > minY && item.getY() < maxY);
    }
}
