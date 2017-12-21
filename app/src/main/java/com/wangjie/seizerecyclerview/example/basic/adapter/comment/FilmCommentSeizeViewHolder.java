package com.wangjie.seizerecyclerview.example.basic.adapter.comment;

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
public class FilmCommentSeizeViewHolder extends BaseViewHolder {
    private FilmCommentSeizeAdapter seizeAdapter;
    TextView commentTv;

    public FilmCommentSeizeViewHolder(final FilmCommentSeizeAdapter seizeAdapter, ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_comment, parent, false));
        this.seizeAdapter = seizeAdapter;
        commentTv = (TextView) itemView.findViewById(R.id.item_film_comment_tv);

        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seizeAdapter.getOnFilmCommentSeizeAdapterListener().onFilmCommentItemClick(seizeAdapter.getList().get(getSeizePosition().getSubSourcePosition()), getSeizePosition());
            }
        });
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        int subSourcePosition = seizePosition.getSubSourcePosition();
        int seizeAdapterIndex = seizePosition.getSeizeAdapterIndex();
        commentTv.setText(seizeAdapterIndex + ", " + subSourcePosition + " / " + "comment_content");
    }
}
