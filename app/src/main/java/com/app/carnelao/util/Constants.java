package com.app.carnelao.util;

import com.app.carnelao.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elder-dell on 2017-03-19.
 */

public class Constants {

    public static String NAME_SHARED_PREF_KEY = "NAME_SHARED_PREF_KEY";
    public static String SCORE_BUNDLE_KEY = "SCORE_BUNDLE_KEY";
    public static String NAME_BUNDLE_KEY = "NAME_BUNDLE_KEY";

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
        LEVEL_1(2300, 400),
        LEVEL_2(2100, 200),
        LEVEL_3(1900, 300),
        LEVEL_4(1700, 400),
        LEVEL_5(1500, 200),
        LEVEL_6(1300, 300),
        LEVEL_7(1100, 200),
        LEVEL_8(1000, 200);

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
                R.drawable.ic_burial_flat_icon,
                R.drawable.ic_gun_flat_icon,
                R.drawable.ic_poison_flat_icon,
                R.drawable.ic_scissors_flat_icon
        };

        int index = 0;

        if(rand == null) {
            rand = new Random();
        }
        index = (int)rand.nextInt(imagesIdVector.length);

        return imagesIdVector[index];
    }

}
