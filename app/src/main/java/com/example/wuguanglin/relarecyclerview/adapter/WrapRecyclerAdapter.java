package com.example.wuguanglin.relarecyclerview.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.w3c.dom.ProcessingInstruction;

/**
 * Created by wuguanglin on 2017/8/17.
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = "WrapRecyclerAdapter";
    //用来存放头部和底部view的集合，类型为（int， Object），比map要高效一些
    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private SparseArray<View> mFooterViews = new SparseArray<>();
    //基本头部类型开始标志，用于ItemType
    private static int BASE_ITEM_TYPE_HEADER = 10000;
    //基本底部类型开始位置，用于ItemType
    private static int BASE_ITEM_TYPE_FOOTER = 20000;
    //列表数据的adapter
    private RecyclerView.Adapter mAdapter;

    public WrapRecyclerAdapter(RecyclerView.Adapter adapter){
        this.mAdapter = adapter;
    }

    //是否是头部位置
    private boolean isHeaderPosition(int position){
        return position < mHeaderViews.size();
    }

    //是否是底部位置
    private boolean isFooterPosition(int position){
        return position >= mHeaderViews.size() + mAdapter.getItemCount();
    }

    //获取数据列表adapter
    private RecyclerView.Adapter getAdapter(){
        return mAdapter;
    }

    //添加Header
    public void addHeaderView(View view){
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
        notifyDataSetChanged();
    }

    //添加Footer
    public void addFooterView(View view){
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
        notifyDataSetChanged();
    }

    //移除头部
    public void removeHeader(View view){
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0)return;
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    //移除底部
    public void removeFooter(View view){
        int index = mFooterViews.indexOfValue(view);
        if (index < 0)return;
        mFooterViews.removeAt(index);
        notifyDataSetChanged();
    }
    /*
    *创建头部或底部的holder
    * */
    private RecyclerView.ViewHolder createHolder(View view){
        return new RecyclerView.ViewHolder(view) {

        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null){
            View headerView = mHeaderViews.get(viewType);
            return createHolder(headerView);
        }
        if (mFooterViews.get(viewType) != null){
            View footerView = mFooterViews.get(viewType);
            return createHolder(footerView);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)){
            return;
        }
        position = position - mHeaderViews.size();
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        //返回数据adapter的大小与头部、底部数目的总和
        return mAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)){
            //直接返回position位置的key
            return mHeaderViews.keyAt(position);
        }
        if (isFooterPosition(position)){
            //需要计算position对应的mFooterViews中的位置，返回该位置的key
            return mFooterViews.keyAt(position - mHeaderViews.size() - mAdapter.getItemCount());
        }
        //返回数据adapter的getItemViewType
        return mAdapter.getItemViewType(position - mHeaderViews.size());
    }



    /*使GridView添加头部或尾部时，头部或尾部能单独占用一行*/
    /*
    private void adjustSpanSize(RecyclerView recyclerView){
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager)layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null || mFooterViews.get(viewType) != null){
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }
    */
    /*
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager)layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null || mFooterViews.get(viewType) != null){
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }
    */
    /*使StaggeredGridLayoutManager添加头部或尾部能独占一行*/
    /*
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderPosition(position) || isFooterPosition(position)){
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams)layoutParams;
                params.setFullSpan(true);
            }
        }
    }
    */
}
