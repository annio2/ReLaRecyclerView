package com.example.wuguanglin.relarecyclerview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import java.util.List;

/**
 * Created by wuguanglin on 2017/8/22.
 */

public class MyStackAdapter extends BaseAdapter {
    private List<Integer> imagesId;
    private Context context;

    public MyStackAdapter(Context context, List<Integer> imagesId) {
        this.imagesId = imagesId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imagesId.size();
    }

    @Override
    public Object getItem(int i) {
        return imagesId.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imagesId.get(i));
        return imageView;
    }
}
