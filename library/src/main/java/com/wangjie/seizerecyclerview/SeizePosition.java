package com.wangjie.seizerecyclerview;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class SeizePosition implements Serializable{
    private int seizeAdapterIndex;
    private int position;
    private int subPosition;

    public SeizePosition(int seizeAdapterIndex, int position, int subPosition) {
        this.seizeAdapterIndex = seizeAdapterIndex;
        this.position = position;
        this.subPosition = subPosition;
    }

    public int getSeizeAdapterIndex() {
        return seizeAdapterIndex;
    }

    public void setSeizeAdapterIndex(int seizeAdapterIndex) {
        this.seizeAdapterIndex = seizeAdapterIndex;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSubPosition() {
        return subPosition;
    }

    public void setSubPosition(int subPosition) {
        this.subPosition = subPosition;
    }

    @Override
    public String toString() {
        return "SeizePosition{" +
                "seizeAdapterIndex=" + seizeAdapterIndex +
                ", position=" + position +
                ", subPosition=" + subPosition +
                '}';
    }
}
