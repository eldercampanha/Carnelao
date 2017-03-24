package com.app.carnelao.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.carnelao.model.Player;
import com.app.carnelao.util.Constants;

import static com.app.carnelao.util.Constants.SHARE_PREF_KEY;

/**
 * Created by elder-dell on 2017-03-22.
 */

public class SharedPreferencesUtil {

    public static void saveString(String key, String value, Context context){
        SharedPreferences sp = context.getSharedPreferences(SHARE_PREF_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String retrieveString(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        return sp.getString(key,null);
    }

    public static Player getPlayer() {

        Player player = null;

        //TODO:LOAD PLAYER FROM SHARED PREF
        // load player

        return player;
    }

    public static void saveBoolean(String key, boolean isMute, Context context) {

        SharedPreferences sp = context.getSharedPreferences(SHARE_PREF_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,isMute);
        editor.commit();

    }

    public static boolean retrieveBoolean(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

}
