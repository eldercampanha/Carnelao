package com.app.carnelao.presentation.ui.PlayScreen;

import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.carnelao.R;

public class PlayActivity extends AppCompatActivity implements PlayContract.View{

    private ImageView imgItem;
    private ImageView imgNextItem;
    private TextView lblScoreRight;
    private TextView lblScoreLeft;
    private RelativeLayout lytTargetsContainer;
    private LinearLayout lytTop;
    private LinearLayout lytWall;
    private LinearLayout lytButtons;
    private ValueAnimator valueAnimator;
    private PlayContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        // bind views
        imgItem = (ImageView) findViewById(R.id.img_item);
        imgNextItem = (ImageView) findViewById(R.id.img_next_item);
        lblScoreLeft = (TextView)findViewById(R.id.lbl_score_left);
        lblScoreRight = (TextView)findViewById(R.id.lbl_score_right);
        lytTargetsContainer = (RelativeLayout) findViewById(R.id.lyt_targets_container);
        lytWall = (LinearLayout) findViewById(R.id.lyt_wall);
        lytButtons = (LinearLayout) findViewById(R.id.lyt_buttons);
        lytTop = (LinearLayout) findViewById(R.id.lyt_top);

        // set up presenter
        presenter = new PlayPresenter();
        presenter.attach(this);
        presenter.setContext(getApplicationContext());
        presenter.startGame();

        // set up View layers order
        imgItem.bringToFront();
        lytWall.bringToFront();
        lytButtons.bringToFront();
        lytTop.bringToFront();

    }

    // UI methods

    public void btnLeftClicked(View button){
         presenter.moveLeft();
    }

    public void btnRightClicked(View button){
        presenter.moveRight();
    }

    // PRESENTER methods

    @Override
    public void resetScreen(int finalPosition, int animationDuration) {
        lblScoreLeft.setText("0");
        lblScoreRight.setText("0");
        Toast.makeText(this, "STAAAARTTTT", Toast.LENGTH_SHORT).show();
        startConveyerAnimation(finalPosition, animationDuration);
    }

    @Override
    public void moveItemRight(final int distance, final int animationDuration) {

        valueAnimator = ValueAnimator.ofFloat(0,distance );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                imgItem.setTranslationX(value);

                if(value >= distance){
                    if( insideTargetRange())
                      presenter.hitRightSide();
                    else
                      presenter.touchTheBottom();
                }
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(animationDuration);
        valueAnimator.start();
    }

    @Override
    public void moveItemLeft(final int distance, final int animationDuration) {

        valueAnimator = ValueAnimator.ofFloat(0, distance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                imgItem.setTranslationX(value);

                if(value <= distance ){
                    if(insideTargetRange())
                        presenter.hitLeftSide();
                    else
                        presenter.touchTheBottom();
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

    @Override
    public void finishGame() {
        valueAnimator.setRepeatCount(1);
    }

    // INTERNAL METHODS

    public void startConveyerAnimation(final int distance, final int animationDuration){

        imgNextItem.setBackgroundResource(R.drawable.conveyor);
        AnimationDrawable anim = (AnimationDrawable) imgNextItem.getBackground();
        anim.start();


        valueAnimator = ValueAnimator.ofFloat(0,distance);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                imgItem.setTranslationY(value);

                if(value >= distance){
                    int xPosition = getWindowManager().getDefaultDisplay().getWidth()/ 2 - imgItem.getWidth()/2;
                    imgItem.setX(xPosition);

                    presenter.newCicleStarted();
                    if(!presenter.isGameOver())
                        startConveyerAnimation(distance,presenter.getAnimationDuration());
                }

            }

        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(animationDuration);
        valueAnimator.start();
    }
}
