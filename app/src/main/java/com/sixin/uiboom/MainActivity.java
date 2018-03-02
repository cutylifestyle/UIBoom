package com.sixin.uiboom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lehand.indicator.ColorTabPageIndicator;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ArrayList<String> data = new ArrayList<>();
    private ColorTabPageIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indicator = findViewById(R.id.indicator);
        ViewPager viewPager = findViewById(R.id.viewPager);
        for(int i = 0 ; i < 9 ; i++){
            data.add("第" + i + "个");
        }
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        indicator.setViewPager(viewPager);

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BlankFragment.newInstance();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position);
        }
    }
}
