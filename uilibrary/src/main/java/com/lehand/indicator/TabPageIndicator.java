package com.lehand.indicator;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lehand.util.DisplayUtil;


/**
 * @author zhou
 */

public class TabPageIndicator extends HorizontalScrollView implements PageIndicator {

    private ViewPager mViewPager;
    private Runnable mTabSelector;
    private LinearLayout mChildView;
    private ViewPager.OnPageChangeListener mListener;
    private TabPageChangeListener mTabPageIndicatorListener;

    private static final float DEFAULT_TEXT_SIZE = 16.0f;
    private static final int CRITICAL_VALUE = 4;
    private static final int MINIMUM_HEIGHT = 50;

    private final int mScreenWidth = DisplayUtil.getDisplayWidth(getContext());
    private final int mTabViewWidth = mScreenWidth / 4;

    public TabPageIndicator(Context context) {
        this(context, null);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mTabPageIndicatorListener = new TabPageChangeListener();
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
            TextView tabView = new TabView(getContext());
            initTabView(tabView, i);
            mChildView.addView(tabView,i);
        }
    }

    private void initTabView(TextView tabView, final int i) {
        tabView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        tabView.setGravity(Gravity.CENTER);
        tabView.setTextSize(DEFAULT_TEXT_SIZE);
        tabView.setPadding(22,15,22,20);
        tabView.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        tabView.setEllipsize(TextUtils.TruncateAt.END);
        tabView.setSingleLine(true);
        String title = (String) mViewPager.getAdapter().getPageTitle(i);
        if(title == null || "".equals(title)){
            throw new IllegalArgumentException("ViewPager must have title");
        }
        tabView.setText(mViewPager.getAdapter().getPageTitle(i));

        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(i,true);
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
            TextView tabView = (TextView) mChildView.getChildAt(i);
            if(i == position){
                tabView.setSelected(true);
            }else{
                tabView.setSelected(false);
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

    private class TabPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(mListener != null){
                mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            changeCurrentItem(position);
            scrollToTab(position);
            if(mListener != null){
                mListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(mListener != null){
                mListener.onPageScrollStateChanged(state);
            }
        }
    }

}
