package com.wangjie.seizerecyclerview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wangjie.seizerecyclerview.SeizePosition;
import com.wangjie.seizerecyclerview.example.adapter.FeedAdapter;
import com.wangjie.seizerecyclerview.example.adapter.actor.FilmActorSeizeAdapter;
import com.wangjie.seizerecyclerview.example.adapter.comment.FilmCommentSeizeAdapter;
import com.wangjie.seizerecyclerview.example.vm.actor.ActorVM;
import com.wangjie.seizerecyclerview.example.vm.comment.CommentVM;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FilmActorSeizeAdapter.OnFilmActorSeizeAdapterListener,
        FilmCommentSeizeAdapter.OnFilmCommentSeizeAdapterListener,
        View.OnClickListener {

    private FeedAdapter adapter;
    private RecyclerView feedRv;
    private FilmActorSeizeAdapter filmActorSeizeAdapter;
    private FilmCommentSeizeAdapter filmCommentSeizeAdapter;
    private Toast toast;

    private View actorHeader;
    private View actorFooter;
    private View commentHeader;
    private View commentFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        feedRv = (RecyclerView) findViewById(R.id.activity_main_rv);

        // The whole origin adapter of RecyclerView
        adapter = new FeedAdapter();

        // attach seize adapters to origin adapter of RecyclerView
        adapter.setSeizeAdapters(
                filmActorSeizeAdapter = new FilmActorSeizeAdapter(),
                filmCommentSeizeAdapter = new FilmCommentSeizeAdapter()
        );

        // set headers and footers for the film actor seize adapter
        filmActorSeizeAdapter.setOnFilmActorSeizeAdapterListener(this);
        filmActorSeizeAdapter.setHeader(actorHeader = inflaterHeaderOrFooterAndBindClick(R.layout.header_film_actor));
        filmActorSeizeAdapter.setFooter(actorFooter = inflaterHeaderOrFooterAndBindClick(R.layout.footer_film_actor));

        // set headers and footers for the film comment seize adapter
        filmCommentSeizeAdapter.setOnFilmCommentSeizeAdapterListener(this);
        filmCommentSeizeAdapter.setHeader(commentHeader = inflaterHeaderOrFooterAndBindClick(R.layout.header_film_comment));
        filmCommentSeizeAdapter.setFooter(commentFooter = inflaterHeaderOrFooterAndBindClick(R.layout.footer_film_comment));

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
        filmActorSeizeAdapter.addList(list);
        filmActorSeizeAdapter.notifyDataSetChanged();
    }

    public void addComments(View view) {
        List<CommentVM> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            long now = System.currentTimeMillis();
            CommentVM commentVM = new CommentVM("comment_" + now);
            list.add(commentVM);
        }
        filmCommentSeizeAdapter.addList(list);
        filmCommentSeizeAdapter.notifyDataSetChanged();
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
        if (v == actorHeader) {
            toast.setText("actor header clicked");
            toast.show();
        } else if (v == actorFooter) {
            toast.setText("actor footer clicked");
            toast.show();
        } else if (v == commentHeader) {
            toast.setText("comment header clicked");
            toast.show();
        } else if (v == commentFooter) {
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
