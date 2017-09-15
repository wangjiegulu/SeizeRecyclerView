package com.wangjie.seizerecyclerview.example.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wangjie.seizerecyclerview.SeizePosition;
import com.wangjie.seizerecyclerview.example.R;
import com.wangjie.seizerecyclerview.example.basic.adapter.FeedAdapter;
import com.wangjie.seizerecyclerview.example.basic.adapter.actor.FilmActorSeizeAdapter;
import com.wangjie.seizerecyclerview.example.basic.adapter.comment.FilmCommentSeizeAdapter;
import com.wangjie.seizerecyclerview.example.vm.actor.ActorVM;
import com.wangjie.seizerecyclerview.example.vm.comment.CommentVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/29/17.
 */
public class BasicActivity extends AppCompatActivity implements
        FilmActorSeizeAdapter.OnFilmActorSeizeAdapterListener,
        FilmCommentSeizeAdapter.OnFilmCommentSeizeAdapterListener,
        View.OnClickListener {

    private FeedAdapter adapter;
    private RecyclerView feedRv;
    private FilmActorSeizeAdapter filmActorSeizeAdapter;
    private FilmCommentSeizeAdapter filmCommentSeizeAdapter;
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
        setContentView(R.layout.activity_basic);
        feedRv = (RecyclerView) findViewById(R.id.activity_basic_rv);

        // The whole origin adapter of RecyclerView
        adapter = new FeedAdapter();
        // set header and footer for the whole origin adapter of RecyclerView
        adapter.setHeader(headerView = inflaterHeaderOrFooterAndBindClick(R.layout.header_film));
        adapter.setFooter(footerView = inflaterHeaderOrFooterAndBindClick(R.layout.footer_film));

        // attach seize adapters to origin adapter of RecyclerView
        adapter.setSeizeAdapters(
                filmActorSeizeAdapter = new FilmActorSeizeAdapter(),
                filmCommentSeizeAdapter = new FilmCommentSeizeAdapter()
        );

        // set header and footer for the film actor seize adapter
        filmActorSeizeAdapter.setOnFilmActorSeizeAdapterListener(this);
        filmActorSeizeAdapter.setHeader(actorHeaderView = inflaterHeaderOrFooterAndBindClick(R.layout.header_film_actor));
        filmActorSeizeAdapter.setFooter(actorFooterView = inflaterHeaderOrFooterAndBindClick(R.layout.footer_film_actor));

        // set header and footer for the film comment seize adapter
        filmCommentSeizeAdapter.setOnFilmCommentSeizeAdapterListener(this);
        filmCommentSeizeAdapter.setHeader(commentHeaderView = inflaterHeaderOrFooterAndBindClick(R.layout.header_film_comment));
        filmCommentSeizeAdapter.setFooter(commentFooterView = inflaterHeaderOrFooterAndBindClick(R.layout.footer_film_comment));

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
            ActorVM actorVM = new ActorVM("actor_" + now);
            list.add(actorVM);
        }
        int size = filmActorSeizeAdapter.getList().size();
        filmActorSeizeAdapter.addList(list);
        filmActorSeizeAdapter.notifyItemRangeInserted(size, list.size());
        filmActorSeizeAdapter.notifyItemRangeChanged(size, list.size());
//        filmActorSeizeAdapter.notifyDataSetChanged();
    }

    public void addComments(View view) {
        List<CommentVM> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            long now = System.currentTimeMillis();
            CommentVM commentVM = new CommentVM("comment_" + now);
            list.add(commentVM);
        }
        int size = filmCommentSeizeAdapter.getList().size();
        filmCommentSeizeAdapter.addList(list);
        filmCommentSeizeAdapter.notifyItemRangeInserted(size, list.size());
        filmCommentSeizeAdapter.notifyItemRangeChanged(size, list.size());
//        filmCommentSeizeAdapter.addList(list);
//        filmCommentSeizeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFilmActorItemClick(ActorVM actorVM, SeizePosition seizePosition) {
        toast.setText(seizePosition.toString());
        toast.show();
    }

    @Override
    public void onFilmCommentItemClick(CommentVM commentVM, SeizePosition seizePosition) {
        toast.setText(seizePosition.toString());
        toast.show();
    }

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