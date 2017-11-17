package com.wangjie.seizerecyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class EmptyViewHolder extends BaseViewHolder {

    public static EmptyViewHolder newInstance(View itemView) {
        if (itemView != null && itemView.getParent() instanceof ViewGroup) {
            ((ViewGroup) itemView.getParent()).removeView(itemView);
        }
        return new EmptyViewHolder(itemView);
    }

    private EmptyViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        // ignore
    }
}
