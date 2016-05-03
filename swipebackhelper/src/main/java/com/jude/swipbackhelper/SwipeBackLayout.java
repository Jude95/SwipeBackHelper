package com.jude.swipbackhelper;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class SwipeBackLayout extends FrameLayout {
    private static final String TAG = "ViewDragHelper";

    /**
     * Minimum velocity that will be detected as a fling
     */
    private static final int MIN_FLING_VELOCITY = 400; // dips per second

    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;

    private static final int FULL_ALPHA = 255;


    /**
     * A view is currently being dragged. The position is currently changing as
     * a result of user input or simulated user input.
     */
    public static final int STATE_DRAGGING = ViewDragHelper.STATE_DRAGGING;

    /**
     * A view is currently settling into place as a result of a fling or
     * predefined non-interactive motion.
     */
    public static final int STATE_SETTLING = ViewDragHelper.STATE_SETTLING;

    /**
     * Default threshold of scroll
     */
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.3f;

    private static final int OVERSCROLL_DISTANCE = 10;

    /**
     * Threshold of scroll, we will close the activity, when scrollPercent over
     * this value;
     */
    private float mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;

    private Activity mActivity;

    private boolean mEnable = true;

    private boolean mDisallowIntercept = false;

    private View mContentView;

    private ViewDragHelper mDragHelper;

    private float mScrollPercent;

    private int mContentLeft;

    /**
     * The set of listeners to be sent events through.
     */
    private List<SwipeListener> mListeners;

    Drawable mShadowLeft;

    private float mScrimOpacity;

    private int mScrimColor = DEFAULT_SCRIM_COLOR;

    private boolean mInLayout;

    private Rect mTmpRect = new Rect();

    /**
     * Edge being dragged
     */
    private int mTrackingEdge;

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, new ViewDragCallback());

        setShadow(R.drawable.shadow_left);

        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;
        setEdgeSize(getResources().getDisplayMetrics().widthPixels);
        mDragHelper.setMinVelocity(minVel);
        mDragHelper.setMaxVelocity(minVel * 2f);
        mDragHelper.setSensitivity(context, 0.3f);
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    /**
     * Sets the sensitivity of the NavigationLayout.
     *
     * @param context     The application context.
     * @param sensitivity value between 0 and 1, the final value for touchSlop =
     *                    ViewConfiguration.getScaledTouchSlop * (1 / s);
     */
    public void setSensitivity(Context context, float sensitivity) {
        mDragHelper.setSensitivity(context, sensitivity);
    }

    /**
     * Set up contentView which will be moved by user gesture
     *
     * @param view
     */
    private void setContentView(View view) {
        mContentView = view;
    }

    public void setEnableGesture(boolean enable) {
        mEnable = enable;
    }

    /**
     * Set a color to use for the scrim that obscures primary content while a
     * drawer is open.
     *
     * @param color Color to use in 0xAARRGGBB format.
     */
    public void setScrimColor(int color) {
        mScrimColor = color;
        invalidate();
    }

    /**
     * Set the size of an edge. This is the range in pixels along the edges of
     * this view that will actively detect edge touches or drags if edge
     * tracking is enabled.
     *
     * @param size The size of an edge in pixels
     */
    public void setEdgeSize(int size) {
        mTrackingEdge = size;
        mDragHelper.setEdgeSize(mTrackingEdge);
    }


    public void setEdgeSizePercent(float size) {
        mTrackingEdge = (int) (getResources().getDisplayMetrics().widthPixels * size);
        mDragHelper.setEdgeSize(mTrackingEdge);
    }

    /**
     * Register a callback to be invoked when a swipe event is sent to this
     * view.
     *
     * @param listener the swipe listener to attach to this view
     * @deprecated use {@link #addSwipeListener} instead
     */
    @Deprecated
    public void setSwipeListener(SwipeListener listener) {
        addSwipeListener(listener);
    }

    /**
     * Add a callback to be invoked when a swipe event is sent to this view.
     *
     * @param listener the swipe listener to attach to this view
     */
    public void addSwipeListener(SwipeListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<SwipeListener>();
        }
        mListeners.add(listener);
    }

    /**
     * Removes a listener from the set of listeners
     *
     * @param listener
     */
    public void removeSwipeListener(SwipeListener listener) {
        if (mListeners == null) {
            return;
        }
        mListeners.remove(listener);
    }



    /**
     * Set scroll threshold, we will close the activity, when scrollPercent over
     * this value
     *
     * @param threshold
     */
    public void setScrollThreshold(float threshold) {
        if (threshold >= 1.0f || threshold <= 0) {
            throw new IllegalArgumentException("Threshold value should be between 0 and 1.0");
        }
        mScrollThreshold = threshold;
    }


    public void setShadow(Drawable shadow) {
        mShadowLeft = shadow;
        invalidate();
    }


    public void setShadow(int resId) {
        setShadow(getResources().getDrawable(resId));
    }

    /**
     * Scroll out contentView and finish the activity
     */
    public void scrollToFinishActivity() {
        final int childWidth = mContentView.getWidth();
        int left = 0, top = 0;
        left = childWidth + mShadowLeft.getIntrinsicWidth() + OVERSCROLL_DISTANCE;
        mDragHelper.smoothSlideViewTo(mContentView, left, top);
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!mEnable||mDisallowIntercept) {
            return false;
        }
        try {
            return mDragHelper.shouldInterceptTouchEvent(event);
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mEnable) {
            return false;
        }
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setDisallowInterceptTouchEvent(boolean disallowIntercept) {
        mDisallowIntercept = disallowIntercept;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mInLayout = true;
        if (mContentView != null)
            mContentView.layout(mContentLeft, 0,
                    mContentLeft + mContentView.getMeasuredWidth(),
                    mContentView.getMeasuredHeight());
        mInLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mContentView;

        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (mScrimOpacity > 0 && drawContent
                && mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return ret;
    }

    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * mScrimOpacity);
        final int color = alpha << 24 | (mScrimColor & 0xffffff);
        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
    }

    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);

        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top,
                childRect.left, childRect.bottom);
        mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
        mShadowLeft.draw(canvas);
    }

    public void attachToActivity(Activity activity) {
        if (getParent()!=null){
            return;
        }
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        View decorChild  = decor.findViewById(android.R.id.content);
        while (decorChild.getParent() != decor){
            decorChild = (View) decorChild.getParent();
        }
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    public void removeFromActivity(Activity activity){
        if (getParent()==null)return;
        ViewGroup decorChild = (ViewGroup) getChildAt(0);
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        decor.removeView(this);
        removeView(decorChild);
        decor.addView(decorChild);
    }

    @Override
    public void computeScroll() {
        mScrimOpacity = 1 - mScrollPercent;
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        private boolean mIsScrollOverValid;

        @Override
        public boolean tryCaptureView(View view, int i) {
            boolean ret = mDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, i);
            if (ret) {
                if (mListeners != null && !mListeners.isEmpty()) {
                    for (SwipeListener listener : mListeners) {
                        listener.onEdgeTouch();
                    }
                }
                mIsScrollOverValid = true;
            }
            return ret;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mTrackingEdge;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 0;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mScrollPercent = Math.abs((float) left / (mContentView.getWidth() /*+ mShadowLeft.getIntrinsicWidth()*/));
            mContentLeft = left;
            invalidate();
            if (mScrollPercent < mScrollThreshold && !mIsScrollOverValid) {
                mIsScrollOverValid = true;
            }

            if (mListeners != null && !mListeners.isEmpty()) {
                for (SwipeListener listener : mListeners) {
                    listener.onScroll(mScrollPercent, mContentLeft);
                }
            }
            if (mScrollPercent >= 1) {
                if (!mActivity.isFinishing()){
                    if (mListeners != null && !mListeners.isEmpty()
                            && mScrollPercent >= mScrollThreshold && mIsScrollOverValid) {
                        mIsScrollOverValid = false;
                        for (SwipeListener listener : mListeners) {
                            listener.onScrollToClose();
                        }
                    }
                    mActivity.finish();
                }
            }


        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final int childWidth = releasedChild.getWidth();

            int left = 0, top = 0;
            //判断释放以后是应该滑到最右边(关闭)，还是最左边（还原）
            left = xvel > 0 || xvel == 0 && mScrollPercent > mScrollThreshold ? childWidth
                    + mShadowLeft.getIntrinsicWidth() + OVERSCROLL_DISTANCE : 0;


            mDragHelper.settleCapturedViewAt(left, top);
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return Math.min(child.getWidth(), Math.max(left, 0));
        }

    }
}
