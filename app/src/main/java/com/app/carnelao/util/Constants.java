package com.app.carnelao.util;

import com.app.carnelao.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elder-dell on 2017-03-19.
 */

public class Constants {

    public static String SCORE_BUNDLE_KEY = "SCORE_BUNDLE_KEY";

    private static Random rand;
    public static enum ItemType{
        CARNE,
        PAPELAO,
        OTHER
    }

    public static enum ValueAnimatorLevel {
        LEVEL_1(2300, 400),
        LEVEL_2(2000, 200),
        LEVEL_3(1600, 300),
        LEVEL_4(1300, 200),
        LEVEL_5(1100, 200);

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
