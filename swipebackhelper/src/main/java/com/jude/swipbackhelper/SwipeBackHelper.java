package com.jude.swipbackhelper;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;


public class SwipeBackHelper {
    private Activity mActivity;

    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackHelper(Activity activity) {
        mActivity = activity;
        onActivityCreate();
    }

    public static SwipeBackHelper prepare(Activity activity){
        return new SwipeBackHelper(activity);
    }

    private void onActivityCreate() {
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(mActivity);
        mSwipeBackLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                Utils.convertActivityToTranslucent(mActivity);
            }

            @Override
            public void onScrollOverThreshold() {

            }
        });
        mActivity.getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mSwipeBackLayout.attachToActivity(mActivity);
            }
        });
    }

    public SwipeBackHelper setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
        return this;
    }

    public SwipeBackHelper setSwipeEdge(int swipeEdge){
        mSwipeBackLayout.setEdgeSize(swipeEdge);
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
