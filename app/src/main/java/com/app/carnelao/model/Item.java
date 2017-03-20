package com.app.carnelao.model;

import android.graphics.Bitmap;

import com.app.carnelao.util.Constants;

/**
 * Created by ElderCMA on 20/03/17.
 */

public class Item {

    public Constants.ItemType getType() {
        return type;
    }

    public void setType(Constants.ItemType type) {
        this.type = type;
    }


    private Constants.ItemType type;

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    private int imageResourceId;
}
