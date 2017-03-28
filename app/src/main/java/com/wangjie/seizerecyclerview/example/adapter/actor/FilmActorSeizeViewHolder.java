package com.wangjie.seizerecyclerview.example.adapter.actor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangjie.seizerecyclerview.example.R;
import com.wangjie.seizerecyclerview.SeizePosition;
import com.wangjie.seizerecyclerview.BaseRecyclerHolder;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class FilmActorSeizeViewHolder extends BaseRecyclerHolder {
    TextView actorTv;
    private FilmActorSeizeAdapter seizeAdapter;
    public FilmActorSeizeViewHolder(final FilmActorSeizeAdapter seizeAdapter, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_actor, parent, false));

        actorTv = (TextView) itemView.findViewById(R.id.item_film_actor_tv);

        actorTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seizeAdapter.getOnFilmActorSeizeAdapterListener().onFilmActorItemClick(seizeAdapter.getList().get(getSeizePosition().getSubPosition()), getSeizePosition());
            }
        });

    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, SeizePosition seizePosition) {

    }
}
