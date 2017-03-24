package com.app.carnelao.presentation.ui.pause;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.carnelao.R;
import com.app.carnelao.presentation.ui.login.LoginActivity;
import com.app.carnelao.util.Constants;
import com.app.carnelao.presentation.ui.helper.TextUtils;

public class PauseActivity extends AppCompatActivity {

    private TextView lblPause;
    private Button btnBack;
    private Button btnContinue;
    private Button btnExit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);


        lblPause = (TextView)findViewById(R.id.lbl_pause);
        btnBack = (Button) findViewById(R.id.btn_back);
        btnContinue = (Button)findViewById(R.id.btn_continue);
        btnExit = (Button)findViewById(R.id.btn_exit);

        // fonts
        setFonts();
//        TextView myTextView=(TextView)findViewById(R.id.textBox);
//        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/mytruetypefont.ttf");
//        myTextView.setTypeface(typeFace);
    }

    private void setFonts() {
        TextUtils.setFont(lblPause, Constants.Fonts.TITLE_FONT, this);
        TextUtils.setFont(btnBack, Constants.Fonts.BUTTON_FONT, this);
        TextUtils.setFont(btnContinue, Constants.Fonts.BUTTON_FONT, this);
        TextUtils.setFont(btnExit, Constants.Fonts.BUTTON_FONT, this);
    }

    public void btnBackClicked(View button){
        Intent intent = new Intent(PauseActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnExitClicked(View button){
        System.exit(0);
    }

    public void btnContinueClicked(View button){
        finish();
    }

}
