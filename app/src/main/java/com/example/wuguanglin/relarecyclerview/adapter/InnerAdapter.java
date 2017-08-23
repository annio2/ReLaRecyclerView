package com.example.wuguanglin.relarecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wuguanglin.relarecyclerview.R;

import java.util.List;

/**
 * Created by wuguanglin on 2017/8/17.
 */

public class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<String> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public InnerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemText.setText(list.get(position));
        holder.itemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemText;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemText = (TextView)itemView.findViewById(R.id.itemText);
        }
    }
}
