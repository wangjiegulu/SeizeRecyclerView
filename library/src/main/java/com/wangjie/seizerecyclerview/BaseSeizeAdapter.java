package com.wangjie.seizerecyclerview;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.annotation.Beta;

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
     * item position in seizeAdapter to position in data
     *
     * @param subPosition item position
     * @return sourcePosition
     */
    @Override
    public int subPositionToSubSourcePosition(int subPosition) {
        return subPosition - getCount(headerView);
    }

    /**
     * item position in data to position in seizeAdapter
     *
     * @param subSourcePosition item position in data
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
     * notify an itemView,some bugs with origin method
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
     * insert some data,used with notifyDataSetRangeChanged
     *
     * @param positionStart positionStart
     * @param itemCount     itemCount
     */
    @Override
    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeInserted(positionParent, itemCount);
    }

    /**
     * insert a data,used with notifyDataSetRangeChanged
     *
     * @param position position
     */
    @Override
    public void notifyItemInserted(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemInserted(positionParent);
    }

    /**
     * notify range,
     *
     * @param positionStart positionStart
     * @param itemCount     itemCount
     */
    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeChanged(positionParent, itemCount);
    }

    /**
     * notify range,
     *
     * @param positionStart positionStart
     * @param itemCount     itemCount
     */
    @Override
    @Beta
    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeChanged(positionParent, itemCount, payload);
    }

    /**
     * notify an item，see notifyItem
     *
     * @param position position
     */
    @Override
    public void notifyItemChanged(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemChanged(positionParent);
    }

    /**
     * notify an item,see notifyItem
     *
     * @param position position
     */
    @Override
    @Beta
    public void notifyItemChanged(int position, Object payload) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemChanged(positionParent, payload);
    }

    /**
     * move
     *
     * @param fromPosition position from
     * @param toPosition   position to
     */
    @Override
    public void notifyItemMoved(int fromPosition, int toPosition) {
        int positionParent = getParentPosition(fromPosition);
        parentAdapter.notifyItemMoved(positionParent, getParentPosition(toPosition));
    }

    /**
     * remove a range ,used with notifyDataSetRangeChanged
     *
     * @param positionStart position start
     * @param itemCount     count
     */
    @Override
    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeRemoved(positionParent, itemCount);
    }

    /**
     * remove an item ,used with notifyDataSetRangeChanged
     *
     * @param position position in data
     */
    @Override
    public void notifyItemRemoved(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemRemoved(positionParent);
    }

    /**
     * there is some bugs
     */
    @Deprecated
    @Override
    public void notifyDataSetInsert() {
        int positionStart = getParentPositionWithoutSelfHeadView(0);
        parentAdapter.notifyItemRangeInserted(positionStart, getItemCount() + getItemCountWithLeftSeizeAdapter(0));
    }

    /**
     * start from current seizeAdapter to end of parentAdapter
     */
    @Override
    public void notifyDataSetRangeChanged() {
        int positionStart = getParentPositionWithoutSelfHeadView(0);
        parentAdapter.notifyItemRangeChanged(positionStart, getItemCount() + getItemCountWithLeftSeizeAdapter(0));
    }
}
