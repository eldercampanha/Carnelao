package com.app.carnelao.presentation.ui.playscreen;

import android.content.Context;

import com.app.carnelao.util.Constants;

/**
 * Created by elder-dell on 2017-03-19.
 */

public interface PlayContract {

    interface View{

        void resetScreen(int finalPosition, int animationDuration);
        void updateScoreLabel(String score);
        void moveItemRight(int distance, int animationDuration);
        void moveItemLeft(int distance, int animationDuration);
        void moveWallUp(int addValue);
        void setImageItem(int bitmap);
        void setWallAlpha(double newAlpha);
        void playSound(Constants.SoundId id);
        void finishGame();
    }

    interface Presenter{
        void attach(View view);
        void setContext(Context context);
        void hitRightSide();
        void hitLeftSide();
        void moveRight();
        void moveLeft();
        void startGame();
        void newCicleStarted();
        void touchTheBottom();
        int getAnimationDuration();
        boolean isGameOver();
    }

}
