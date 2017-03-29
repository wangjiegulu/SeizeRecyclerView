package com.wangjie.seizerecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/27/17.
 */
public abstract class BaseSeizeAdapter implements SeizeAdapter<BaseRecyclerHolder> {
    protected RecyclerView.Adapter<BaseRecyclerHolder> parentAdapter;
    private static final int TYPE_DEFAULT = 0x8682;
    private int typeHeaderDefault = -0x8683;
    private int typeFooterDefault = -0x8684;
    private View headerView;
    private View footerView;

    public void setHeader(@NonNull View view) {
        headerView = view;
        // TODO: 3/29/17 wangjie optim
        typeHeaderDefault = this.hashCode();
    }

    public void setFooter(@NonNull View view) {
        footerView = view;
        // TODO: 3/29/17 wangjie optim
        typeFooterDefault = this.hashCode() - 1;
    }

    @Override
    public final BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createTypeViewHolderInternal(parent, viewType);
    }

    private BaseRecyclerHolder createTypeViewHolderInternal(ViewGroup parent, int viewType) {
        if (viewType == typeHeaderDefault) {
            return new EmptyViewHolder(headerView);
        } else if (viewType == typeFooterDefault) {
            return new EmptyViewHolder(footerView);
        } else {
            return onCreateTypeViewHolder(parent, viewType);
        }
    }

    public abstract BaseRecyclerHolder onCreateTypeViewHolder(ViewGroup parent, int type);

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, SeizePosition seizePosition) {
        holder.onBindViewHolderInternal(holder, seizePosition);
    }

    @Override
    public void setParentAdapter(RecyclerView.Adapter<BaseRecyclerHolder> parentAdapter) {
        this.parentAdapter = parentAdapter;
    }

    @Override
    public boolean hasViewType(int viewType) {
        return true;
    }

    @Override
    public final int getItemCount() {
        return getSourceItemCount() + getCount(headerView) + getCount(footerView);
    }

    private int getCount(View view) {
        return null == view ? 0 : 1;
    }

    @Override
    public long getItemId(int subPosition) {
        return subPosition;
    }

    @Override
    public final int getItemViewType(SeizePosition seizePosition) {
        int subPosition = seizePosition.getSubPosition();
        int headerCount = getCount(headerView);
        int footerCount = getCount(footerView);
        if (0 != headerCount && subPosition <= headerCount - 1) {
            return typeHeaderDefault;
        } else if (0 != footerCount && subPosition >= getItemCount() - footerCount) {
            return typeFooterDefault;
        } else {
            return getSourceItemViewType(seizePosition.getSubSourcePosition());
        }
    }

    @Override
    public int subPositionToSubSourcePosition(int subPosition) {
        return subPosition - getCount(headerView);
    }

    @Override
    public int subSourcePositionToSubPosition(int subSourcePosition) {
        return subSourcePosition + getCount(headerView);
    }

    public abstract int getSourceItemCount();

    public int getSourceItemViewType(int subSourcePosition) {
        return TYPE_DEFAULT;
    }

    @Override
    public void notifyDataSetChanged() {
        if (null != parentAdapter) {
            parentAdapter.notifyDataSetChanged();
        }
    }

}
