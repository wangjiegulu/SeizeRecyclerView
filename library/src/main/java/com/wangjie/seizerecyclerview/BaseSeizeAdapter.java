package com.wangjie.seizerecyclerview;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.annotation.Beta;

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
            parentAdapter.notifyItemChanged(getPositionWithoutHeadView(0));
        } else if (hasHead) {
            parentAdapter.notifyItemRemoved(getPositionWithoutHeadView(0));
            notifyDataSetRangeChanged();
        } else if (headerView != null) {
            parentAdapter.notifyItemInserted(getPositionWithoutHeadView(0));
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
            parentAdapter.notifyItemChanged(getPositionWithoutHeadView(getItemCount() - 1));
        } else if (hasFooter) {
            parentAdapter.notifyItemRemoved(getPositionWithoutHeadView(getItemCount() - 1));
            notifyDataSetRangeChanged();
        } else {
            parentAdapter.notifyItemInserted(getPositionWithoutHeadView(getSourceItemCount() + getCount(headerView)));
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

    public int getPosition(int subSourcePosition) {
        return getPositionWithoutHeadView(subSourcePosition) + getCount(headerView);
    }

    /**
     * @param subSourcePosition see getPosition
     */
    @Deprecated
    public int getParentPosition(int subSourcePosition) {
        return getPositionWithoutHeadView(subSourcePosition) + getCount(headerView);
    }

    /**
     * @param subSourcePosition subSourcePosition
     * @return position - selfHeadViewCount
     */
    private int getPositionWithoutHeadView(int subSourcePosition) {
        if (parentAdapter == null) {
            return 0;
        }
        SeizePosition seizePosition = parentAdapter.convertSeizePosition(this,
                subSourcePosition + getCount(headerView));
        if (seizePosition == null) {
            return 0;
        }
        return seizePosition.getPosition() - getCount(headerView);
    }

    /**
     * get behind itemCount ,from position to parentAdapterCount
     *
     * @param position position
     * @return behind total itemCount
     */
    private int getBehindItemCount(int position) {
        if (parentAdapter == null) {
            return 0;
        }
        return parentAdapter.getItemCount() - position;
    }

    /**
     * notify an itemView,some bugs with origin method
     */
    @Override
    public void notifyItem(int position) {
        if (parentAdapter != null) {
            parentAdapter.notifyItem(getPosition(position));
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
        int positionParent = getPosition(positionStart);
        parentAdapter.notifyItemRangeInserted(positionParent, itemCount);
    }

    /**
     * insert a data,used with notifyDataSetRangeChanged
     *
     * @param position position
     */
    @Override
    public void notifyItemInserted(int position) {
        int positionParent = getPosition(position);
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
        int positionParent = getPosition(positionStart);
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
        int positionParent = getPosition(positionStart);
        parentAdapter.notifyItemRangeChanged(positionParent, itemCount, payload);
    }

    /**
     * notify an item，see notifyItem
     *
     * @param position position
     */
    @Override
    public void notifyItemChanged(int position) {
        int positionParent = getPosition(position);
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
        int positionParent = getPosition(position);
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
        int positionParent = getPosition(fromPosition);
        parentAdapter.notifyItemMoved(positionParent, getPosition(toPosition));
    }

    /**
     * remove a range ,used with notifyDataSetRangeChanged
     *
     * @param positionStart position start
     * @param itemCount     count
     */
    @Override
    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        int positionParent = getPosition(positionStart);
        parentAdapter.notifyItemRangeRemoved(positionParent, itemCount);
    }

    /**
     * remove an item ,used with notifyDataSetRangeChanged
     *
     * @param position position in data
     */
    @Override
    public void notifyItemRemoved(int position) {
        int positionParent = getPosition(position);
        parentAdapter.notifyItemRemoved(positionParent);
    }

    /**
     * there is some bugs
     */
    @Deprecated
    @Override
    public void notifyDataSetInsert() {
        int positionStart = getPositionWithoutHeadView(0);
        parentAdapter.notifyItemRangeInserted(positionStart, getBehindItemCount(positionStart));
    }

    /**
     * start from current seizeAdapter to end of parentAdapter
     */
    @Override
    public void notifyDataSetRangeChanged() {
        int positionStart = getPositionWithoutHeadView(0);
        parentAdapter.notifyItemRangeChanged(positionStart, getBehindItemCount(positionStart));
    }
}
