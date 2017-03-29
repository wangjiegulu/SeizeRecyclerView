package com.wangjie.seizerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    private SeizePosition seizePosition;

    public SeizePosition getSeizePosition() {
        return seizePosition;
    }

    public final void onBindViewHolderInternal(BaseViewHolder holder, SeizePosition seizePosition) {
        this.seizePosition = seizePosition;
        onBindViewHolder(holder, seizePosition);
    }

    public abstract void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition);

    public void onViewRecycled() {
        // ignore
    }

    public void onViewDetachedFromWindow() {
        // ignore
    }
}
