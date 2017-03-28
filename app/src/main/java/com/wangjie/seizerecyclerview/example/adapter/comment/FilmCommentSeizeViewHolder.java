package com.wangjie.seizerecyclerview.example.adapter.comment;

import android.view.LayoutInflater;
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
public class FilmCommentSeizeViewHolder extends BaseRecyclerHolder {
    TextView commentTv;
    public FilmCommentSeizeViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_comment, parent, false));

        commentTv = (TextView) itemView.findViewById(R.id.item_film_comment_tv);

    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, SeizePosition seizePosition) {

    }
}
