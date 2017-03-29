package com.wangjie.seizerecyclerview.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangjie.seizerecyclerview.example.basic.BasicActivity;
import com.wangjie.seizerecyclerview.example.multitype.MultiTypeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toBasicMode(View view) {
        startActivity(new Intent(this, BasicActivity.class));
    }

    public void toMultiTypeMode(View view) {
        startActivity(new Intent(this, MultiTypeActivity.class));
    }
}
