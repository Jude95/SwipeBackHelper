package com.jude.swipbackhelper;

import android.util.Log;

/**
 * Created by Mr.Jude on 2015/8/26.
 */
public class RelateSlider implements SwipeListener {
    public SwipeBackPage curPage;
    private int offset = 500;

    public RelateSlider(SwipeBackPage curActivity) {
        this.curPage = curActivity;
        //curPage.addListener(this);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setEnable(boolean enable){
        if (enable)curPage.addListener(this);
        else curPage.removeListener(this);
    }

    @Override
    public void onScroll(float percent, int px) {
        SwipeBackPage page = SwipeBackHelper.getPrePage(curPage);
        if (page!=null){
            page.getSwipeBackLayout().setX(-offset * Math.max(1 - percent,0));
            if (percent == 0){
                page.getSwipeBackLayout().setX(0);
            }
        }
    }

    @Override
    public void onEdgeTouch() {

    }

    @Override
    public void onScrollToClose() {
        SwipeBackPage page = SwipeBackHelper.getPrePage(curPage);
        page.getSwipeBackLayout().setX(0);
    }
}
