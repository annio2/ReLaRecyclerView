package com.example.wuguanglin.relarecyclerview.RecyclerView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by wuguanglin on 2017/8/21.
 */

public class ReLaRecyclerView extends RecyclerView {
    private Adapter mAdapter;
    private boolean isLoading = false;
    private OnLoadListener onLoadListener;

    public interface OnLoadListener{
//        void onLoadStart();
        void onLoading();
//        void onLoadEnd();
    }

    public void setOnLoadListener(OnLoadListener listener){
        this.onLoadListener = listener;
    }

    public ReLaRecyclerView(Context context) {
        super(context);
    }

    public ReLaRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReLaRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.mAdapter = adapter;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && !isLoading){
            LayoutManager layoutManager = getLayoutManager();
            int lastVisiablePosition;
            if (layoutManager instanceof GridLayoutManager){
                lastVisiablePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            }else if (layoutManager instanceof StaggeredGridLayoutManager){
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisiablePosition = getMaxPosition(into);
            }else {
                lastVisiablePosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
            }
            if (lastVisiablePosition == layoutManager.getItemCount() - 1
                    && layoutManager.getItemCount() >= layoutManager.getChildCount()){
                isLoading = true;
                if (onLoadListener != null){
                    onLoadListener.onLoading();
                    isLoading = false;
                }
            }
        }
    }
    //获取瀑布流布局最后可见的position数组中的最大位置
    private int getMaxPosition(int[] positions){
        int max = positions[0];
        for (int value : positions){
            if (value > max){
                max = value;
            }
        }
        return max;
    }
}
