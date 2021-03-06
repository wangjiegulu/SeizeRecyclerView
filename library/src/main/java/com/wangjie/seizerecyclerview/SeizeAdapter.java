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
public interface SeizeAdapter<VH extends RecyclerView.ViewHolder> {

    void setParentAdapter(RecyclerView.Adapter<VH> parentAdapter);

    void setHeader(@NonNull View view);

    void setFooter(@NonNull View view);

    boolean isHeader(int subPosition);

    boolean isFooter(int subPosition);

    boolean hasViewType(int viewType);

    int getItemViewType(SeizePosition seizePosition);

    VH onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(VH holder, SeizePosition seizePosition);

    int getItemCount();

    long getItemId(int position);

    int subPositionToSubSourcePosition(int subPosition);

    int subSourcePositionToSubPosition(int subSourcePosition);

    void notifyDataSetChanged();

}
