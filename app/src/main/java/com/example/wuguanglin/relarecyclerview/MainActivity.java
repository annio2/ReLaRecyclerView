package com.example.wuguanglin.relarecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wuguanglin.relarecyclerview.adapter.InnerAdapter;
import com.example.wuguanglin.relarecyclerview.adapter.WrapRecyclerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        InnerAdapter adapter = new InnerAdapter(this, list);
        WrapRecyclerAdapter wrapAdapter = new WrapRecyclerAdapter(adapter);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View headView = layoutInflater.inflate(R.layout.header, null);
        View footView = layoutInflater.inflate(R.layout.footer, null);
        wrapAdapter.addHeaderView(headView);
        wrapAdapter.addFooterView(footView);
        recyclerView.setAdapter(wrapAdapter);
    }

    void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.headFootRecyclerView);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    void initData(){
        for (int i = 0; i < 20; i++) {
            list.add("RelaRecyclerView" + i);
        }
    }
}
