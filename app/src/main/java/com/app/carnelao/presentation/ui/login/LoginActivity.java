package com.app.carnelao.presentation.ui.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.carnelao.R;
import com.app.carnelao.presentation.ui.Util.Util;
import com.app.carnelao.presentation.ui.playscreen.PlayActivity;
import com.app.carnelao.util.Constants;

public class LoginActivity extends AppCompatActivity {

    private Button mPlayButton;
    private View decorView;
    private String mName;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        decorView = getWindow().getDecorView();
        mPlayButton = (Button)findViewById(R.id.btn_play);

        getSupportActionBar().hide();
     //   loadUserName();
    }

    private void loadUserName() {

        mName = Util.getSharedPreferences(Constants.NAME_SHARED_PREF_KEY, this);
        if(mName != null){
            mPlayButton.setText("Continuar como "+mName);
        }
    }


    public void btnPlayClicked(View button){

        if(mName == null){
            showDialog();
        } else {
            startActivity(intent);
        }
    }

    private void showDialog() {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Digite seu Nome:");
        final EditText input = new EditText(this);
        b.setView(input);
        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(mName == null) mName = "Player";
//                Util.saveSharedPreference(mName,getApplicationContext());
                intent = new Intent(LoginActivity.this, PlayActivity.class);
                intent.putExtra(Constants.NAME_BUNDLE_KEY, mName);
                startActivity(intent);
            }
        });
        b.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mName = "Player";
                intent = new Intent(LoginActivity.this, PlayActivity.class);
                intent.putExtra(Constants.NAME_BUNDLE_KEY, mName);
                startActivity(intent);
            }
        });
        b.show();
    }

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
}
