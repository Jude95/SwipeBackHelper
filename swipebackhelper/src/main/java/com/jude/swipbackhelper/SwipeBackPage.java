package com.jude.swipbackhelper;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

/**
 * Created by Mr.Jude on 2015/8/3.
 * 每个滑动页面的管理
 */
public class SwipeBackPage {
    Activity mActivity;
    SwipeBackLayout mSwipeBackLayout;

    SwipeBackPage(Activity activity){
        this.mActivity = activity;
    }

    //页面的回调用于配置滑动效果
    void onCreate(){
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        mSwipeBackLayout = new SwipeBackLayout(mActivity);
        mSwipeBackLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    void onPostCreate(){
        mSwipeBackLayout.attachToActivity(mActivity);
    }

    //这个页面的属性设置
    public SwipeBackPage setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
        return this;
    }

    public SwipeBackPage setSwipeEdge(int swipeEdge){
        mSwipeBackLayout.setEdgeSize(swipeEdge);
        return this;
    }

    public SwipeBackPage setSwipeEdgePercent(float swipeEdgePercent){
        mSwipeBackLayout.setEdgeSizePercent(swipeEdgePercent);
        return this;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(mActivity);
        mSwipeBackLayout.scrollToFinishActivity();
    }


}
