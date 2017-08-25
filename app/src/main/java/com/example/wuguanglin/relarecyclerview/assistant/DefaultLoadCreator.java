package com.example.wuguanglin.relarecyclerview.assistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.example.wuguanglin.relarecyclerview.R;

/**
 * Created by wuguanglin on 2017/8/23.
 */

public class DefaultLoadCreator extends LoadMoreCreator {
    private View ivFooterView;
    private TextView tvLoadView;
    @Override
    public View getLoadView(Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.load_footer, parent, false);
        ivFooterView = view.findViewById(R.id.ivLoadFooter);
        tvLoadView = (TextView) view.findViewById(R.id.tvLoadFooter);
        tvLoadView.setText("上拉加载更多");
        return view;
    }

    @Override
    public void onPull(int currentDragHeight, int loadViewHeight, int currentLoadStatus) {
        ivFooterView.setVisibility(View.VISIBLE);
        if (currentDragHeight >= loadViewHeight){
            tvLoadView.setText("松开加载更多");
        }
        float rotate = ((float) currentDragHeight) / loadViewHeight;
        // 不断上拉的过程中不断的旋转图片
        ivFooterView.setRotation(rotate * 360);
    }

    @Override
    public void onLoad() {
        // 加载的时候不断旋转
        RotateAnimation animation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(1000);
        ivFooterView.startAnimation(animation);
    }

    @Override
    public void onLoadStop() {
        tvLoadView.setText("上拉加载更多");
        ivFooterView.setRotation(0);
        ivFooterView.clearAnimation();
        ivFooterView.setVisibility(View.GONE);
    }
}
