package com.app.carnelao.presentation.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.carnelao.R;
import com.app.carnelao.presentation.ui.playscreen.PlayActivity;
import com.app.carnelao.util.Constants;
import com.app.carnelao.presentation.ui.helper.TextUtils;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private Button mPlayButton;
    private View decorView;
    private TextView txtName;
    private Intent intent;
    private TextView mScoreLabel;
    private TextView mPlayerName;
    private ImageView imgMedal;
    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        decorView = getWindow().getDecorView();

        // terminate application
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
        }

        mPlayButton = (Button)findViewById(R.id.btn_play);
        mScoreLabel = (TextView) findViewById(R.id.lbl_score);
        txtName = (TextView) findViewById(R.id.txt_name);
        imgMedal = (ImageView)findViewById(R.id.img_medal);

        // FONT
        TextUtils.setFont(mPlayButton, Constants.Fonts.BUTTON_FONT, this);

        // PRESENTER
        mPresenter = new LoginPresenter(getApplication());
        mPresenter.attach(this);

    }


    @Override
    protected void onResume() {
        mPresenter.loadUser();
        super.onResume();
    }

    public void btnPlayClicked(View button){
        mPresenter.saveUser(txtName.getText().toString());
        openPlayScreen();
    }

    private void openPlayScreen() {

        //TODO: CHECK IF THE USER CHANGED THE NAME
        Intent intent = new Intent(LoginActivity.this, PlayActivity.class);
        startActivity(intent);
    }

//    private void showDialog() {
//
//        AlertDialog.Builder b = new AlertDialog.Builder(this);
//        b.setTitle("Digite seu Nome:");
//        final EditText input = new EditText(this);
//        b.setView(input);
//        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                if(mName == null) mName = "Player";
////                SharedPreferencesUtil.save(mName,getApplicationContext());
//                intent = new Intent(LoginActivity.this, PlayActivity.class);
//                intent.putExtra(Constants.NAME_BUNDLE_KEY, mName);
//                startActivity(intent);
//            }
//        });
//        b.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mName = "Player";
//                intent = new Intent(LoginActivity.this, PlayActivity.class);
//                intent.putExtra(Constants.NAME_BUNDLE_KEY, mName);
//                startActivity(intent);
//            }
//        });
//        b.show();
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public void setPlayerNameText(String name) {
        txtName.setText(name);
    }

    @Override
    public void setScoreText(String score) {
        mScoreLabel.setText(score);
    }

    @Override
    public void hideRecordLabel() {
        imgMedal.setVisibility(View.INVISIBLE);
    }

}
