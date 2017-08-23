package com.example.wuguanglin.relarecyclerview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.StackView;

import com.example.wuguanglin.relarecyclerview.R;
import com.example.wuguanglin.relarecyclerview.adapter.MyStackAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class StackViewActivity extends AppCompatActivity {
    private StackView stackView;
    private List<Integer> imagesId;
    private MyStackAdapter adapter;
//    private Timer up, down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_view);
        initView();
        initData();
        adapter = new MyStackAdapter(this, imagesId);
        stackView.setAdapter(adapter);
    }

    void initView(){
        stackView = (StackView)findViewById(R.id.stackView);
    }

    void initData(){
        imagesId = new ArrayList<>();
        imagesId.add(R.drawable.stack1);
        imagesId.add(R.drawable.stack2);
        imagesId.add(R.drawable.stack3);
        imagesId.add(R.drawable.stack4);
    }
}
