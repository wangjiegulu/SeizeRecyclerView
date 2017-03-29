package com.wangjie.seizerecyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/23/17.
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {

    protected List<SeizeAdapter<BaseRecyclerHolder>> seizeAdapters = new ArrayList<>();

    private static final int TYPE_DEFAULT = 0x7682;
    private static final int TYPE_HEADER_VIEW = 0x7683;
    private static final int TYPE_FOOTER_VIEW = 0x7684;

    private View headerView;
    private View footerView;

    public void setHeader(@NonNull View view) {
        headerView = view;
    }

    public void setFooter(@NonNull View view) {
        footerView = view;
    }

    @SafeVarargs
    public final void setSeizeAdapters(SeizeAdapter<BaseRecyclerHolder>... seizeAdapters) {
        this.seizeAdapters = Arrays.asList(seizeAdapters);
        for (SeizeAdapter<BaseRecyclerHolder> seizeAdapter : this.seizeAdapters) {
            seizeAdapter.setParentAdapter(this);
        }
    }

    @Override
    public final BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER_VIEW:
                return new EmptyViewHolder(headerView);
            case TYPE_FOOTER_VIEW:
                return new EmptyViewHolder(footerView);
            default:
                if (null != seizeAdapters) {
                    for (SeizeAdapter<BaseRecyclerHolder> seizeAdapter : seizeAdapters) {
                        BaseRecyclerHolder viewHolder;
                        if (seizeAdapter.hasViewType(viewType)
                                && null != (viewHolder = seizeAdapter.onCreateViewHolder(parent, viewType))
                                ) {
                            return viewHolder;
                        }
                    }
                }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int headerCount = getCount(headerView);
        int footerCount = getCount(footerView);
        if (0 != headerCount && position <= headerCount - 1) {
            return TYPE_HEADER_VIEW;
        } else if (0 != footerCount && position >= getItemCount() - footerCount) {
            return TYPE_FOOTER_VIEW;
        } else {
            SeizePosition seizePosition = convertSeizePosition(position);
            if (null != seizePosition) {
                return seizeAdapters.get(seizePosition.getSeizeAdapterIndex())
                        .getItemViewType(seizePosition);
            }
        }

        return super.getItemViewType(position);
    }

    @Override
    public final void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        SeizePosition seizePosition = convertSeizePosition(position);
        if (null != seizePosition) {
            seizeAdapters.get(seizePosition.getSeizeAdapterIndex())
                    .onBindViewHolder(holder, seizePosition);
        }
    }

    @Nullable
    private SeizePosition  convertSeizePosition(int position) {
        if (null != seizeAdapters) {
            int seizeLastPosition = getCount(headerView);
            for (int i = 0, len = seizeAdapters.size(); i < len; i++) {
                SeizeAdapter seizeAdapter = seizeAdapters.get(i);
                int count = seizeAdapter.getItemCount();
                seizeLastPosition += count;
                if (seizeLastPosition > position) {
                    int subPosition = count - (seizeLastPosition - position);
                    return new SeizePosition(i,
                            position, positionToSourcePosition(position),
                            subPosition, seizeAdapter.subPositionToSubSourcePosition(subPosition)
                    );
                }
            }
        }
        return null;
    }

    @Nullable
    private SeizePosition convertSeizePosition(int seizeAdapterIndex, int subPosition) {
        // TODO: 3/28/17 wangjie impl
        throw new RuntimeException("Not supported!");
    }

    @Override
    public final int getItemCount() {
        int itemCount = getCount(headerView) + getCount(footerView);
        List<SeizeAdapter<BaseRecyclerHolder>> childSeizeAdapters = seizeAdapters;
        if (null != childSeizeAdapters) {
            for (SeizeAdapter seizeAdapter : childSeizeAdapters) {
                itemCount += seizeAdapter.getItemCount();
            }
        }
        return itemCount;
    }

    private int getCount(View view) {
        return null == view ? 0 : 1;
    }

    public int positionToSourcePosition(int position) {
        return position - getCount(headerView);
    }

    public int sourcePositionToPosition(int sourcePosition) {
        return sourcePosition + getCount(headerView);
    }
}
