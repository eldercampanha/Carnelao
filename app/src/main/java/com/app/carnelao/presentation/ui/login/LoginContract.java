package com.app.carnelao.presentation.ui.login;

/**
 * Created by ElderCMA on 23/03/17.
 */

public interface LoginContract {


    interface View{

        void setPlayerNameText(String button);
        void setScoreText(String score);
        void hideRecordLabel();
    }

    interface Presenter{

        boolean checkUserExists();
        void attach(View view);
        void loadUser();
        void saveUser(String text);
    }
}
