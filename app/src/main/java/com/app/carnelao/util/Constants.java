package com.app.carnelao.util;

import com.app.carnelao.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elder-dell on 2017-03-19.
 */

public class Constants {

    private static Random rand;
    public static enum ItemType{
        CARNE,
        PAPELAO,
        OTHER
    }

    public static enum ValueAnimatorLevel {
        LEVEL_1(2000, 400),
        LEVEL_2(1000, 200),
        LEVEL_3(500, 300),
        LEVEL_4(400, 200);


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
