package com.wangjie.seizerecyclerview.attacher;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.BaseSeizeAdapter;
import com.wangjie.seizerecyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class MultiSeizeAdapter<T> extends BaseSeizeAdapter {
    /**
     * Get type function
     */
    private Func1R<T, Integer> getItemType;
    /**
     * Map -> key: type; value: viewHolderOwner creation function
     */
    private SparseArray<ViewHolderOwner> viewHolderOwnerMap = new SparseArray<>();

    public void setGetItemType(Func1R<T, Integer> getItemType) {
        this.getItemType = getItemType;
    }

    public void addSupportViewHolder(int type, ViewHolderOwner viewHolderOwner) {
        viewHolderOwnerMap.put(type, viewHolderOwner);
    }

    private List<T> list = new ArrayList<>();

    public List<T> getList() {
        return list;
    }

    public void addList(List<T> list) {
        this.list.addAll(list);
    }

    public void setList(List<T> list) {
        if (null == list) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
    }

    @Override
    public int getSourceItemViewType(int subSourcePosition) {
        if (null != getItemType) {
            return getItemType.call(list.get(subSourcePosition));
        }
        return super.getSourceItemViewType(subSourcePosition);
    }

    @Override
    public BaseViewHolder onCreateTypeViewHolder(ViewGroup parent, int type) {
        ViewHolderOwner viewHolderOwner = viewHolderOwnerMap.get(type);
        if (null != viewHolderOwner) {
            return viewHolderOwner.createViewHolder(parent);
        }
        return null;
    }

    @Override
    public T getItem(int subSourcePosition) {
        return list.get(subSourcePosition);
    }

    @Override
    public int getSourceItemCount() {
        return list.size();
    }

    /**
     * ViewHolderOwner与RecyclerView周期绑定
     */
    public void attachToRecyclerView(RecyclerView recyclerView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            recyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    for (int i = 0, size = viewHolderOwnerMap.size(); i < size; i++) {
                        viewHolderOwnerMap.get(viewHolderOwnerMap.keyAt(i)).onParentViewDetachedFromWindow();
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    for (int i = 0, size = viewHolderOwnerMap.size(); i < size; i++) {
                        viewHolderOwnerMap.get(viewHolderOwnerMap.keyAt(i)).onParentViewDetachedFromWindow();
                    }
                }
            });
        }
    }

    /**
     * get type by item
     */
    public int getSourceItemViewType(T item) {
        return null == getItemType ? TYPE_DEFAULT : getItemType.call(item);
    }

    @Override
    public boolean hasViewType(int viewType) {
        return typeHeaderDefault == viewType
                || typeFooterDefault == viewType
                || null != viewHolderOwnerMap.get(viewType);
    }
}
