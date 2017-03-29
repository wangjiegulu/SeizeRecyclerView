package com.wangjie.seizerecyclerview.example.multitype.adapter.comment.b;

import android.content.Context;
import android.view.ViewGroup;

import com.wangjie.seizerecyclerview.BaseViewHolder;
import com.wangjie.seizerecyclerview.attacher.ViewHolderOwner;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class FilmCommentBViewHolderOwner extends ViewHolderOwner {
    public FilmCommentBViewHolderOwner(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder createViewHolder(ViewGroup parent) {
        return new FilmCommentBViewHolder(parent);
    }
}
