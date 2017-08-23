package com.example.wuguanglin.relarecyclerview.RecyclerView;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.wuguanglin.relarecyclerview.assistant.LoadMoreCreator;

/**
 * Created by wuguanglin on 2017/8/23.
 */

public class LoadMoreRecyclerView extends WrapRecyclerView {
    //上拉加载更多的辅助类
    private LoadMoreCreator loadMoreCreator;
    //上拉加载更多底部的高度
    private int loadViewHeight;
    //上拉加载底部的view
    private View loadView;
    //手指按下的位置
    private int mFingerDowm;
    //当前是否正在拖动
    private boolean currentDrag = false;
    //手指拖拽的阻力指数
    private float mDragIndex = 0.35f;
    //当前状态
    private int currentStatus;
    //默认状态
    private int LOAD_STATUS_NORMAL = 0x0011;
    //上拉状态
    private static int LOAD_STATUS_PULL_REFRESH = 0x0022;
    //松开状态
    private static int LOAD_STATUS_LOOSE_LOADING = 0x0033;
    //正在加载状态
    private static int LOAD_STATUS_LOADING = 0x0044;
    //处理加载更多的回调
    private OnLoadMoreListener mLoadListener;

    public interface OnLoadMoreListener{
        void onLoad();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mLoadListener = listener;
    }

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*考虑到上拉加载的布局样式不同，不直接添加view，使用辅助类*/
    public void addLoadMoreCreator(LoadMoreCreator creator){
        this.loadMoreCreator = creator;
        addRefreshView();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
//        addRefreshView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //记录手指按下的位置
                mFingerDowm = (int)ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (currentDrag){
                    restoreLoadView();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    //重置当前加载更多状态
    private void restoreLoadView(){
        final int currentBottomMargin = ((MarginLayoutParams)loadView.getLayoutParams()).bottomMargin;
        int finalBottomMargin = 0;
        if (currentStatus == LOAD_STATUS_LOOSE_LOADING){
            currentStatus = LOAD_STATUS_LOADING;
            if (loadMoreCreator != null){
                loadMoreCreator.onLoad();
            }
            if (mLoadListener != null){
                mLoadListener.onLoad();
            }
            int distance = currentBottomMargin - finalBottomMargin;
            //弹回到指定位置
            final ValueAnimator animator = ValueAnimator.ofFloat(currentBottomMargin, finalBottomMargin);
            animator.setDuration(distance);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float currentTopMargin = (float) animator.getAnimatedValue();
                    setLoadViewMarginBottom((int) currentTopMargin);
                }
            });
            animator.start();
            currentDrag = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_MOVE:
                //在底部才需要处理
                if (canScrollDown() || currentStatus == LOAD_STATUS_LOADING){
                    return super.onTouchEvent(e);
                }
                if (loadMoreCreator != null){
                    loadViewHeight = loadView.getMeasuredHeight();
                }
//                if (currentDrag){
//
//                }
                //获取手指触摸拖拽的距离
                int distanceY = (int) ((e.getRawY() - mFingerDowm) * mDragIndex);
                if (distanceY < 0) {
                    setLoadViewMarginBottom(-distanceY);
                    updateLoadStatus(-distanceY);
                    currentDrag = true;
                    return true;
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    //更新加载的状态
    private void updateLoadStatus(int distanceY){
        if (distanceY < 0){
            currentStatus = LOAD_STATUS_NORMAL;
        }else if (distanceY < loadViewHeight){
            currentStatus = LOAD_STATUS_PULL_REFRESH;
        }else {
            currentStatus = LOAD_STATUS_LOOSE_LOADING;
        }
        if (loadMoreCreator != null){
            loadMoreCreator.onPull(distanceY, loadViewHeight, currentStatus);
        }
    }

    //添加底部加载更多view
    private void addRefreshView(){
        Adapter adapter = getAdapter();
        if (adapter != null && loadMoreCreator != null){
            //添加底部加载更多view
            View loadView = loadMoreCreator.getLoadView(getContext(), this);
            if (loadView != null){
                addFooter(loadView);
                this.loadView = loadView;
            }
        }
    }

    //设置加载view的marginBottom
    private void setLoadViewMarginBottom(int marginBottom){
        MarginLayoutParams params = (MarginLayoutParams) loadView.getLayoutParams();
        if (marginBottom < 0){
            marginBottom = 0;
        }
        params.bottomMargin = marginBottom;
        loadView.setLayoutParams(params);
    }

    //判断是否滚动到了最顶部
    private boolean canScrollDown(){
        return ViewCompat.canScrollVertically(this, 1);
    }

    //停止加载更多
    private void onStopLoad(){
        currentStatus = LOAD_STATUS_NORMAL;
        restoreLoadView();
        if (loadMoreCreator != null){
            loadMoreCreator.onLoadStop();
        }
    }
}
