package com.lehand.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sixin.uilibrary.R;

/**
 * Created by 周文涛 on 2018/2/9.
 */

public class TabView extends TextView {
    public TabView(Context context) {
        super(context);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.indicator_style);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
