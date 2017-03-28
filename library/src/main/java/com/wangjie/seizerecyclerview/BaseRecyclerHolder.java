package com.wangjie.seizerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public abstract class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    public BaseRecyclerHolder(View itemView) {
        super(itemView);
    }

    private SeizePosition seizePosition;

    public SeizePosition getSeizePosition() {
        return seizePosition;
    }

    public final void onBindViewHolderInternal(BaseRecyclerHolder holder, SeizePosition seizePosition) {
        this.seizePosition = seizePosition;
        onBindViewHolder(holder, seizePosition);
    }

    public abstract void onBindViewHolder(BaseRecyclerHolder holder, SeizePosition seizePosition);
}
