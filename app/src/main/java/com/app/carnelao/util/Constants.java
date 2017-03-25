package com.app.carnelao.util;

import com.app.carnelao.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elder-dell on 2017-03-19.
 */

public class Constants {

    // SHARED PREFERENCES
    public static final String SHARED_PREF_KEY_MUTE = "SHARED_PREF_KEY_MUTE";
    public static final String SHARED_PREF_KEY_LAST_PLAYER_NAME ="SHARED_PREF_KEY_LAST_PLAYER_NAME";
    public static final String SHARED_PREF_KEY_SCORE = "SHARED_PREF_KEY_SCORE";
    public static final String SHARED_PREF_KEY_NAME = "SHARED_PREF_KEY_NAME";
    public static final String SCORE_BUNDLE_KEY = "SCORE_BUNDLE_KEY";
    public static final String SHARE_PREF_KEY = "SHARE_PREF_KEY_azd_095";

    private static Random rand;
    public static enum ItemType{
        CARNE,
        PAPELAO,
        OTHER
    }

    public static enum SoundId {
        CONVEYOR(R.raw.esteira),
        SWIPE_LEFT(R.raw.swipe_left),
        SWIPE_RIGHT(R.raw.swipe_right),
        FAIL(R.raw.error),
        GATE(R.raw.som_fechando_portao),
        GAME_OVER(R.raw.som_fechando_portao);

        private final int soundId;

        SoundId(int soundId) {
            this.soundId = soundId;
        }

        public int getSoundId() {
            return soundId;
        }
    }

    public static enum ValueAnimatorLevel {
        LEVEL_1(2200, 400),
        LEVEL_2(2000, 200),
        LEVEL_3(1800, 300),
        LEVEL_4(1600, 400),
        LEVEL_5(1500, 200),
        LEVEL_6(1200, 300),
        LEVEL_7(1000, 200),
        LEVEL_8(900, 200);

        private final int upToDownTime;
        private final int sideWaysTime;

        ValueAnimatorLevel(int upToDownTime, int sideWaysTime) {
            this.upToDownTime = upToDownTime;
            this.sideWaysTime = sideWaysTime;
        }

        public int getUpToDownTime() {
            return upToDownTime;
        }

        public int getSideWaysTime() {
            return sideWaysTime;
        }
    }

    public static int getRamdomImageId() {

        int[] imagesIdVector = new int[]{
                R.drawable.ic_poison,
                R.drawable.ic_rat_silhouette,
                R.drawable.ic_revolver,
                R.drawable.ic_scissors_flat_icon
        };

        int index = 0;

        if(rand == null) {
            rand = new Random();
        }
        index = (int)rand.nextInt(imagesIdVector.length);

        return imagesIdVector[index];
    }

    public static enum Fonts{
        BUTTON_FONT("font_zig.ttf", 30),
        TITLE_FONT("font_zig.ttf", 50),
        GAME_OVER_SCORE_TITLE("font_zig.ttf",16),
        GAME_OVER_SCORE("font_zig.ttf", 40);

        private final String path;
        private final int size;

        Fonts(String path, int size) {
            this.size = size;
            this.path = path;
        }

        public String getPath() {
            return path;
        }
        public int getSize(){ return size; };
    }

}
