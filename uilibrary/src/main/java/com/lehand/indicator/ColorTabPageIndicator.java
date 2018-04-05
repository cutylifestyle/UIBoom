package com.lehand.indicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lehand.util.DisplayUtil;

/**
 * @author zhou
 * <p>通过颜色变换来实现的指示器</p>
 */

public class ColorTabPageIndicator extends HorizontalScrollView implements PageIndicator {
    // TODO: 2018/3/5 控件的大小
    // TODO: 2018/3/5 修复左右滑动的bug
    // TODO: 2018/3/5 滑动方向确定存在问题
    // TODO: 2018/3/5 滑动过程中的滑动效果有点儿突兀
    // TODO: 2018/3/5 对于状态信息用一个类进行封装
    // TODO: 2018/3/5 控件大小有字体的长度来确定
    private static final String TAG = ColorTabPageIndicator.class.getName();
    private ViewPager mViewPager;
    private Runnable mTabSelector;
    private LinearLayout mChildView;
    private ViewPager.OnPageChangeListener mListener;
    private ColorTabPageIndicator.TabPageChangeListener mTabPageIndicatorListener;

    private static final float DEFAULT_TEXT_SIZE = 16.0f;
    private static final int CRITICAL_VALUE = 4;
    private static final int MINIMUM_HEIGHT = 50;

    private final int mScreenWidth = DisplayUtil.getDisplayWidth(getContext());
    private final int mTabViewWidth = mScreenWidth / 4;

    private int  mCurrentScrollX;

    private float mLastPositionOffsetSum;

    public ColorTabPageIndicator(Context context) {
        this(context, null);
    }

    public ColorTabPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTabPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        setMinimumHeight(MINIMUM_HEIGHT);
        initChildView(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            post(mTabSelector);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        resetTabViewLayoutParams();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setViewPager(@NonNull ViewPager viewPager) {
        final PagerAdapter pagerAdapter = viewPager.getAdapter();
        if(pagerAdapter == null){
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        if(mViewPager == viewPager){
            return;
        }
        if(mViewPager == null){
            mViewPager = viewPager;
        }else{
            mViewPager.addOnPageChangeListener(null);
            mViewPager = viewPager;
        }
        if(mTabPageIndicatorListener != null){
            mViewPager.removeOnPageChangeListener(mTabPageIndicatorListener);
        }
        mTabPageIndicatorListener = new ColorTabPageIndicator.TabPageChangeListener();
        mViewPager.addOnPageChangeListener(mTabPageIndicatorListener);
        notifyDataSetChanged();
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        mListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildView.removeAllViews();
        addTabViews();
        changeCurrentItem(0);
        requestLayout();
    }

    private void initChildView(Context context) {
        mChildView = new LinearLayout(context);
        mChildView.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        addView(mChildView);
    }

    private void addTabViews(){
        int viewPagerChildCount = mViewPager.getAdapter().getCount();
        for(int i = 0 ; i < viewPagerChildCount ; i++){
            TextView tabView = new ColorTabView(getContext());
            initTabView(tabView, i);
            mChildView.addView(tabView,i);
        }
    }

    private void initTabView(TextView tabView, final int i) {
        tabView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        String title = (String) mViewPager.getAdapter().getPageTitle(i);
        if(title == null || "".equals(title)){
            throw new IllegalArgumentException("ViewPager must have title");
        }
        tabView.setText(mViewPager.getAdapter().getPageTitle(i));
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(i,false);
            }
        });
    }

    private void resetTabViewLayoutParams() {
        int tabViewCount = mChildView.getChildCount();
        if(tabViewCount < CRITICAL_VALUE){
            for(int i = 0 ; i < tabViewCount ; i++){
                View tabView = mChildView.getChildAt(i);
                tabView.setLayoutParams(new LinearLayout.LayoutParams(
                        mScreenWidth / tabViewCount,
                        LayoutParams.WRAP_CONTENT));
            }
        }else{
            for(int i = 0 ; i < tabViewCount ; i++){
                View tabView = mChildView.getChildAt(i);
                tabView.setLayoutParams(new LinearLayout.LayoutParams(
                        mTabViewWidth,
                        LayoutParams.WRAP_CONTENT));
            }
        }
    }

    private void changeCurrentItem(int position) {
        int tabViewCount = mChildView.getChildCount();
        for(int i = 0 ; i < tabViewCount ; i++){
            ColorTabView tabView = (ColorTabView) mChildView.getChildAt(i);
            if(i == position){
                tabView.setInitColor(true);
            }else{
                tabView.setInitColor(false);
            }
        }
    }

    private void scrollToTab(final int position) {
        if(mTabSelector != null){
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            @Override
            public void run() {
                View tabView = mChildView.getChildAt(position);
                int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth())/2;
                smoothScrollTo(scrollPos,0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    private void handleUpdate(boolean leftToRight, int currentPosition, int nextPosition, float positionOffset) {
        ColorTabView currentTabView = (ColorTabView) mChildView.getChildAt(currentPosition);
        ColorTabView nextTabView = (ColorTabView) mChildView.getChildAt(nextPosition);
        if(currentTabView != null && nextTabView != null){
            if (leftToRight) {
                currentTabView.onColorOffset(1,positionOffset,false);
                nextTabView.onColorOffset(1,positionOffset,true);
            }else{
                currentTabView.onColorOffset(1,positionOffset,false);
                nextTabView.onColorOffset(-1,positionOffset,true);
            }
        }
    }

    private class TabPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG, "position:" + position+" positionOffset:"+positionOffset);
            float currentPositionOffsetSum = position + positionOffset;
            int nextPosition;
            if(mLastPositionOffsetSum < currentPositionOffsetSum){
                nextPosition = position+1;
                handleUpdate(true,position,nextPosition,positionOffset);
            }else if(mLastPositionOffsetSum > currentPositionOffsetSum){
                if(currentPositionOffsetSum >= position){
                    nextPosition = position + 1;
                    handleUpdate(true,position,nextPosition,positionOffset);
                }else{
                    nextPosition = position - 1;
                    handleUpdate(false,position,nextPosition,positionOffset);
                }
            }
            mLastPositionOffsetSum = currentPositionOffsetSum;

            if(mListener != null){
                mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
//            Log.d(TAG, "onPageSelected:" + position);
//            changeCurrentItem(position);
//            scrollToTab(position);
            if(mListener != null){
                mListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG,"onPageScrollStateChanged:"+state);
            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    break;
            }
            if(mListener != null){
                mListener.onPageScrollStateChanged(state);
            }
        }
    }
}
