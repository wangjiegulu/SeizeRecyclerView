package com.wangjie.seizerecyclerview.attacher;

import android.util.SparseArray;
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
    public Object getItem(int subSourcePosition) {
        return list.get(subSourcePosition);
    }

    @Override
    public int getSourceItemCount() {
        return list.size();
    }


}
