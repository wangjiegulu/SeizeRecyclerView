package com.wangjie.seizerecyclerview;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class SeizePosition implements Serializable {

    /**
     * index of seizeAdapter in parentAdapter,from top to bottom ,from left to right
     */
    private int seizeAdapterIndex;
    /**
     * position of item in recyclerview
     */
    private int position;
    /**
     * position in parentAdapter not include headerView or footerView
     * just is a data position in parentAdapter
     */
    private int sourcePosition;
    /**
     * item position in seizeAdapter ,include headerView,footerView in seizeAdapter
     * this value of headerView is 0 if seizeAdapter has a headerView
     */
    private int subPosition;
    /**
     * item position in seizeAdapter ,not include headerView,footerView in seizeAdapter
     * this value of data is 0 ,weather a seizeAdapter has a headerView
     */
    private int subSourcePosition;

    public SeizePosition(int seizeAdapterIndex, int position, int sourcePosition, int subPosition, int subSourcePosition) {
        this.seizeAdapterIndex = seizeAdapterIndex;
        this.position = position;
        this.sourcePosition = sourcePosition;
        this.subPosition = subPosition;
        this.subSourcePosition = subSourcePosition;
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

    public int getSubSourcePosition() {
        return subSourcePosition;
    }

    public void setSubSourcePosition(int subSourcePosition) {
        this.subSourcePosition = subSourcePosition;
    }

    public int getSourcePosition() {
        return sourcePosition;
    }

    public void setSourcePosition(int sourcePosition) {
        this.sourcePosition = sourcePosition;
    }

    @Override
    public String toString() {
        return "SeizePosition{" +
                "seizeAdapterIndex=" + seizeAdapterIndex +
                ", position=" + position +
                ", sourcePosition=" + sourcePosition +
                ", subPosition=" + subPosition +
                ", subSourcePosition=" + subSourcePosition +
                '}';
    }
}
