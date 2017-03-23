package com.app.carnelao.presentation.ui.playscreen;

import android.animation.ValueAnimator;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.carnelao.R;
import com.app.carnelao.presentation.ui.gameover.GameOverActivity;
import com.app.carnelao.util.Constants;
import static com.app.carnelao.util.Constants.SCORE_BUNDLE_KEY;

public class PlayActivity extends AppCompatActivity implements PlayContract.View{

    private MediaPlayer mMainMediaPlayer,
            mSwipeLeftMediaPlayer,
            mSwipeRightMediaPlayer,
            mFailMediaPlayer,
            mGameOverMediaPlayer,
            mGateClosingMediaPlayer;

    private ImageView loadTruck;
    private ImageView trashTruck;
    private ImageView imgItem;
    private ImageView imgNextItem;
    private TextView lblScoreRight;
    private RelativeLayout lytTargetsContainer;
    private LinearLayout lytTop;
    private LinearLayout lytWall;
    private LinearLayout lytButtons;
    private ValueAnimator valueAnimator;
    private PlayContract.Presenter presenter;
    private float x1 = 0, x2 = 0;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);



        // bind views
        imgItem = (ImageView) findViewById(R.id.img_item);
        lblScoreRight = (TextView)findViewById(R.id.lbl_score_right);
        lytTargetsContainer = (RelativeLayout) findViewById(R.id.lyt_targets_container);
        lytWall = (LinearLayout) findViewById(R.id.lyt_wall);
        lytButtons = (LinearLayout) findViewById(R.id.lyt_buttons);
        loadTruck = (ImageView)findViewById(R.id.img_load_truck);
        trashTruck = (ImageView)findViewById(R.id.img_trash_truck);
        imgNextItem = (ImageView)findViewById(R.id.img_next_item);
        //lytTop = (LinearLayout) findViewById(R.id.lyt_top);



        // set up View layers order
        imgItem.bringToFront();
        lytTargetsContainer.bringToFront();
        loadTruck.bringToFront();
        trashTruck.bringToFront();
        lytWall.bringToFront();
        lytButtons.bringToFront();
        //lytTop.bringToFront();

        imgNextItem.setBackgroundResource(R.drawable.conveyor_anim);
        AnimationDrawable anim = (AnimationDrawable) imgNextItem.getBackground();
        anim.start();

        // SET UP MEDIA PLAYE

        // conveyor_anim
        mMainMediaPlayer = MediaPlayer.create(this,Constants.SoundId.CONVEYOR.getSoundId());
        mMainMediaPlayer.setVolume(0.3f,0.3f);
        mMainMediaPlayer.setLooping(true);
        // swipe left and right / gate / fail / game over
        mSwipeLeftMediaPlayer = MediaPlayer.create(this,Constants.SoundId.SWIPE_LEFT.getSoundId());
        mSwipeRightMediaPlayer = MediaPlayer.create(this,Constants.SoundId.SWIPE_RIGHT.getSoundId());
        mGateClosingMediaPlayer = MediaPlayer.create(this,Constants.SoundId.GATE.getSoundId());
        mFailMediaPlayer = MediaPlayer.create(this,Constants.SoundId.FAIL.getSoundId());
        mGameOverMediaPlayer = MediaPlayer.create(this,Constants.SoundId.GAME_OVER.getSoundId());
        mMainMediaPlayer.start();

        // enable swipe on the wall
//        enableSwipeWall();

        // set up presenter
        presenter = new PlayPresenter();
        presenter.attach(this);
        presenter.setContext(getApplicationContext());
        presenter.startGame();


        // used to hide nav bar and status bar
//        decorView = getWindow().getDecorView();
    }

    private void enableSwipeWall() {
        lytWall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                didSwipe(event);
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mMainMediaPlayer != null)
            mMainMediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(presenter.isGameOver()) {
            presenter.startGame();
            valueAnimator.cancel();
            valueAnimator.start();

        } else {
            valueAnimator.cancel();
            valueAnimator.start();
        }

        if(mMainMediaPlayer != null)
            mMainMediaPlayer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if(presenter.isGameOver()) {
            presenter.startGame();
            valueAnimator.cancel();
            valueAnimator.start();

        } else {
            valueAnimator.cancel();
            valueAnimator.start();
        }

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
        lblScoreRight.setText("0");
        lytWall.getLayoutParams().height = 5;
        lytWall.requestLayout();
        startConveyorAnimation(finalPosition, animationDuration);
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
    public void setWallAlpha(double newAlpha) {
       //TODO: REMOVE IF NOT USED
      //  lytWall.getBackground().setAlpha( (int)newAlpha*100);
    }

    @Override
    public void playSound(Constants.SoundId type) {

        switch (type){
            case SWIPE_LEFT:
                mSwipeLeftMediaPlayer.start();
                break;
            case SWIPE_RIGHT:
                mSwipeRightMediaPlayer.start();
                break;
            case FAIL:
                mFailMediaPlayer.start();
                break;
            case GATE:
//                mGateClosingMediaPlayer.start();
                break;
            case GAME_OVER:
                mGameOverMediaPlayer.start();
                break;
        }
    }

    @Override
    public void finishGame() {

        Intent intent = new Intent(PlayActivity.this, GameOverActivity.class);
        intent.putExtra(SCORE_BUNDLE_KEY, lblScoreRight.getText());
        startActivity(intent);

        // TODO: ADD PLAY AGAING BUTTON
        presenter.startGame();
    }

    // INTERNAL METHODS

    public void startConveyorAnimation(final int distance, final int animationDuration){

        valueAnimator = ValueAnimator.ofFloat(-150,distance);
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
                        startConveyorAnimation(distance,presenter.getAnimationDuration());
                }

            }

        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(animationDuration);
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        didSwipe(event);
        return super.onTouchEvent(event);
    }

    private void didSwipe(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();

                if(x2 == x1)
                    break;

                if(x2 > x1)
                    presenter.moveRight();
                else
                    presenter.moveLeft();
                break;
        }
    }


//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
//    }

}
