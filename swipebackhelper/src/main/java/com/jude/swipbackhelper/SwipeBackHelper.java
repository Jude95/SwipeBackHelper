package com.jude.swipbackhelper;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * 滑动的全局管理类
 */
public class SwipeBackHelper {

    private static final Stack<SwipeBackPage> mPageStack = new Stack<>();

    private static SwipeBackPage findHelperByActivity(Activity activity){
        for (SwipeBackPage swipeBackPage : mPageStack) {
            if (swipeBackPage.mActivity == activity) return swipeBackPage;
        }
        return null;
    }

    public static SwipeBackPage getCurrentPage(Activity activity){
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null){
            throw new RuntimeException("You Should call SwipeBackHelper.onCreate(activity) first");
        }
        return page;
    }



    public static void onCreate(Activity activity) {
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null){
            page = mPageStack.push(new SwipeBackPage(activity));
        }
        page.onCreate();
    }

    public static void onPostCreate(Activity activity){
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null){
            throw new RuntimeException("You Should call SwipeBackHelper.onCreate(activity) first");
        }
        page.onPostCreate();
    }

    public static void onDestroy(Activity activity){
        SwipeBackPage helper;
        if ((helper = findHelperByActivity(activity)) == null){
            throw new RuntimeException("You Should call SwipeBackHelper.onCreate(activity) first");
        }
        mPageStack.remove(helper);
        helper.mActivity=null;
    }

    static SwipeBackPage getPrePage(SwipeBackPage activity){
        int index = mPageStack.indexOf(activity);
        Log.i("Swipe", "index" + index + "  length:" + mPageStack.size());
        if (index>0)return mPageStack.get(index-1);
        else return null;
    }

}
