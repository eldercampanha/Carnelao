package com.app.carnelao.presentation.ui.login;

/**
 * Created by ElderCMA on 23/03/17.
 */

public interface LoginContract {


    interface View{

        void setButtonText(String button);
        void setScoreText(String score);
    }

    interface Presenter{

        boolean checkUserExists();
        void attach(View view);
        void loadUser();
    }
}
