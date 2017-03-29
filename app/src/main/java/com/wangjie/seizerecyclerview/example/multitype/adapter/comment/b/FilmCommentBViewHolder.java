package com.wangjie.seizerecyclerview.example.multitype.adapter.comment.b;

import android.graphics.Color;
import android.view.LayoutInflater;
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
public class FilmCommentBViewHolder extends BaseViewHolder {
    TextView commentTv;

    public FilmCommentBViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_comment, parent, false));
        commentTv = (TextView) itemView.findViewById(R.id.item_film_comment_tv);
        commentTv.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        commentTv.setText("Comment B, " + seizePosition.getPosition());
    }
}
