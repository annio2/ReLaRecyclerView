package com.example.wuguanglin.relarecyclerview.RecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuguanglin.relarecyclerview.adapter.WrapRecyclerAdapter;

/**
 * Created by wuguanglin on 2017/8/23.
 */

public class WrapRecyclerView extends RecyclerView {
    //包裹了头部底部的adapter
    private WrapRecyclerAdapter wrapAdapter;
    //数据列表的adapter
    private Adapter mAdapter;

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mAdapter != null){
            mAdapter.unregisterAdapterDataObserver(dataObserver);
            mAdapter = null;
        }
        this.mAdapter = adapter;
        if (adapter instanceof WrapRecyclerAdapter){
            wrapAdapter = (WrapRecyclerAdapter)adapter;
        }else {
            wrapAdapter = new WrapRecyclerAdapter(adapter);
        }
        super.setAdapter(wrapAdapter);
        mAdapter.registerAdapterDataObserver(dataObserver);
    }

    /*添加头部尾部必须放在setAdapter之后*/
    //添加头部
    public void addHeader(View view){
        if (wrapAdapter != null){
            wrapAdapter.addHeaderView(view);
        }
    }

    //添加尾部
    public void addFooter(View view){
        if (wrapAdapter != null){
            wrapAdapter.addFooterView(view);
        }
    }

    //移除头部
    public void removeHeader(View view){
        if (wrapAdapter != null){
            wrapAdapter.removeHeader(view);
        }
    }

    //移除尾部
    public void removeFooter(View view){
        if (wrapAdapter != null){
            wrapAdapter.removeFooter(view);
        }
    }

    private AdapterDataObserver dataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter == null)return;
            if (wrapAdapter != mAdapter){
                wrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter == null)return;
            if (wrapAdapter != mAdapter){
                wrapAdapter.notifyItemChanged(positionStart);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mAdapter == null)return;
            if (wrapAdapter != mAdapter){
                wrapAdapter.notifyItemChanged(positionStart, payload);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter == null)return;
            if (wrapAdapter != mAdapter){
                wrapAdapter.notifyItemInserted(positionStart);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter == null)return;
            if (wrapAdapter != mAdapter){
                wrapAdapter.notifyItemRemoved(positionStart);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter == null)return;
            if (wrapAdapter != mAdapter){
                wrapAdapter.notifyItemMoved(fromPosition, toPosition);
            }
        }
    };
}
