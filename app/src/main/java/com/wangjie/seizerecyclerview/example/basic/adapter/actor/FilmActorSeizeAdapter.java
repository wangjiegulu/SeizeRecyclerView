package com.wangjie.seizerecyclerview.example.basic.adapter.actor;

import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.BaseViewHolder;
import com.wangjie.seizerecyclerview.BaseSeizeAdapter;
import com.wangjie.seizerecyclerview.SeizePosition;
import com.wangjie.seizerecyclerview.example.vm.actor.ActorVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class FilmActorSeizeAdapter extends BaseSeizeAdapter {
    public interface OnFilmActorSeizeAdapterListener {
        void onFilmActorItemClick(ActorVM actorVM, SeizePosition seizePosition);
    }

    private OnFilmActorSeizeAdapterListener onFilmActorSeizeAdapterListener;

    public void setOnFilmActorSeizeAdapterListener(OnFilmActorSeizeAdapterListener onFilmActorSeizeAdapterListener) {
        this.onFilmActorSeizeAdapterListener = onFilmActorSeizeAdapterListener;
    }

    public OnFilmActorSeizeAdapterListener getOnFilmActorSeizeAdapterListener() {
        return onFilmActorSeizeAdapterListener;
    }

    private List<ActorVM> list = new ArrayList<>();

    public void setList(List<ActorVM> list) {
        this.list = list;
    }

    public void addList(List<ActorVM> list) {
        this.list.addAll(list);
    }

    public List<ActorVM> getList() {
        return list;
    }

    @Override
    public int getSourceItemViewType(int subSourcePosition) {
        return list.get(subSourcePosition).getViewType();
    }

    @Override
    public BaseViewHolder onCreateTypeViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ActorVM.TYPE_ACTOR:
                return new FilmActorSeizeViewHolder(this, parent);
            default:
                return null;
        }
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
