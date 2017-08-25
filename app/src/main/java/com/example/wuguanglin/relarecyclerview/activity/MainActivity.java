package com.example.wuguanglin.relarecyclerview.activity;

import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuguanglin.relarecyclerview.R;
import com.example.wuguanglin.relarecyclerview.RecyclerView.LoadMoreRecyclerView;
import com.example.wuguanglin.relarecyclerview.RecyclerView.ReLaRecyclerView;
import com.example.wuguanglin.relarecyclerview.RecyclerView.WrapRecyclerView;
import com.example.wuguanglin.relarecyclerview.adapter.InnerAdapter;
import com.example.wuguanglin.relarecyclerview.assistant.DefaultLoadCreator;
import com.example.wuguanglin.relarecyclerview.assistant.LoadMoreCreator;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
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

        InnerAdapter adapter = new InnerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addLoadMoreCreator(new DefaultLoadCreator());
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoad() {
                Toast.makeText(MainActivity.this, "正在加载", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.onStopLoad();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
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
