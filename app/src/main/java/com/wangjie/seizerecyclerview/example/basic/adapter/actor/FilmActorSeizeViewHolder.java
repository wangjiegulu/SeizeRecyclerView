package com.wangjie.seizerecyclerview.example.basic.adapter.actor;

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
 * Date: 3/28/17.
 */
public class FilmActorSeizeViewHolder extends BaseViewHolder {
    TextView actorTv;
    private FilmActorSeizeAdapter seizeAdapter;

    public FilmActorSeizeViewHolder(final FilmActorSeizeAdapter seizeAdapter, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_actor, parent, false));
        this.seizeAdapter = seizeAdapter;

        actorTv = (TextView) itemView.findViewById(R.id.item_film_actor_tv);

        actorTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seizeAdapter.getOnFilmActorSeizeAdapterListener().onFilmActorItemClick(seizeAdapter.getList().get(getSeizePosition().getSubSourcePosition()), getSeizePosition());
            }
        });

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        int subSourcePosition = seizePosition.getSubSourcePosition();
        int seizeAdapterIndex = seizePosition.getSeizeAdapterIndex();
        actorTv.setText(seizeAdapterIndex + ", " + subSourcePosition + " / " + "actor___content");
//        actorTv.setText(seizeAdapterIndex + ", " + subSourcePosition + " / " + seizeAdapter.getList().get(subSourcePosition).getObj());
    }
}
