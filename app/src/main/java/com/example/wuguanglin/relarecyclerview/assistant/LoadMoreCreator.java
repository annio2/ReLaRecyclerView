package com.example.wuguanglin.relarecyclerview.assistant;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wuguanglin on 2017/8/23.
 * Description 为了匹配加载的效果
 */

public abstract class LoadMoreCreator {

    /**获取上拉加载更多的view
     * @param context 上下文
     * @param parent RecyclerView
    * */
    public abstract View getLoadView(Context context, ViewGroup parent);

    /**
     * 正在拖动
     * @param currentDragHeight 当前拖动的高度
     * @param loadViewHeight 总的加载高度
     * @param currentLoadStatus 当前状态
     * */
    public abstract void onPull(int currentDragHeight, int loadViewHeight, int currentLoadStatus);

    /**
     * 正在加载
     * */
    public abstract void onLoad();

    /**
     * 加载停止
     * */
    public abstract void onLoadStop();
}
