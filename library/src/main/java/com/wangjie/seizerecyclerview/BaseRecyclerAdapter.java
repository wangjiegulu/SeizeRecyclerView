package com.wangjie.seizerecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/23/17.
 */
public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<SeizeAdapter<VH>> seizeAdapters = new ArrayList<>();

    public void setSeizeAdapters(SeizeAdapter<VH>... seizeAdapters) {
        this.seizeAdapters = Arrays.asList(seizeAdapters);
        for (SeizeAdapter<VH> seizeAdapter : this.seizeAdapters) {
            seizeAdapter.setParentAdapter(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        SeizePosition seizePosition = convertSeizePosition(position);
        if (null != seizePosition) {
            return seizeAdapters.get(seizePosition.getSeizeAdapterIndex())
                    .getItemViewType(seizePosition);
        }
        return super.getItemViewType(position);
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null != seizeAdapters) {
            for (SeizeAdapter<VH> seizeAdapter : seizeAdapters) {
                VH viewHolder;
                if (seizeAdapter.hasViewType(viewType)
                        && null != (viewHolder = seizeAdapter.onCreateViewHolder(parent, viewType))
                        ) {
                    return viewHolder;
                }
            }
        }
        return null;
    }

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        SeizePosition seizePosition = convertSeizePosition(position);
        if (null != seizePosition) {
            seizeAdapters.get(seizePosition.getSeizeAdapterIndex())
                    .onBindViewHolder(holder, seizePosition);
        }
    }

    @Nullable
    private SeizePosition convertSeizePosition(int position) {
        if (null != seizeAdapters) {
            int seizeLastPosition = 0;
            for (int i = 0, len = seizeAdapters.size(); i < len; i++) {
                SeizeAdapter seizeAdapter = seizeAdapters.get(i);
                int count = seizeAdapter.getItemCount();
                seizeLastPosition += count;
                if (seizeLastPosition > position) {
                    int subPosition = count - (seizeLastPosition - position);
                    return new SeizePosition(i, position, subPosition, seizeAdapter.subPositionToSubSourcePosition(subPosition));
                }
            }
        }
        return null;
    }

    @Nullable
    private SeizePosition convertSeizePosition(int seizeAdapterIndex, int subPosition) {
        // TODO: 3/28/17 wangjie impl
        return null;
    }

    @Override
    public final int getItemCount() {
        int itemCount = 0;
        List<SeizeAdapter<VH>> childSeizeAdapters = seizeAdapters;
        if (null != childSeizeAdapters) {
            for (SeizeAdapter seizeAdapter : childSeizeAdapters) {
                itemCount += seizeAdapter.getItemCount();
            }
        }
        return itemCount;
    }


}
