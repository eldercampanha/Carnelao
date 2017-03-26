package com.app.carnelao.presentation.ui.playscreen;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.app.carnelao.R;
import com.app.carnelao.model.Item;
import com.app.carnelao.util.SharedPreferencesUtil;
import com.app.carnelao.util.Constants;
import java.util.Random;

import static com.app.carnelao.util.Constants.*;

/**
 * Created by elder-dell on 2017-03-19.
 */

public class PlayPresenter implements PlayContract.Presenter{


    private static final int COUNTDOWN_INITIAL_TIME = 75000;
    private String TAG = "PLAY_PRESENTER";
    private int wallIncreaseValue;
    private final int IMAGE_WIDTH = 120;
    private final int IMAGE_HEIGHT = 70;
    private final int NAV_BAR_HEIGHT = 100;

    private Context mContext;
    private int mCountRight = 0;
    private boolean touchTheBotton = false;
    private boolean gameOver = false;
    private int mWallHeight;
    private int screenWidth;
    private int screenHeight;
    private ValueAnimatorLevel mCurrentLevel;
    private PlayContract.View mView;
    private Item mItem;
    private Random mRand;
    private boolean isMoving;
    private CountDownTimer mTimer;
    private boolean isPaused;
    private long mLastTimeState;


    @Override
    public void setContext(Context context) {

        if(context != null) {

            mContext = context;

            // used to the animation Right-to-Left and Left-to-Botton
            screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

            // used to the animation Top-to-Bottom and Verify the "height of the Wall"
            screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;

            // used to calculate how much the wall's height should be increased (15%)
            wallIncreaseValue = (int) (screenHeight * 0.07);

            // set up timer
            if(mTimer == null)
                createNewCountdownTimer(COUNTDOWN_INITIAL_TIME);
        }
    }

    @Override
    public void attach(PlayContract.View view) {
        mView = view;
        mCurrentLevel = ValueAnimatorLevel.LEVEL_1;
    }

    // GAME METHODS

    @Override
    public void startGame() {

        Log.i(TAG,"start game");

        // used to block all methods
        this.gameOver = false;

        // reset game variables
        mWallHeight = 5;
        mCountRight = 0;
        isMoving = false;
        isPaused = false;

        mCurrentLevel = ValueAnimatorLevel.LEVEL_1;
        mTimer.start();

        mView.playSound(SoundId.CONVEYOR);

        // start conveyor_anim
        mView.resetScreen(screenHeight, mCurrentLevel.getUpToDownTime());
    }

    @Override
    public void hitRightSide() {

        if(this.gameOver) return;

        // DID SCORE_BUNDLE_KEY
        if(mItem.getType() == ItemType.CARNE
                || mItem.getType() == ItemType.PAPELAO){

            mCountRight++;
            mView.updateScoreLabel("" + mCountRight);
        }

        // DID MISS
        if(mItem.getType() == ItemType.OTHER){

            // wall should move up
            this.touchTheBottom();
            mView.playSound(SoundId.FAIL);
        }
    }

    @Override
    public void hitLeftSide() {
        if(this.gameOver) return;

        // DID SCORE_BUNDLE_KEY
        if(mItem.getType() == ItemType.OTHER){
            mCountRight++;

            mView.updateScoreLabel("" + mCountRight);
        }

        // DID MISS
        if(mItem.getType() == ItemType.CARNE
                || mItem.getType() == ItemType.PAPELAO){

            // wall should move up
            this.touchTheBottom();
            mView.playSound(SoundId.FAIL);
        }
    }

    @Override
    public void moveRight() {
        if(this.gameOver) return;

        if(!isMoving) {
            mView.playSound(SoundId.SWIPE_RIGHT);
            int distance = screenWidth / 2 + IMAGE_WIDTH;
            mView.moveItemRight(distance, mCurrentLevel.getSideWaysTime());
            isMoving = true;
        }
    }

    @Override
    public void moveLeft() {
        if(this.gameOver) return;

        if(!isMoving) {
            mView.playSound(SoundId.SWIPE_LEFT);

            int distance = screenWidth / 2 + IMAGE_WIDTH;

            // right to left, so the distance should be negative
            distance = -1 * distance;

            mView.moveItemLeft(distance, mCurrentLevel.getSideWaysTime());
            isMoving = true;
        }
    }

