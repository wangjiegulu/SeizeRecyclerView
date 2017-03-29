package com.wangjie.seizerecyclerview.example.multitype.adapter.comment.b;

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
 * Date: 3/28/17.
 */
public class FilmCommentBViewHolder extends BaseViewHolder implements View.OnClickListener {
    public interface OnFilmCommentBViewHolderListener {

        void onFilmCommentBItemClick(int subSourcePosition);
    }

    private OnFilmCommentBViewHolderListener onFilmCommentBViewHolderListener;

    public void setOnFilmCommentBViewHolderListener(OnFilmCommentBViewHolderListener onFilmCommentBViewHolderListener) {
        this.onFilmCommentBViewHolderListener = onFilmCommentBViewHolderListener;
    }

    TextView commentTv;

    public FilmCommentBViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_comment, parent, false));
        commentTv = (TextView) itemView.findViewById(R.id.item_film_comment_tv);
        commentTv.setBackgroundColor(Color.LTGRAY);
        commentTv.setOnClickListener(this);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        commentTv.setText("Comment B, " + seizePosition.getPosition());
    }

    @Override
    public void onClick(View v) {
        if (commentTv == v && null != onFilmCommentBViewHolderListener) {
            onFilmCommentBViewHolderListener.onFilmCommentBItemClick(getSeizePosition().getSubSourcePosition());
        }
    }
}
