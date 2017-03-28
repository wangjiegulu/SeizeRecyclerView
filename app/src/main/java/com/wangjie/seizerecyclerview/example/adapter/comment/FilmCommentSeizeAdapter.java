package com.wangjie.seizerecyclerview.example.adapter.comment;

import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.BaseSeizeAdapter;
import com.wangjie.seizerecyclerview.BaseRecyclerHolder;
import com.wangjie.seizerecyclerview.example.vm.comment.CommentVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class FilmCommentSeizeAdapter extends BaseSeizeAdapter<BaseRecyclerHolder> {
    private List<CommentVM> list = new ArrayList<>();

    public void setList(List<CommentVM> list) {
        this.list = list;
    }

    public void addList(List<CommentVM> list) {
        this.list.addAll(list);
    }

    @Override
    public int getItemViewType(int subPosition) {
        return list.get(subPosition).getViewType();
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CommentVM.TYPE_COMMENT:
                return new FilmCommentSeizeViewHolder(parent);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
