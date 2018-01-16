package com.lehand.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.sixin.uilibrary.R;

/**
 * @author zhou
 */

public class TabLayout extends LinearLayout {

    private static final String TAG = TabLayout.class.getName();
    private Drawable mBottomDrawable;

    public TabLayout(Context context) {
        this(context,null);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBottomDrawable = context.getResources().getDrawable(R.drawable.li);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBottomDrawable.setBounds(0,getBottom()-2,getWidth(),getBottom());
        mBottomDrawable.draw(canvas);
        super.onDraw(canvas);
    }
}
