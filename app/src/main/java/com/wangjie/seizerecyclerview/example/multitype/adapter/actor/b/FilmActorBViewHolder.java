package com.wangjie.seizerecyclerview.example.multitype.adapter.actor.b;

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
public class FilmActorBViewHolder extends BaseViewHolder implements View.OnClickListener {
    public interface OnFilmActorBViewHolderListener {

        void onFilmActorBItemClick(int subSourcePosition);
    }
    private OnFilmActorBViewHolderListener onFilmActorBViewHolderListener;

    public void setOnFilmActorBViewHolderListener(OnFilmActorBViewHolderListener onFilmActorBViewHolderListener) {
        this.onFilmActorBViewHolderListener = onFilmActorBViewHolderListener;
    }

    TextView actorTv;

    public FilmActorBViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_actor, parent, false));
        actorTv = (TextView) itemView.findViewById(R.id.item_film_actor_tv);
        actorTv.setBackgroundColor(Color.YELLOW);
        actorTv.setOnClickListener(this);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        actorTv.setText("actor B " + seizePosition.getPosition());
    }

    @Override
    public void onClick(View v) {
        if(v == actorTv && null != onFilmActorBViewHolderListener){
            onFilmActorBViewHolderListener.onFilmActorBItemClick(getSeizePosition().getSubSourcePosition());
        }
    }
}
