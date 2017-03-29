package com.wangjie.seizerecyclerview.example.multitype.adapter.actor.a;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangjie.seizerecyclerview.BaseViewHolder;
import com.wangjie.seizerecyclerview.SeizePosition;
import com.wangjie.seizerecyclerview.example.R;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class FilmActorAViewHolder extends BaseViewHolder implements View.OnClickListener{
    public interface OnFilmActorAViewHolderListener {

        void onFilmActorAItemClick(int subSourcePosition);
    }
    private OnFilmActorAViewHolderListener onFilmActorAViewHolderListener;
    public void setOnFilmActorAViewHolderListener(OnFilmActorAViewHolderListener onFilmActorAViewHolderListener) {
        this.onFilmActorAViewHolderListener = onFilmActorAViewHolderListener;
    }

    TextView actorTv;

    public FilmActorAViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_actor, parent, false));
        actorTv = (TextView) itemView.findViewById(R.id.item_film_actor_tv);
        actorTv.setBackgroundColor(Color.BLUE);
        actorTv.setOnClickListener(this);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        actorTv.setText("actor A " + seizePosition.getPosition());
    }

    @Override
    public void onClick(View v) {
        if(v == actorTv && null != onFilmActorAViewHolderListener){
            onFilmActorAViewHolderListener.onFilmActorAItemClick(getSeizePosition().getSubSourcePosition());
        }
    }
}
