package com.app.carnelao.presentation.ui.gameover;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.carnelao.R;
import com.app.carnelao.presentation.ui.helper.TextUtils;
import com.app.carnelao.presentation.ui.login.LoginActivity;
import com.app.carnelao.presentation.ui.playscreen.PlayActivity;
import com.app.carnelao.util.Constants;
import com.app.carnelao.util.SharedPreferencesUtil;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.app.carnelao.util.Constants.SCORE_BUNDLE_KEY;
import static com.app.carnelao.util.Constants.SHARED_PREF_KEY_LAST_PLAYER_NAME;
import static com.app.carnelao.util.Constants.SHARED_PREF_KEY_NAME;
import static com.app.carnelao.util.Constants.SHARED_PREF_KEY_SCORE;

public class GameOverActivity extends AppCompatActivity {

    private TextView lblScore;
    private TextView lblScoreTitle;
    private TextView lblHighestScore;
    private TextView lblHighestScoreTitle;
    private TextView lblTitle;
    private Button btnExit;
    private Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);



        String score = getIntent().getExtras().getString(SCORE_BUNDLE_KEY);
        lblScore  = (TextView) findViewById(R.id.lbl_score);
        lblScoreTitle = (TextView) findViewById(R.id.lbl_score_title);
        lblHighestScore = (TextView) findViewById(R.id.lbl_score_highest);
        lblHighestScoreTitle = (TextView) findViewById(R.id.lbl_score_highest_title);
        lblTitle  = (TextView) findViewById(R.id.lbl_title);
        btnExit = (Button) findViewById(R.id.btn_exit);
        btnPlayAgain = (Button) findViewById(R.id.btn_play_again);

        calculateScore(score);
        lblScore.setText(score);

        //FONT
        TextUtils.setFont(lblScore, Constants.Fonts.BUTTON_FONT, this);
        TextUtils.setFont(lblTitle, Constants.Fonts.TITLE_FONT, this);
        TextUtils.setFont(lblHighestScore, Constants.Fonts.GAME_OVER_SCORE, this);
        TextUtils.setFont(lblHighestScoreTitle, Constants.Fonts.GAME_OVER_SCORE_TITLE, this);
        TextUtils.setFont(lblScore, Constants.Fonts.GAME_OVER_SCORE, this);
        TextUtils.setFont(lblScoreTitle, Constants.Fonts.GAME_OVER_SCORE_TITLE, this);

        TextUtils.setFont(btnExit, Constants.Fonts.BUTTON_FONT, this);
        TextUtils.setFont(btnPlayAgain, Constants.Fonts.BUTTON_FONT, this);


        // AdMod
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void calculateScore(String newScore) {

        String lastScore = SharedPreferencesUtil.retrieveString(SHARED_PREF_KEY_SCORE, this);

        if(lastScore != null){

            lblHighestScore.setText(lastScore);

            int iLastScore = Integer.valueOf(lastScore);
            int iNewScore = Integer.valueOf(newScore);

            if(iNewScore > iLastScore){
                saveNewRecord(newScore);
            }

        } else {
            saveNewRecord(newScore);
        }

    }

    private void saveNewRecord(String score) {

        // get current player name
        String newName  = SharedPreferencesUtil.retrieveString(SHARED_PREF_KEY_LAST_PLAYER_NAME, this);

        // save new record player name and score
        SharedPreferencesUtil.saveString(SHARED_PREF_KEY_NAME,newName, this);
        SharedPreferencesUtil.saveString(SHARED_PREF_KEY_SCORE,score, this);
    }

    public void btnPlayAgainClicked(View button){
        finish();
    }

    public void btnExitClicked(View button){
        Intent intent = new Intent(GameOverActivity.this,
                LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameOverActivity.this,
                LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
