package com.app.carnelao.presentation.ui.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import com.app.carnelao.util.Constants;

/**
 * Created by elder-dell on 2017-03-23.
 */

public class TextUtils {


    public static void setFont(Button button, Constants.Fonts font, Context context){

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),font.getPath());
        button.setTypeface(typeFace);
        button.setTextSize(font.getSize());

    }

    public static void setFont(TextView textView, Constants.Fonts font, Context context){

        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),font.getPath());
        textView.setTypeface(typeFace);
        textView.setTextSize(font.getSize());
    }
}
