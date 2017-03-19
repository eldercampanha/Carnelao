package com.app.carnelao.presentation.ui.PlayScreen;

import android.content.Context;

import com.app.carnelao.util.Constants;

/**
 * Created by elder-dell on 2017-03-19.
 */

public interface PlayContract {

    interface View{

        void startGame(int finalPosition, int animationDuration);
        void updateLeftScoreLabel(String score);
        void updateRightScoreLabel(String score);
        void moveRight(int distance, int animationDuration);
        void moveLeft (int distance, int animationDuration);
        void moveWallUp(int newHeight);
    }

    interface Presenter{
        void attach(View view);
        void setContext(Context context);
        void hitRightSide();
        void hitLeftSide();
        void moveRight();
        void moveLeft();
        void startGame();
    }

}
