package com.wangjie.seizerecyclerview;

import android.view.View;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class EmptyViewHolder extends BaseRecyclerHolder{
    public EmptyViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, SeizePosition seizePosition) {
        // ignore
    }
}
