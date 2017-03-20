package com.app.carnelao.presentation.ui.PlayScreen;

import android.content.Context;
import com.app.carnelao.R;
import com.app.carnelao.model.Item;
import com.app.carnelao.util.Constants;
import java.util.Random;

import static com.app.carnelao.util.Constants.*;

/**
 * Created by elder-dell on 2017-03-19.
 */

public class PlayPresenter implements PlayContract.Presenter{


    private final int WALL_UP_VALUE = 50;
    private final int IMAGE_WIDTH = 120;
    private final int IMAGE_HEIGHT = 70;
    private final int NAV_BAR_HEIGHT = 100;

    private ValueAnimatorLevel mCurrentLevel;
    private PlayContract.View mView;
    private Context mContext;
    private int countRight = 0;
    private int countLeft = 0;
    private boolean touchTheBotton = false;
    private int wallHeight;
    private int screenWidth;
    private int screenHeight;
    private Item mItem;
    private Random mRand;
    private String TAG = "PLAY_PRESENTER";

    @Override
    public void setContext(Context context) {
        mContext = context;

        // used to the animation Right-to-Left and Left-to-Botton
        screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        // used to the animation Top-to-Bottom and Verify the "height of the Wall"
        screenHeight = mContext.getResources().getDisplayMetrics().heightPixels - IMAGE_HEIGHT - NAV_BAR_HEIGHT;
    }

    @Override
    public void attach(PlayContract.View view) {
        mView = view;
        mCurrentLevel = ValueAnimatorLevel.LEVEL_3;
    }

    @Override
    public void hitRightSide() {

        // DID SCORE
        if(mItem.getType() == ItemType.CARNE
                || mItem.getType() == ItemType.PAPELAO){

            countRight++;
            mView.updateScoreLabel("" + countRight);
        }

        // DID MISS
        if(mItem.getType() == ItemType.OTHER){
            countLeft++;
            mView.updateFailsLabel("" + countLeft);

            // wall should move up
            this.moveWallUp();
        }
    }

    @Override
    public void hitLeftSide() {

        // DID SCORE
        if(mItem.getType() == ItemType.OTHER){

            countRight++;
            mView.updateScoreLabel("" + countRight);
        }

        // DID MISS
        if(mItem.getType() == ItemType.OTHER){
            countLeft++;
            mView.updateFailsLabel("" + countLeft);

            // wall should move up
            mView.moveWallUp(WALL_UP_VALUE);
        }
    }

    @Override
    public void moveRight() {

        int finalPosition = screenWidth/2 + IMAGE_WIDTH;
        mView.moveItemRight(finalPosition, mCurrentLevel.getSideWaysTime());
    }

    @Override
    public void moveLeft() {

        int distance =  screenWidth/2 + IMAGE_WIDTH;

        // right to left, so the distance should be negative
        distance = -1 * distance;

        mView.moveItemLeft(distance, mCurrentLevel.getSideWaysTime());
    }

    @Override
    public void startGame() {
        mView.resetScreen(screenHeight, mCurrentLevel.getUpToDownTime());
    }

    @Override
    public void setNewObject() {

        //TODO: FIX LOGIG
        // move the wall up if the image touch the botton of the screen
        if(touchTheBotton)
            this.moveWallUp();

        if(mItem == null || mRand == null) {
            mItem = new Item();
            mRand = new Random(ItemType.values().length);
        }

        int type = (int)mRand.nextInt(3);


        switch (type){

            case 0: // CARNE
                mItem.setType(ItemType.CARNE);
                mItem.setImageResourceId(R.drawable.ic_beef_flat_icon);
                break;

            case 1: // PAPELAO
                mItem.setType(ItemType.PAPELAO);
                mItem.setImageResourceId(R.drawable.ic_box_flat_icon);
                break;

            default: // OTHER
                mItem.setType(ItemType.OTHER);
                mItem.setImageResourceId(Constants.getRamdomImageId());
        }

        mView.setImageItem(mItem.getImageResourceId());
    }

    public void moveWallUp(){

        // variable used for verify if the wall height has crossed the limit
        wallHeight =+ WALL_UP_VALUE;

        // end game if the Wall Height is bigger then 70% of the screen
        if(wallHeight >= (screenHeight * 0.7))
            startGame();


        mView.moveWallUp(WALL_UP_VALUE);
    }
}
