package com.wangjie.seizerecyclerview;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class SeizePosition implements Serializable {

    /**
     * seizeAdapter在parentAdapter中的索引，从上往下，从左往右。
     */
    private int seizeAdapterIndex;
    /**
     * recyclerview 中item的位置索引
     */
    private int position;
    /**
     * parentAdapter中不包含parentAdapter的headerView ，footerView的位置索引，
     * 即parentAdapter数据源的索引
     */
    private int sourcePosition;
    /**
     * seizeAdapter中包含headerView，footerView的位置索引，
     * 即seizeAdapter的第一个headerView在当前seizePosition中的该值为0
     */
    private int subPosition;
    /**
     * seizeAdapter中不包含headerView，footerView的位置索引，
     * 即seizeAdapter的第一个数据源item在当前seizePosition中的该值为0
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
