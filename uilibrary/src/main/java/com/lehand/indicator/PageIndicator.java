package com.lehand.indicator;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

/**
 * @author zhou
 * 指示器的职责在于显示所有的标签以及当前标签下对应的视图
 */

public interface PageIndicator{

    /**
     * 设置与指示器关联的viewPager
     * @param viewPager {@link ViewPager}
     * */
    void setViewPager(@NonNull ViewPager viewPager);

    /**
     * 当ViewPager的数据发生变化，更新标签
     * */
    void notifyDataSetChanged();

}
