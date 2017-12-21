package com.wangjie.seizerecyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.attacher.FuncR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/23/17.
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<SeizeAdapter<BaseViewHolder>> seizeAdapters = new ArrayList<>();

    private static final int TYPE_DEFAULT = 0x7682;
    private static final int TYPE_HEADER_VIEW = 0x7683;
    private static final int TYPE_FOOTER_VIEW = 0x7684;

    private View headerView;
    private View footerView;

    private FuncR<BaseViewHolder> defaultViewHolderFunc;
    private RecyclerView recyclerView;

    public void setHeader(View view) {
        headerView = view;
    }

    public void setFooter(View view) {
        footerView = view;
    }

    @SafeVarargs
    public final void setSeizeAdapters(SeizeAdapter<BaseViewHolder>... seizeAdapters) {
        this.seizeAdapters = Arrays.asList(seizeAdapters);
        for (SeizeAdapter<BaseViewHolder> seizeAdapter : this.seizeAdapters) {
            seizeAdapter.setParentAdapter(this);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER_VIEW:
                return EmptyViewHolder.newInstance(headerView);
            case TYPE_FOOTER_VIEW:
                return EmptyViewHolder.newInstance(footerView);
            default:
                if (null != seizeAdapters) {
                    for (SeizeAdapter<BaseViewHolder> seizeAdapter : seizeAdapters) {
                        BaseViewHolder viewHolder;
                        if (seizeAdapter.hasViewType(viewType)
                                && null != (viewHolder = seizeAdapter.onCreateViewHolder(parent, viewType))
                                ) {
                            return viewHolder;
                        }
                    }
                }
        }
        return createDefaultViewHolder(parent);
    }

    private BaseViewHolder createDefaultViewHolder(ViewGroup parent) {
        return null == defaultViewHolderFunc ? EmptyViewHolder.newInstance(new View(parent.getContext())) : defaultViewHolderFunc.call();
    }

    public void setDefaultViewHolderFunc(FuncR<BaseViewHolder> defaultViewHolderFunc) {
        this.defaultViewHolderFunc = defaultViewHolderFunc;
    }

    @Override
    public final int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER_VIEW;
        } else if (isFooter((position))) {
            return TYPE_FOOTER_VIEW;
        } else {
            SeizePosition seizePosition = convertSeizePosition(position);
            if (null != seizePosition && seizePosition.getSeizeAdapterIndex() >= 0) {
                return seizeAdapters.get(seizePosition.getSeizeAdapterIndex())
                        .getItemViewType(seizePosition);
            }
        }

        return super.getItemViewType(position);
    }

    public boolean isHeader(int position) {
        int headerCount = getCount(headerView);
        return 0 != headerCount && position <= headerCount - 1;
    }

    public boolean isFooter(int position) {
        int footerCount = getCount(footerView);
        return 0 != footerCount && position >= getItemCount() - footerCount;
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        SeizePosition seizePosition = convertSeizePosition(position);
        if (null != seizePosition && seizePosition.getSeizeAdapterIndex() >= 0) {
            seizeAdapters.get(seizePosition.getSeizeAdapterIndex())
                    .onBindViewHolder(holder, seizePosition);
        }
    }

    /**
     * 刷新某个位置itemView，使用android自带刷新方式，在TV上会有渐变效果，
     * 并且自带方法在某些情况导致setText不显示文字
     */
    public void notifyItem(int position) {
        if (recyclerView == null) {
            return;
        }
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (!(viewHolder instanceof BaseViewHolder)) {
            return;
        }
        onBindViewHolder(((BaseViewHolder) viewHolder), position);
    }

    @Nullable
    public final SeizeAdapter<BaseViewHolder> getSeizeAdapter(int position) {
        SeizePosition seizePosition = convertSeizePosition(position);
        if (null != seizePosition && seizePosition.getSeizeAdapterIndex() >= 0) {
            return seizeAdapters.get(seizePosition.getSeizeAdapterIndex());
        }
        return null;
    }

    @Nullable
    public final List<SeizeAdapter<BaseViewHolder>> getSeizeAdapters() {
        return seizeAdapters;
    }

    @Nullable
    public final View getHeaderView() {
        return headerView;
    }

    /**
     * 给定parentAdapter中的position位置，返回一个seizeAdapter
     *
     * @param position parentAdapter中item的位置，包含headerView，footerView
     * @return seizePosition
     */
    @Nullable
    public final SeizePosition convertSeizePosition(int position) {
        if (null != seizeAdapters) {
            int seizeLastCount = getCount(headerView);
            //属于headerView
            if (position < seizeLastCount) {
                return new SeizePosition(-1,
                        position, -1, -1, -1);
            }
            for (int i = 0, len = seizeAdapters.size(); i < len; i++) {
                SeizeAdapter seizeAdapter = seizeAdapters.get(i);
                int count = seizeAdapter.getItemCount();
                seizeLastCount += count;
                if (seizeLastCount > position) {
                    int subPosition = count - (seizeLastCount - position);
                    return new SeizePosition(i,
                            position, positionToSourcePosition(position),
                            subPosition, seizeAdapter.subPositionToSubSourcePosition(subPosition)
                    );
                }
            }
            //属于parentAdapter的footerView
            if (position > getItemCount() - 1 - getCount(footerView)) {
                return new SeizePosition(-1,
                        position, -1, -1, -1);
            }
        }
        return null;
    }

    @Nullable
    public final SeizePosition convertSeizePosition(int seizeAdapterIndex, int subPosition) {
        // TODO: 3/28/17 wangjie impl
        throw new RuntimeException("Not supported!");
    }

    @Override
    public final int getItemCount() {
        int itemCount = getCount(headerView) + getCount(footerView);
        List<SeizeAdapter<BaseViewHolder>> childSeizeAdapters = seizeAdapters;
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

    /**
     * parentAdapter中item的位置转换为数据源中的位置，
     *
     * @param position item的位置
     * @return sourcePosition
     */
    public final int positionToSourcePosition(int position) {
        return position - getCount(headerView);
    }

    /**
     * parentAdapter中数据源中的位置转换为item的位置，
     *
     * @param sourcePosition 数据源中的位置
     * @return position
     */
    public final int sourcePositionToPosition(int sourcePosition) {
        return sourcePosition + getCount(headerView);
    }
}
