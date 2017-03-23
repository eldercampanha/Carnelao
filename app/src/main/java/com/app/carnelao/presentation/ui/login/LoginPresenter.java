package com.app.carnelao.presentation.ui.login;

import android.content.Context;

import com.app.carnelao.R;
import com.app.carnelao.model.Player;
import com.app.carnelao.presentation.ui.Util.Util;

/**
 * Created by ElderCMA on 23/03/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private Context mContext;
    private Player mPlayer;

    public LoginPresenter(Context context){
        mContext = context;
    }

    @Override
    public boolean checkUserExists() {
        return mPlayer != null;
    }

    @Override
    public void attach(LoginContract.View view) {
        mView = view;
    }


    @Override
    public void loadUser() {

        // load player from Shared Preferences
        mPlayer = Util.getPlayer();

        if(mPlayer != null){

            mView.setButtonText(mContext.getString(R.string.keep_playing));
            mView.setScoreText("RECORD: "+ mPlayer.getScore());

        } else {

            mView.setButtonText(mContext.getString(R.string.play));
            mView.setScoreText("");
        }

    }
}
