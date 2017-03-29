package com.wangjie.seizerecyclerview.example.multitype.adapter.comment.b;

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
public class FilmCommentBViewHolderOwner extends ViewHolderOwner implements FilmCommentBViewHolder.OnFilmCommentBViewHolderListener {
    public FilmCommentBViewHolderOwner(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder createViewHolder(ViewGroup parent) {
        FilmCommentBViewHolder filmCommentBViewHolder = new FilmCommentBViewHolder(parent);
        filmCommentBViewHolder.setOnFilmCommentBViewHolderListener(this);
        return filmCommentBViewHolder;
    }

    @Override
    public void onFilmCommentBItemClick(int subSourcePosition) {
        Toast.makeText(context, "B comment " + subSourcePosition + " clicked", Toast.LENGTH_SHORT).show();
    }
}
