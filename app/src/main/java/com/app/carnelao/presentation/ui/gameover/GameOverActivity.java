package com.app.carnelao.presentation.ui.gameover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.carnelao.R;

import static com.app.carnelao.util.Constants.SCORE_BUNDLE_KEY;

public class GameOverActivity extends AppCompatActivity {

    private TextView lblScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        String score = (String)getIntent().getExtras().get(SCORE_BUNDLE_KEY);
        lblScore  = (TextView) findViewById(R.id.lbl_score);

        if(score != null)
            lblScore.setText(score);
    }

    public void btnPlayAgainClicked(View button){
        finish();
    }
}
