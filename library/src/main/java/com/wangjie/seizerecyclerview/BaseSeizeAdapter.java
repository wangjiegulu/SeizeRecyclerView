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
        headerView = view;
        // TODO: 3/29/17 wangjie optim
        typeHeaderDefault = this.hashCode();
    }

    @Override
    public void setFooter(View view) {
        footerView = view;
        // TODO: 3/29/17 wangjie optim
        typeFooterDefault = this.hashCode() - 1;
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
            return new EmptyViewHolder(headerView);
        } else if (viewType == typeFooterDefault) {
            return new EmptyViewHolder(footerView);
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

    private int getParentPosition(int positionStart) {
        int position = (parentAdapter.getHeaderView() == null ? 0 : 1) + positionStart + getCount(headerView);
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

    @Override
    public void notifyDataSetChanged() {
        if (null != parentAdapter) {
            parentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeInserted(positionParent, itemCount);
    }

    @Override
    public void notifyItemInserted(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemInserted(positionParent);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeChanged(positionParent, itemCount);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeChanged(positionParent, itemCount, payload);
    }

    @Override
    public void notifyItemChanged(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemChanged(positionParent);
    }

    @Override
    public void notifyItemChanged(int positionStart, Object payload) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemChanged(positionParent, payload);
    }

    @Override
    public void notifyItemMoved(int fromPosition, int toPosition) {
        int positionParent = getParentPosition(fromPosition);
        parentAdapter.notifyItemMoved(positionParent, toPosition);
    }

    @Override
    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        int positionParent = getParentPosition(positionStart);
        parentAdapter.notifyItemRangeRemoved(positionParent, itemCount);
    }

    @Override
    public void notifyItemRemoved(int position) {
        int positionParent = getParentPosition(position);
        parentAdapter.notifyItemRemoved(positionParent);
    }

}
