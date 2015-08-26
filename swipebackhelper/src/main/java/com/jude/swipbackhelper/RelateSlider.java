package com.jude.swipbackhelper;

/**
 * Created by Mr.Jude on 2015/8/26.
 */
public class RelateSlider implements SwipeListener {
    public SwipeBackPage curPage;
    private int offset;

    public RelateSlider(SwipeBackPage curActivity, int offset) {
        this.curPage = curActivity;
        this.offset = offset;
    }

    @Override
    public void onScroll(float percent, int px) {
        SwipeBackPage page = SwipeBackHelper.getPrePage(curPage);
        if (page!=null){
            page.getSwipeBackLayout().setX(-offset * (1 - percent));
        }
    }

    @Override
    public void onEdgeTouch() {

    }

    @Override
    public void onScrollToClose() {

    }
}
