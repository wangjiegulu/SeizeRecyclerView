package com.wangjie.seizerecyclerview.example.multitype;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wangjie.seizerecyclerview.attacher.Func1R;
import com.wangjie.seizerecyclerview.attacher.MultiSeizeAdapter;
import com.wangjie.seizerecyclerview.example.R;
import com.wangjie.seizerecyclerview.example.basic.adapter.FeedAdapter;
import com.wangjie.seizerecyclerview.example.multitype.adapter.actor.a.FilmActorAViewHolderOwner;
import com.wangjie.seizerecyclerview.example.multitype.adapter.actor.b.FilmActorBViewHolderOwner;
import com.wangjie.seizerecyclerview.example.multitype.adapter.comment.a.FilmCommentAViewHolderOwner;
import com.wangjie.seizerecyclerview.example.multitype.adapter.comment.b.FilmCommentBViewHolderOwner;
import com.wangjie.seizerecyclerview.example.vm.actor.ActorAVM;
import com.wangjie.seizerecyclerview.example.vm.actor.ActorBVM;
import com.wangjie.seizerecyclerview.example.vm.actor.ActorVM;
import com.wangjie.seizerecyclerview.example.vm.comment.CommentAVM;
import com.wangjie.seizerecyclerview.example.vm.comment.CommentBVM;
import com.wangjie.seizerecyclerview.example.vm.comment.CommentVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class MultiTypeActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FeedAdapter adapter;
    private RecyclerView feedRv;
    private MultiSeizeAdapter<ActorVM> filmActorSeizeAdapter;
    private MultiSeizeAdapter<CommentVM> filmCommentSeizeAdapter;
    private Toast toast;

    private View headerView;
    private View footerView;
    private View actorHeaderView;
    private View actorFooterView;
    private View commentHeaderView;
    private View commentFooterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        feedRv = (RecyclerView) findViewById(R.id.activity_multi_type_rv);

        // The whole origin adapter of RecyclerView
        adapter = new FeedAdapter();
        // set header and footer for the whole origin adapter of RecyclerView
        adapter.setHeader(headerView = inflaterHeaderOrFooterAndBindClick(R.layout.header_film));
        adapter.setFooter(footerView = inflaterHeaderOrFooterAndBindClick(R.layout.footer_film));

        // set header and footer for the film actor seize adapter
        filmActorSeizeAdapter = new MultiSeizeAdapter<>();
        filmActorSeizeAdapter.setHeader(actorHeaderView = inflaterHeaderOrFooterAndBindClick(R.layout.header_film_actor));
        filmActorSeizeAdapter.setFooter(actorFooterView = inflaterHeaderOrFooterAndBindClick(R.layout.footer_film_actor));
        filmActorSeizeAdapter.setGetItemType(new Func1R<ActorVM, Integer>() {
            @Override
            public Integer call(ActorVM actorVM) {
                return actorVM.getActorViewType();
            }
        });
        filmActorSeizeAdapter.addSupportViewHolder(ActorVM.TYPE_ACTOR_A, new FilmActorAViewHolderOwner(this));
        filmActorSeizeAdapter.addSupportViewHolder(ActorVM.TYPE_ACTOR_B, new FilmActorBViewHolderOwner(this));

        // set header and footer for the film comment seize adapter
        filmCommentSeizeAdapter = new MultiSeizeAdapter<>();
        filmCommentSeizeAdapter.setHeader(commentHeaderView = inflaterHeaderOrFooterAndBindClick(R.layout.header_film_comment));
        filmCommentSeizeAdapter.setFooter(commentFooterView = inflaterHeaderOrFooterAndBindClick(R.layout.footer_film_comment));
        filmCommentSeizeAdapter.setGetItemType(new Func1R<CommentVM, Integer>() {
            @Override
            public Integer call(CommentVM commentVM) {
                return commentVM.getCommentViewType();
            }
        });
        filmCommentSeizeAdapter.addSupportViewHolder(CommentVM.TYPE_COMMENT_A, new FilmCommentAViewHolderOwner(this));
        filmCommentSeizeAdapter.addSupportViewHolder(CommentVM.TYPE_COMMENT_B, new FilmCommentBViewHolderOwner(this));

        // attach seize adapters to origin adapter of RecyclerView
        adapter.setSeizeAdapters(filmActorSeizeAdapter, filmCommentSeizeAdapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        feedRv.setLayoutManager(layoutManager);

        // set origin adapter to RecyclerView
        feedRv.setAdapter(adapter);

        initOtherComponents();
    }

    public void addActors(View view) {
        List<ActorVM> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            long now = System.currentTimeMillis();
            ActorVM actorVM;
            if (i % 2 == 0) {
                actorVM = new ActorAVM("actor_" + now);
            } else {
                actorVM = new ActorBVM("actor_" + now);
            }
            list.add(actorVM);

        }
        filmActorSeizeAdapter.addList(list);
        filmActorSeizeAdapter.notifyDataSetChanged();
    }

    public void addComments(View view) {
        List<CommentVM> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            long now = System.currentTimeMillis();
            CommentVM commentVM;
            if (i % 2 == 0) {
                commentVM = new CommentAVM("comment_" + now);
            } else {
                commentVM = new CommentBVM("comment_" + now);
            }
            list.add(commentVM);
        }
        filmCommentSeizeAdapter.addList(list);
        filmCommentSeizeAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onFilmActorItemClick(ActorVM actorVM, SeizePosition seizePosition) {
//        toast.setText(seizePosition.toString());
//        toast.show();
//    }
//
//    @Override
//    public void onFilmCommentItemClick(CommentVM commentVM, SeizePosition seizePosition) {
//        toast.setText(seizePosition.toString());
//        toast.show();
//    }

    @Override
    public void onClick(View v) {
        if (v == headerView) {
            toast.setText("header clicked");
            toast.show();
        } else if (v == footerView) {
            toast.setText("footer clicked");
            toast.show();
        } else if (v == actorHeaderView) {
            toast.setText("actor header clicked");
            toast.show();
        } else if (v == actorFooterView) {
            toast.setText("actor footer clicked");
            toast.show();
        } else if (v == commentHeaderView) {
            toast.setText("comment header clicked");
            toast.show();
        } else if (v == commentFooterView) {
            toast.setText("comment footer clicked");
            toast.show();
        }
    }

    private void initOtherComponents() {
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
    }

    private View inflaterHeaderOrFooterAndBindClick(int resId) {
        View view = LayoutInflater.from(this).inflate(resId, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(this);
        return view;
    }
}