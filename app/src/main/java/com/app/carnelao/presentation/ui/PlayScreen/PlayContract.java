package com.app.carnelao.presentation.ui.PlayScreen;

import android.content.Context;
import android.graphics.Bitmap;

import com.app.carnelao.util.Constants;

/**
 * Created by elder-dell on 2017-03-19.
 */

public interface PlayContract {

    interface View{

        void resetScreen(int finalPosition, int animationDuration);
        void updateFailsLabel(String score);
        void updateScoreLabel(String score);
        void moveItemRight(int distance, int animationDuration);
        void moveItemLeft(int distance, int animationDuration);
        void moveWallUp(int addValue);
        void setImageItem(int bitmap);
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