    public void hitBottom(){
        if(this.gameOver) return;

        // variable used for verify if the wall height has crossed the limit
        mWallHeight +=wallIncreaseValue;

        // end game if the Wall Height is bigger then 80% of the screen
        if(mWallHeight >= (int)screenHeight*0.8) {
            mView.playSound(SoundId.GAME_OVER);
            mView.setWallAlpha(1);
            mView.finishGame();
            gameOver = true;
            mTimer.cancel();
            mTimer.onFinish();
        }

        mView.playSound(SoundId.GATE);
        mView.moveWallUp(wallIncreaseValue);
    }

    @Override
    public void touchTheBottom() {
        hitBottom();
    }

    @Override
    public void newCicleStarted() {
        if(this.gameOver || this.isPaused) return;

        // move the wall up if the image touch the botton of the screen
        if(touchTheBotton)
            this.touchTheBottom();

        if(mItem == null || mRand == null) {
            mItem = new Item();
            mRand = new Random();
        }

        int type = (int)mRand.nextInt(3);

        switch (type){

            case 0: // CARNE
                mItem.setType(ItemType.CARNE);
                mItem.setImageResourceId(R.drawable.carnelao);
                break;

            case 1: // PAPELAO
                mItem.setType(ItemType.PAPELAO);
                mItem.setImageResourceId(R.drawable.ic_box_flat_icon);
                break;

            default: // OTHER
                mItem.setType(ItemType.OTHER);
                mItem.setImageResourceId(Constants.getRamdomImageId());
        }

        // isMoving is false only when the user didn't press any button
        if(!isMoving)
            hitBottom();

        this.isMoving = false;
        mView.setImageItem(mItem.getImageResourceId());
    }

    @Override
    public int getAnimationDuration() {
        return mCurrentLevel.getUpToDownTime();
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void finishGame() {

        // reset game variables
        mWallHeight = 5;
        mCountRight = 0;
        isMoving = false;
        gameOver = false;
        isPaused = false;
        mCurrentLevel = ValueAnimatorLevel.LEVEL_1;
    }

    @Override
    public void setMuteState(boolean isMute) {

        SharedPreferencesUtil.saveBoolean(SHARED_PREF_KEY_MUTE, isMute, mContext);

        if (isMute) {
            mView.updateMuteButton(R.drawable.ic_speaker_mute);
        } else {
            mView.updateMuteButton(R.drawable.ic_speaker);
        }
    }

    @Override
    public void pause() {
        Log.i(TAG,"pause");
        isPaused = true;
        mTimer.cancel();
        mTimer.onFinish();
    }

    @Override
    public void resume() {
        Log.i(TAG,"resume");

        if(isPaused){
            Log.i(TAG, "last state " + mLastTimeState);
            isPaused = false;
            createNewCountdownTimer(mLastTimeState);
            mTimer.start();
        }
    }

    private void createNewCountdownTimer(long lastTimeState) {

        mTimer = new CountDownTimer(lastTimeState, 1000) {
            int time;

            @Override
            public void onTick(long millisUntilFinished) {


                time = ((int) millisUntilFinished / 1000);
                mLastTimeState = millisUntilFinished;

                // used to save the time when the game is paused
                if (!isPaused) {

                    switch (time) {
                        case 70:
                            mCurrentLevel = ValueAnimatorLevel.LEVEL_2;
                            Log.i(TAG, "70");
                            break;
                        case 60:
                            mCurrentLevel = ValueAnimatorLevel.LEVEL_3;
                            Log.i(TAG, "60");
                            break;
                        case 50:
                            mCurrentLevel = ValueAnimatorLevel.LEVEL_4;
                            Log.i(TAG, "50");
                            break;
                        case 40:
                            mCurrentLevel = ValueAnimatorLevel.LEVEL_5;
                            Log.i(TAG, "40");
                            break;
                        case 30:
                            mCurrentLevel = ValueAnimatorLevel.LEVEL_6;
                            Log.i(TAG, "30");
                            break;
                        case 20:
                            mCurrentLevel = ValueAnimatorLevel.LEVEL_7;
                            Log.i(TAG, "20");
                            break;
                        case 10:
                            mCurrentLevel = ValueAnimatorLevel.LEVEL_8;
                            Log.i(TAG, "10");
                            break;
                    }

                }
            }

            @Override
            public void onFinish() {

            }

        };
    }
}
