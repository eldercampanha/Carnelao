package com.app.carnelao.presentation.ui.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.carnelao.R;
import com.app.carnelao.model.Player;
import com.app.carnelao.util.Constants;
import com.app.carnelao.util.SharedPreferencesUtil;

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
        String lastPlayerName = SharedPreferencesUtil.retrieveString(Constants.SHARED_PREF_KEY_LAST_PLAYER_NAME,mContext);
        String recordPlayerName = SharedPreferencesUtil.retrieveString(Constants.SHARED_PREF_KEY_NAME,mContext);
        String score = SharedPreferencesUtil.retrieveString(Constants.SHARED_PREF_KEY_SCORE, mContext);

        if(lastPlayerName != null ){
            mView.setPlayerNameText(lastPlayerName);
        }
        if(score != null && recordPlayerName != null) {
            mView.setScoreText( score+ "pts - "+ lastPlayerName) ;
        }  else {
            mView.hideRecordLabel();
        }

    }

    @Override
    public void saveUser(String playerName) {

        if(playerName == null || playerName.length() == 0)
            playerName = "Player";

        SharedPreferencesUtil.saveString(Constants.SHARED_PREF_KEY_LAST_PLAYER_NAME, playerName,mContext);
    }
}
