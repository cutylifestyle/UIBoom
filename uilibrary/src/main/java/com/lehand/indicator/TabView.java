package com.lehand.indicator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sixin.uilibrary.R;

/**
 * @author zhou
 */

public class TabView extends android.support.v7.widget.AppCompatTextView {

    private Drawable mSelectedDrawable;
    private Drawable mTransparentDrawable;



    public TabView(Context context) {
        super(context);
        initBottomDrawable(context);
        switchBottomDrawable(false);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBottomDrawable(context);
        switchBottomDrawable(false);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBottomDrawable(context);
        switchBottomDrawable(false);
    }

    public void switchBottomDrawable(boolean isSelected) {
        if(isSelected){
            mSelectedDrawable.setBounds(0,0,getWidth(),15);
            setCompoundDrawables(null,null,null, mSelectedDrawable);
        }else{
            mTransparentDrawable.setBounds(0,0,getWidth(),15);
            setCompoundDrawables(null,null,null, mTransparentDrawable);
        }
    }

    private void initBottomDrawable(Context context) {
        mSelectedDrawable = context.getResources().getDrawable(R.drawable.line);
        mTransparentDrawable = context.getResources().getDrawable(R.drawable.transparent);
    }


}
