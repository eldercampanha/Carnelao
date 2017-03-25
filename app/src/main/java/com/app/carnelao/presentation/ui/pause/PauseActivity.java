package com.app.carnelao.presentation.ui.pause;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.carnelao.R;
import com.app.carnelao.presentation.ui.login.LoginActivity;
import com.app.carnelao.presentation.ui.playscreen.PlayActivity;
import com.app.carnelao.util.Constants;
import com.app.carnelao.presentation.ui.helper.TextUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PauseActivity extends AppCompatActivity {

    private TextView lblPause;
    private Button btnBack;
    private Button btnContinue;
    private Button btnExit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        // AdMod
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        lblPause = (TextView)findViewById(R.id.lbl_pause);
        btnBack = (Button) findViewById(R.id.btn_restart);
        btnContinue = (Button)findViewById(R.id.btn_play_again);
        btnExit = (Button)findViewById(R.id.btn_exit);

        // fonts
        setFonts();

    }

    private void setFonts() {
        TextUtils.setFont(lblPause, Constants.Fonts.TITLE_FONT, this);
        TextUtils.setFont(btnBack, Constants.Fonts.BUTTON_FONT, this);
        TextUtils.setFont(btnContinue, Constants.Fonts.BUTTON_FONT, this);
        TextUtils.setFont(btnExit, Constants.Fonts.BUTTON_FONT, this);
    }

    public void btnRestartClicked(View button){
        Intent intent = new Intent(PauseActivity.this,
                PlayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void btnExitClicked(View button){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void btnContinueClicked(View button){
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
