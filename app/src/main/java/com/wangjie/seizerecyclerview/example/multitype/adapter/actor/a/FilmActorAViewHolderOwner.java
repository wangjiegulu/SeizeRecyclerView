package com.wangjie.seizerecyclerview.example.multitype.adapter.actor.a;

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
public class FilmActorAViewHolderOwner extends ViewHolderOwner implements FilmActorAViewHolder.OnFilmActorAViewHolderListener {
    public FilmActorAViewHolderOwner(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder createViewHolder(ViewGroup parent) {
        FilmActorAViewHolder holder = new FilmActorAViewHolder(parent);
        holder.setOnFilmActorAViewHolderListener(this);
        return holder;
    }

    @Override
    public void onFilmActorAItemClick(int subSourcePosition) {
        Toast.makeText(context, "A actor " + subSourcePosition + " clicked", Toast.LENGTH_SHORT).show();
    }
}
