package com.app.carnelao.model;

/**
 * Created by ElderCMA on 23/03/17.
 */

public class Player {

    private String name;
    private String score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public int getScoreIntValue() {
        return Integer.valueOf(score);
    }


    public void setScore(String score) {
        this.score = score;
    }
}
