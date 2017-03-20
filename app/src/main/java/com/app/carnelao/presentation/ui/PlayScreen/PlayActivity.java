package com.app.carnelao.presentation.ui.PlayScreen;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.carnelao.R;

public class PlayActivity extends AppCompatActivity implements PlayContract.View{

    private ImageView imgItem;
    private TextView lblScoreRight;
    private TextView lblScoreLeft;
    private RelativeLayout lytTargetsContainer;
    private LinearLayout lytWall;
    private ValueAnimator valueAnimator;
    private boolean isMoving = false;
    private PlayContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        // bind views
        imgItem = (ImageView) findViewById(R.id.imgItem);
        lblScoreLeft = (TextView)findViewById(R.id.lbl_score_left);
        lblScoreRight = (TextView)findViewById(R.id.lbl_score_right);
        lytTargetsContainer = (RelativeLayout) findViewById(R.id.lyt_targets_container);
        lytWall = (LinearLayout) findViewById(R.id.lyt_wall);

        // set up presenter
        presenter = new PlayPresenter();
        presenter.attach(this);
        presenter.setContext(getApplicationContext());
        presenter.startGame();
    }

    // UI methods

    public void btnLeftClicked(View button){
        if(!isMoving) presenter.moveLeft();
    }

    public void btnRightClicked(View button){
        if(!isMoving) presenter.moveRight();
    }

    // PRESENTER methods

    @Override
    public void resetScreen(int finalPosition, int animationDuration) {
        lblScoreLeft.setText("0");
        lblScoreRight.setText("0");
        Toast.makeText(this, "STAAAARTTTT", Toast.LENGTH_LONG).show();
        moveDown(finalPosition, animationDuration);
    }

    @Override
    public void moveItemRight(final int distance, final int animationDuration) {

        isMoving = true;
        valueAnimator = ValueAnimator.ofFloat(0,distance );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                imgItem.setTranslationX(value);

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
    public void moveItemLeft(final int distance, final int animationDuration) {

        isMoving = true;
        valueAnimator = ValueAnimator.ofFloat(0, distance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                imgItem.setTranslationX(value);

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


        imgItem.bringToFront();
        valueAnimator = ValueAnimator.ofFloat(0,distance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                imgItem.setTranslationY(value);

                if(value >= distance){
                    presenter.setNewObject();
                    int xPosition = getWindowManager().getDefaultDisplay().getWidth()/ 2 - imgItem.getWidth()/2;
                    imgItem.setX(xPosition);
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
    public void updateFailsLabel(String score) {
        lblScoreLeft.setText(score);
    }

    @Override
    public void updateScoreLabel(String score) {
        lblScoreRight.setText(score);
    }

    public boolean insideTargetRange() {
        float minY = lytTargetsContainer.getY();
        float maxY = minY + lytTargetsContainer.getHeight();
        return (imgItem.getY() > minY && imgItem.getY() < maxY);
    }

    @Override
    public void moveWallUp(int addValue) {
        lytWall.getLayoutParams().height = lytWall.getHeight() + addValue;
        lytWall.requestLayout();
    }

    @Override
    public void setImageItem(int imgResourceId) {
        imgItem.setImageResource(imgResourceId);
    }
}
