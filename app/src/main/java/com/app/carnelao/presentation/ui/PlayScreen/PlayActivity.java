package com.app.carnelao.presentation.ui.PlayScreen;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private long DEFAULT_ANIMATION_DURATION = 2000;

    private int displayHeight;
    private int displayWidth;
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

    @Override
    public void startGame(Constants.ValueAnimatorLevel level) {
        lblScoreLeft.setText("0");
        lblScoreRight.setText("0");

        moveDown(level.getUpToDownTime());
    }

    public void moveDown(final int animationDuration){

        ValueAnimator valueAnimatorUpToDown;

        if(displayHeight == 0){
            Display display = getWindowManager().getDefaultDisplay();
            displayHeight = display.getHeight();
        }

        item.bringToFront();
        valueAnimatorUpToDown = ValueAnimator.ofFloat(0,displayHeight);
        valueAnimatorUpToDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                item.setTranslationY(value);

                if(value >= displayHeight){
                    moveDown(animationDuration);
                }

            }

        });

        valueAnimatorUpToDown.setInterpolator(new LinearInterpolator());
        valueAnimatorUpToDown.setDuration(animationDuration);
        valueAnimatorUpToDown.setRepeatCount(Animation.INFINITE);
        valueAnimatorUpToDown.start();
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
