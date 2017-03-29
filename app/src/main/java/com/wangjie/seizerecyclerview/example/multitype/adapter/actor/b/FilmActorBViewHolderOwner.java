package com.wangjie.seizerecyclerview.example.multitype.adapter.actor.b;

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
public class FilmActorBViewHolderOwner extends ViewHolderOwner implements FilmActorBViewHolder.OnFilmActorBViewHolderListener {
    public FilmActorBViewHolderOwner(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder createViewHolder(ViewGroup parent) {
        FilmActorBViewHolder filmActorBViewHolder = new FilmActorBViewHolder(parent);
        filmActorBViewHolder.setOnFilmActorBViewHolderListener(this);
        return filmActorBViewHolder;
    }

    @Override
    public void onFilmActorBItemClick(int subSourcePosition) {
        Toast.makeText(context, "B actor " + subSourcePosition + " clicked", Toast.LENGTH_SHORT).show();
    }
}
