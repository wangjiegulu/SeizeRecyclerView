package com.wangjie.seizerecyclerview.attacher;

import android.util.SparseArray;
import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.BaseSeizeAdapter;
import com.wangjie.seizerecyclerview.BaseViewHolder;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class AdapterAttacher<T> implements BaseSeizeAdapter.OnSeizeAdapterListener {

    private BaseSeizeAdapter adapter;

    public AdapterAttacher(BaseSeizeAdapter adapter) {
        this.adapter = adapter;
        adapter.setOnSeizeAdapterListener(this);
    }

    /**
     * Get type function
     */
    private Func1R<T, Integer> getFeedItemType;
    /**
     * Map -> key: type; value: viewHolderOwner creation function
     */
    private SparseArray<ViewHolderOwner> viewHolderOwnerMap = new SparseArray<>();

    public void setGetItemType(Func1R<T, Integer> getFeedItemType) {
        this.getFeedItemType = getFeedItemType;
    }

    public void addSupportViewHolder(int type, ViewHolderOwner viewHolderOwner) {
        viewHolderOwnerMap.put(type, viewHolderOwner);
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
    @SuppressWarnings("unchecked")
    public int getSourceItemViewType(int subSourcePosition) {
        if (null != getFeedItemType) {
            return getFeedItemType.call((T) adapter.getItem(subSourcePosition));
        }
        return -1;
    }
}
