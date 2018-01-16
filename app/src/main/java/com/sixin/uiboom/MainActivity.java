package com.sixin.uiboom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lehand.indicator.TabPageIndicator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabPageIndicator indicator;
    private ViewPager viewPager;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indicator = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.viewPager);
        for(int i = 0 ; i < 100 ; i++){
            data.add("第" + i + "个");
        }
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        indicator.setViewPager(viewPager);
    }

    class MyAdapter extends FragmentStatePagerAdapter{

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
