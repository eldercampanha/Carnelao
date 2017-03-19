package com.app.carnelao.util;

/**
 * Created by elder-dell on 2017-03-19.
 */

public class Constants {

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
}
