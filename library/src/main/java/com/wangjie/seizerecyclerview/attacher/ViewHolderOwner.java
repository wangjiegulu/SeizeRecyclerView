package com.wangjie.seizerecyclerview.attacher;

import android.content.Context;
import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.BaseViewHolder;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 3/2/17.
 */
public abstract class ViewHolderOwner<T> {
    private static final String TAG = ViewHolderOwner.class.getSimpleName();
    protected Context context;

    public ViewHolderOwner(Context context) {
        this.context = context;
    }


    public abstract BaseViewHolder createViewHolder(ViewGroup parent);

    public void onParentViewAttachedToWindow() {

    }

    public void onParentViewDetachedFromWindow() {

    }
}
