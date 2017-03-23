package com.app.carnelao.presentation.ui.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.carnelao.model.Player;
import com.app.carnelao.util.Constants;

/**
 * Created by elder-dell on 2017-03-22.
 */

public class Util {

    public static void saveSharedPreference(String string, Context context){
        SharedPreferences sp = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.NAME_SHARED_PREF_KEY, string);
    }

    public static String getSharedPreferences(String nameSharedPrefKey,Context context) {
        SharedPreferences sp = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        return sp.getString(Constants.NAME_SHARED_PREF_KEY,null);
    }

    public static Player getPlayer() {

        Player player = null;

        //TODO:LOAD PLAYER FROM SHARED PREF
        // load player

        return player;
    }
}
