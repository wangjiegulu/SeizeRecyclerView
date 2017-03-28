package com.wangjie.seizerecyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/27/17.
 */
public abstract class BaseSeizeAdapter<VH extends BaseRecyclerHolder> implements SeizeAdapter<VH> {
    protected RecyclerView.Adapter<VH> parentAdapter;

    @Override
    public void onBindViewHolder(VH holder, SeizePosition seizePosition) {
        holder.onBindViewHolderInternal(holder, seizePosition);
    }

    @Override
    public void setParentAdapter(RecyclerView.Adapter<VH> parentAdapter) {
        this.parentAdapter = parentAdapter;
    }

    @Override
    public boolean hasViewType(int viewType) {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        if (null != parentAdapter) {
            parentAdapter.notifyDataSetChanged();
        }
    }
}
