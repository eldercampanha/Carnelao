package com.app.carnelao.presentation.ui.PlayScreen;

import android.content.Context;
import android.view.Display;

import com.app.carnelao.util.Constants;

/**
 * Created by elder-dell on 2017-03-19.
 */

public class PlayPresenter implements PlayContract.Presenter{

    private PlayContract.View mView;
    private Context mContext;
    private int countRight = 0;
    private int countLeft = 0;
    private boolean isMoving;

    private int IMAGE_WIDTH = 70;

    private Constants.ValueAnimatorLevel mCurrentLevel;

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void attach(PlayContract.View view) {
        mView = view;
        mCurrentLevel = Constants.ValueAnimatorLevel.LEVEL_1;
    }

    @Override
    public void hitRightSide() {
        countRight++;
        isMoving = false;
        mView.updateRightScoreLabel("" + countRight);
    }

    @Override
    public void hitLeftSide() {
        countLeft++;
        isMoving = false;
        mView.updateLeftScoreLabel("" + countLeft);
    }

    @Override
    public void moveRight() {
        isMoving = true;
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        int finalPosition = screenWidth/2 + IMAGE_WIDTH;
        mView.moveRight(finalPosition, mCurrentLevel.getSideWaysTime());
    }

    @Override
    public void moveLeft() {

        isMoving = true;

        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        int distance =  screenWidth/2 + IMAGE_WIDTH;

        // right to left, so the distance should be negative
        distance = -1 * distance;

        mView.moveLeft(distance, mCurrentLevel.getSideWaysTime());
    }

    @Override
    public void startGame() {
        mView.startGame(mCurrentLevel);
    }
}
