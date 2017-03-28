package com.wangjie.seizerecyclerview.example.adapter.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangjie.seizerecyclerview.BaseRecyclerHolder;
import com.wangjie.seizerecyclerview.SeizePosition;
import com.wangjie.seizerecyclerview.example.R;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/28/17.
 */
public class FilmCommentSeizeViewHolder extends BaseRecyclerHolder {
    private FilmCommentSeizeAdapter seizeAdapter;
    TextView commentTv;

    public FilmCommentSeizeViewHolder(final FilmCommentSeizeAdapter seizeAdapter, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_comment, parent, false));
        this.seizeAdapter = seizeAdapter;
        commentTv = (TextView) itemView.findViewById(R.id.item_film_comment_tv);

        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seizeAdapter.getOnFilmCommentSeizeAdapterListener().onFilmCommentItemClick(seizeAdapter.getList().get(getSeizePosition().getSubPosition()), getSeizePosition());
            }
        });
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, SeizePosition seizePosition) {
        int subPosition = seizePosition.getSubPosition();
        int seizeAdapterIndex = seizePosition.getSeizeAdapterIndex();
        commentTv.setText(seizeAdapterIndex + ", " + subPosition + " / " + seizeAdapter.getList().get(subPosition).getObj());
    }
}
