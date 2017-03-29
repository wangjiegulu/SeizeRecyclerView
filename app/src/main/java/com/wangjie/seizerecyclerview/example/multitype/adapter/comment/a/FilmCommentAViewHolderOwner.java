package com.wangjie.seizerecyclerview.example.multitype.adapter.comment.a;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wangjie.seizerecyclerview.BaseViewHolder;
import com.wangjie.seizerecyclerview.attacher.ViewHolderOwner;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class FilmCommentAViewHolderOwner extends ViewHolderOwner implements FilmCommentAViewHolder.OnFilmCommentAViewHolderListener {
    public FilmCommentAViewHolderOwner(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder createViewHolder(ViewGroup parent) {
        FilmCommentAViewHolder holder = new FilmCommentAViewHolder(parent);
        holder.setOnFilmCommentAViewHolderListener(this);
        return holder;
    }

    @Override
    public void onFilmCommentAItemClick(int subSourcePosition) {
        Toast.makeText(context, "A comment " + subSourcePosition + " clicked", Toast.LENGTH_SHORT).show();
    }
}
