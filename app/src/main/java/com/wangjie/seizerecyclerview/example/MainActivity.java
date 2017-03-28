package com.wangjie.seizerecyclerview.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wangjie.seizerecyclerview.SeizePosition;
import com.wangjie.seizerecyclerview.example.adapter.FeedAdapter;
import com.wangjie.seizerecyclerview.example.adapter.actor.FilmActorSeizeAdapter;
import com.wangjie.seizerecyclerview.example.adapter.comment.FilmCommentSeizeAdapter;
import com.wangjie.seizerecyclerview.example.vm.actor.ActorVM;
import com.wangjie.seizerecyclerview.example.vm.comment.CommentVM;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FilmActorSeizeAdapter.OnFilmActorSeizeAdapterListener {

    private FeedAdapter adapter;
    private RecyclerView feedRv;
    private FilmActorSeizeAdapter filmActorSeizeAdapter;
    private FilmCommentSeizeAdapter filmCommentSeizeAdapter;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        feedRv = (RecyclerView) findViewById(R.id.activity_main_rv);

        adapter = new FeedAdapter();

        adapter.setSeizeAdapters(
                filmCommentSeizeAdapter = new FilmCommentSeizeAdapter(),
                filmActorSeizeAdapter = new FilmActorSeizeAdapter()
        );
        filmActorSeizeAdapter.setOnFilmActorSeizeAdapterListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        feedRv.setLayoutManager(layoutManager);

        feedRv.setAdapter(adapter);

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
    }

    public void actorAdd(View view) {
        List<ActorVM> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            long now = System.currentTimeMillis();
            ActorVM actorVM = new ActorVM("actor_" + now);
            list.add(actorVM);
        }
        filmActorSeizeAdapter.addList(list);
        filmActorSeizeAdapter.notifyDataSetChanged();
    }

    public void commentAdd(View view) {
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
        toast.setText(actorVM.getObj() + ", " + seizePosition);
        toast.show();
    }
}
