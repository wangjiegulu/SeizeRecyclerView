package com.wangjie.seizerecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/27/17.
 */
public interface SeizeAdapter<VH extends RecyclerView.ViewHolder> {

    void setParentAdapter(BaseRecyclerAdapter parentAdapter);

    void setHeader(View view);

    void setFooter(View view);

    boolean isHeader(int subPosition);

    boolean isFooter(int subPosition);

    boolean hasViewType(int viewType);

    int getItemViewType(SeizePosition seizePosition);

    @Nullable
    VH onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(VH holder, SeizePosition seizePosition);

    int getItemCount();

    long getItemId(int position);

    int subPositionToSubSourcePosition(int subPosition);

    int subSourcePositionToSubPosition(int subSourcePosition);

    void notifyItem(int position);

    /**
     * parentAdapter刷新
     */
    void notifyDataSetChanged();

    void notifyItemRangeInserted(int positionStart, int itemCount);

    void notifyItemInserted(int positionStart);

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyItemRangeChanged(int positionStart, int itemCount, Object payload);

    void notifyItemChanged(int positionStart);

    void notifyItemChanged(int positionStart, Object payload);

    void notifyItemMoved(int fromPosition, int toPosition);

    void notifyItemRangeRemoved(int positionStart, int itemCount);

    void notifyItemRemoved(int position);

    /**
     * insert include headView footView
     */
    void notifyDataSetInsert();

    /**
     * notify include headView footView
     */
    void notifyDataSetRangeChanged();

}
