package com.wangjie.seizerecyclerview;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/27/17.
 */
public abstract class BaseSeizeAdapter implements SeizeAdapter<BaseViewHolder> {
    private static final String TAG = BaseSeizeAdapter.class.getSimpleName();
    protected BaseRecyclerAdapter parentAdapter;
    public static final int TYPE_DEFAULT = 0x8682;
    protected int typeHeaderDefault = -0x8683;
    protected int typeFooterDefault = -0x8684;
    private View headerView;
    private View footerView;

    @Override
    public void setHeader(View view) {
        if (headerView == view) {
            return;
        }
        boolean hasHead = headerView != null;
        headerView = view;
        // TODO: 3/29/17 wangjie optim
        typeHeaderDefault = this.hashCode();
        if (parentAdapter == null) {
            return;
        }
        if (hasHead && headerView != null) {
            parentAdapter.notifyItemChanged(getParentPositionWithoutSelfHeadView(0));
        } else if (hasHead) {
            parentAdapter.notifyItemRemoved(getParentPositionWithoutSelfHeadView(0));
            notifyDataSetRangeChanged();
        } else if (headerView != null) {
            parentAdapter.notifyItemInserted(getParentPositionWithoutSelfHeadView(0));
            notifyDataSetRangeChanged();
        }
    }

    @Override
    public void setFooter(View view) {
        if (footerView == view) {
            return;
        }
        boolean hasFooter = footerView != null;
        footerView = view;
        // TODO: 3/29/17 wangjie optim
        typeFooterDefault = this.hashCode() - 1;
        if (parentAdapter == null) {
            return;
        }
        if (hasFooter && footerView != null) {
            parentAdapter.notifyItemChanged(getParentPositionWithoutSelfHeadView(getItemCount() - 1));
        } else if (hasFooter) {
            parentAdapter.notifyItemRemoved(getParentPositionWithoutSelfHeadView(getItemCount() - 1));
            notifyDataSetRangeChanged();
        } else {
            parentAdapter.notifyItemInserted(getParentPositionWithoutSelfHeadView(getSourceItemCount() + getCount(headerView)));
            notifyDataSetRangeChanged();
        }
    }

