package com.app.carnelao.presentation.ui.PlayScreen;

import android.content.Context;

import com.app.carnelao.util.Constants;

/**
 * Created by elder-dell on 2017-03-19.
 */

public interface PlayContract {

    interface View{

        void startGame(Constants.ValueAnimatorLevel level);
        void updateLeftScoreLabel(String score);
        void updateRightScoreLabel(String score);
        void moveRight(int distance, int animationDuration);
        void moveLeft (int distance, int animationDuration);
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
