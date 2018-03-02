package com.lehand.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sixin.uilibrary.R;

/**
 * @author zhou
 * <p>默认样式的标签</p>
 */

class TabView extends android.support.v7.widget.AppCompatTextView {
    public TabView(Context context) {
        this(context,null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,R.attr.tabPageIndicatorStyle);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