    @Override
    @Nullable
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            return createTypeViewHolderInternal(parent, viewType);
        } catch (Throwable throwable) {
            Log.e(TAG, "onCreateViewHolder", throwable);
        }
        return null;
    }

    @Nullable
    private BaseViewHolder createTypeViewHolderInternal(ViewGroup parent, int viewType) {
        if (viewType == typeHeaderDefault) {
            return EmptyViewHolder.newInstance(headerView);
        } else if (viewType == typeFooterDefault) {
            return EmptyViewHolder.newInstance(footerView);
        } else {
            return onCreateTypeViewHolder(parent, viewType);
        }
    }

    @Nullable
    public abstract BaseViewHolder onCreateTypeViewHolder(ViewGroup parent, int type);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        try {
            holder.onBindViewHolderInternal(holder, seizePosition);
        } catch (Throwable throwable) {
            Log.e(TAG, "onBindViewHolder", throwable);
        }
    }

    @Override
    public void setParentAdapter(BaseRecyclerAdapter parentAdapter) {
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

    public abstract Object getItem(int subSourcePosition);

    @Override
    public final int getItemViewType(SeizePosition seizePosition) {
        int subPosition = seizePosition.getSubPosition();
        if (isHeader(subPosition)) {
            return typeHeaderDefault;
        } else if (isFooter(subPosition)) {
            return typeFooterDefault;
        } else {
            return getSourceItemViewType(seizePosition.getSubSourcePosition());
        }
    }

    @Override
    public boolean isHeader(int subPosition) {
        int headerCount = getCount(headerView);
        return 0 != headerCount && subPosition <= headerCount - 1;
    }

    @Override
    public boolean isFooter(int subPosition) {
        int footerCount = getCount(footerView);
        return 0 != footerCount && subPosition >= getItemCount() - footerCount;
    }

    /**
     * seizeAdapter中item的位置转换为数据源中的位置，
     * @param subPosition item的位置
     * @return sourcePosition
     */
    @Override
    public int subPositionToSubSourcePosition(int subPosition) {
        return subPosition - getCount(headerView);
    }

    /**
     * zeizeAdapter中数据源中的位置转换为item的位置，
     * @param subSourcePosition 数据源中的位置
     * @return position
     */
    @Override
    public int subSourcePositionToSubPosition(int subSourcePosition) {
        return subSourcePosition + getCount(headerView);
    }

    public abstract int getSourceItemCount();

    public int getSourceItemViewType(int subSourcePosition) {
        return TYPE_DEFAULT;
    }

    public int getParentPosition(int positionStart) {
        return getParentPositionWithoutSelfHeadView(positionStart) + getCount(headerView);
    }

    private int getParentPositionWithoutSelfHeadView(int positionStart) {
        if (parentAdapter == null) {
            return 0;
        }
        int position = (parentAdapter.getHeaderView() == null ? 0 : 1) + positionStart;
        List<SeizeAdapter<BaseViewHolder>> seizeAdapters = parentAdapter.getSeizeAdapters();
        if (seizeAdapters == null) {
            return position;
        }
        for (SeizeAdapter<BaseViewHolder> seizeAdapter : seizeAdapters) {
            if (seizeAdapter == this) {
                break;
            }
            position += seizeAdapter.getItemCount();
        }
        return position;
    }

    private int getItemCountWithLeftSeizeAdapter(int itemCount) {
        if (parentAdapter == null) {
            return 0;
        }
        itemCount = itemCount + getCount(footerView);
        List<SeizeAdapter<BaseViewHolder>> seizeAdapters = parentAdapter.getSeizeAdapters();
        if (seizeAdapters == null) {
            return itemCount;
        }
        boolean findCurrentSeizeAdapter = false;
        for (SeizeAdapter seizeAdapter : seizeAdapters) {
            if (seizeAdapter == this) {
                findCurrentSeizeAdapter = true;
                continue;
            }
            if (!findCurrentSeizeAdapter) {
                continue;
            }
            itemCount = itemCount + seizeAdapter.getItemCount();
        }
        return itemCount;
    }


    /**
     * 刷新某个位置itemView，使用android自带刷新方式，在TV上会有渐变效果，
     * 并且自带方法在某些情况导致setText不显示文字
     */
    @Override
    public void notifyItem(int position) {
        if (parentAdapter != null) {
            parentAdapter.notifyItem(getParentPosition(position));
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (null != parentAdapter) {
            parentAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 插入指定数量的数据，需要配合notifyDataSetRangeChanged使用
     * @param positionStart 数据源的位置
     * @param itemCount 插入数量
     */
    @Override
    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeInserted(positionParent, itemCount);
    }

    /**
     * 插入指定数量的数据，需要配合notifyDataSetRangeChanged使用
     * @param position 数据源的位置
     */
    @Override
    public void notifyItemInserted(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemInserted(positionParent);
    }

    /**
     * 范围刷新,
     * @param positionStart 数据源位置，数量
     * @param itemCount 刷新的数量
     */
    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeChanged(positionParent, itemCount);
    }

    /**
     * 范围刷新,
     * @param positionStart 数据源位置，数量
     * @param itemCount 刷新的数量
     */
    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeChanged(positionParent, itemCount, payload);
    }

    /**
     * 单个刷新，see notifyItem
     * @param position 位置
     */
    @Override
    public void notifyItemChanged(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemChanged(positionParent);
    }

    /**
     * 单个刷新，see notifyItem
     * @param position 位置
     */
    @Override
    public void notifyItemChanged(int position, Object payload) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemChanged(positionParent, payload);
    }

    /**
     * 移动刷新
     * @param fromPosition 移动开始位置
     * @param toPosition 移动结束位置
     */
    @Override
    public void notifyItemMoved(int fromPosition, int toPosition) {
        int positionParent = getParentPosition(fromPosition);
        parentAdapter.notifyItemMoved(positionParent, getParentPosition(toPosition));
    }

    /**
     * 范围删除，需配合notifyDataSetRangeChanged使用
     * @param positionStart 开始位置
     * @param itemCount 数量
     */
    @Override
    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeRemoved(positionParent, itemCount);
    }

    /**
     * 单个移除，需配合notifyDataSetRangeChanged使用
     * @param position 数据源位置
     */
    @Override
    public void notifyItemRemoved(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemRemoved(positionParent);
    }

    /**
     * 该方法不合理
     */
    @Deprecated
    @Override
    public void notifyDataSetInsert() {
        int positionStart = getParentPositionWithoutSelfHeadView(0);
        parentAdapter.notifyItemRangeInserted(positionStart, getItemCount() + getItemCountWithLeftSeizeAdapter(0));
    }

    /**
     * 从当前seizeAdapter刷新到parentAdapter结束位置
     */
    @Override
    public void notifyDataSetRangeChanged() {
        int positionStart = getParentPositionWithoutSelfHeadView(0);
        parentAdapter.notifyItemRangeChanged(positionStart, getItemCount() + getItemCountWithLeftSeizeAdapter(0));
    }
}
