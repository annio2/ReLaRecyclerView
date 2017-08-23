package com.example.wuguanglin.relarecyclerview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.wuguanglin.relarecyclerview.R;
import com.example.wuguanglin.relarecyclerview.RecyclerView.LoadMoreRecyclerView;
import com.example.wuguanglin.relarecyclerview.RecyclerView.ReLaRecyclerView;
import com.example.wuguanglin.relarecyclerview.RecyclerView.WrapRecyclerView;
import com.example.wuguanglin.relarecyclerview.adapter.InnerAdapter;
import com.example.wuguanglin.relarecyclerview.assistant.DefaultLoadCreator;
import com.example.wuguanglin.relarecyclerview.assistant.LoadMoreCreator;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LoadMoreRecyclerView recyclerView;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        final InnerAdapter adapter = new InnerAdapter(this, list);
        adapter.setOnItemClickListener(new InnerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Toast.makeText(MainActivity.this, "item被点击", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
//        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
//        recyclerView.addFooter(header);
        recyclerView.addLoadMoreCreator(new DefaultLoadCreator());
//        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
//            @Override
//            public void onLoad() {
//                Toast.makeText(MainActivity.this, "正在加载", Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    void initView(){
        recyclerView = (LoadMoreRecyclerView) findViewById(R.id.loadMoreRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    void initData(){
        for (int i = 0; i < 20; i++) {
            list.add("RelaRecyclerView" + i);
        }
    }
}
