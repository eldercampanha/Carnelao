package com.app.carnelao.presentation.ui.gameover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.carnelao.R;
import com.app.carnelao.util.Constants;
import com.app.carnelao.util.SharedPreferencesUtil;

import static com.app.carnelao.util.Constants.SCORE_BUNDLE_KEY;
import static com.app.carnelao.util.Constants.SHARED_PREF_KEY_LAST_PLAYER_NAME;
import static com.app.carnelao.util.Constants.SHARED_PREF_KEY_NAME;
import static com.app.carnelao.util.Constants.SHARED_PREF_KEY_SCORE;

public class GameOverActivity extends AppCompatActivity {

    private TextView lblScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        String score = getIntent().getExtras().getString(SCORE_BUNDLE_KEY);
        lblScore  = (TextView) findViewById(R.id.lbl_score);

        calculateScore(score);
        lblScore.setText(score);

        // TODO: SEND TO PRESENTER

    }

    private void calculateScore(String newScore) {

        String lastScore = SharedPreferencesUtil.retrieveString(SHARED_PREF_KEY_SCORE, this);

        if(lastScore != null){

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
}
